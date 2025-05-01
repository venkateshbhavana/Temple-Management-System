package TempleManagementPackage;
import java.sql.*;
import java.util.*;

class DatabaseUtil {
    public static Connection getConnection() throws SQLException {
    	
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/cvsql", 
            "root", 
            "venky@123"
        );
    }
}

abstract class Service {
    protected String name;
    protected int price;

    public Service(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public String getName() { return name; }
    public int getPrice() { return price; }
}

class ArchanaService extends Service { public ArchanaService() { super("Archana", 100); } }
class AbhishekamService extends Service { public AbhishekamService() { super("Abhishekam", 150); } }
class HomamService extends Service { public HomamService() { super("Homam", 200); } }
class DarshanamService extends Service { public DarshanamService() { super("Darshanam", 100); } }
class PoojaService extends Service { public PoojaService() { super("Pooja", 120); } }
class PrasadamService extends Service { public PrasadamService() { super("Prasadam", 50); } }

class Temple {
    private String name;
    private String info;

    public Temple(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public void showInfo() {
        System.out.println("Temple Name: " + name);
        System.out.println("Temple Info: " + info);
    }

    public void showServices() {
        try (Connection con = DatabaseUtil.getConnection()) {
            String query = "SELECT s.name, s.price FROM services s JOIN temple_services ts ON s.id = ts.service_id WHERE ts.temple_name = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            int i = 1;
            while (rs.next()) {
                System.out.println(i + ". " + rs.getString("name") + " - Cost: " + rs.getInt("price"));
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching services: " + e.getMessage());
        }
    }

    public Service getService(int serviceNumber) {
        try (Connection con = DatabaseUtil.getConnection()) {
            String query = "SELECT s.name, s.price FROM services s JOIN temple_services ts ON s.id = ts.service_id WHERE ts.temple_name = ?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            int i = 1;
            while (rs.next()) {
                if (i == serviceNumber) {
                    String serviceName = rs.getString("name");
                    int price = rs.getInt("price");
                    switch (serviceName) {
                        case "Archana": return new ArchanaService();
                        case "Abhishekam": return new AbhishekamService();
                        case "Homam": return new HomamService();
                        case "Darshanam": return new DarshanamService();
                        case "Pooja": return new PoojaService();
                        case "Prasadam": return new PrasadamService();
                    }
                }
                i++;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching service: " + e.getMessage());
        }
        return null;
    }

    public String getName() { return name; }
    public String getInfo() { return info; }
}

public class TempleManagementSystem {
    // ANSI color codes


    private static Map<String, Temple> temples = new HashMap<>();
    private static int totalCost = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            initializeTemples();
            int choice = 0;
            while (choice != 4) {
                System.out.println("\nSelect an option:");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Admin Login");
                System.out.println("4. Exit");
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: registerUser(); break;
                    case 2: loginUser(); break;
                    case 3: adminLogin(); break;
                    case 4: System.out.println( "Exiting..." ); return;
                    default: System.out.println( "Invalid choice" );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void initializeTemples() {
        try (Connection con = DatabaseUtil.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM temples")) {
            while (rs.next()) {
                Temple temple = new Temple(rs.getString("name"), rs.getString("info"));
                temples.put(temple.getName(), temple);
            }
        } catch (SQLException e) {
            System.out.println("Error loading temples: " + e.getMessage());
        }
    }

    static void registerUser() {
        String username, password, mobile;
        while (true) {
            System.out.println("Enter your username (at least 5 characters):" );
            username = scanner.nextLine();
            if (username.length() < 5) {
                System.out.println("Invalid username. Please re-enter.");
                continue;
            }
            // Check if username exists in DB
            try (Connection con = DatabaseUtil.getConnection();
                 PreparedStatement pst = con.prepareStatement("SELECT * FROM users WHERE username=?")) {
                pst.setString(1, username);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    System.out.println( "Username already taken. Please re-enter.");
                    continue;
                }
            } catch (SQLException e) { System.out.println( e.getMessage()); }
            break;
        }
        while (true) {
            System.out.println( "Enter your password (min 6 chars, at least one uppercase letter, one special symbol):");
            password = scanner.nextLine();
            if (!isValidPassword(password)) {
                System.out.println("Password doesn't meet the criteria." );
                continue;
            }
            break;
        }
        while (true) {
            System.out.println( "Enter your mobile number (10 digits):" );
            mobile = scanner.nextLine();
            if (!isValidMobileNumber(mobile)) {
                System.out.println( "Invalid mobile number." );
                continue;
            }
            break;
        }
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO users(username, password, mobile) VALUES(?,?,?)")) {
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, mobile);
            pst.executeUpdate();
            System.out.println("Registration successful." );
        } catch (SQLException e) {
            System.out.println( "Registration failed: " + e.getMessage() );
        }
    }

    static void loginUser() {
        System.out.println( "Enter username:" );
        String username = scanner.nextLine();
        System.out.println( "Enter password:" );
        String password = scanner.nextLine();
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "SELECT * FROM users WHERE username = ? AND password = ?")) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println( "Login successful." );
                userMenu();
            } else {
                System.out.println( "Invalid username or password." );
            }
        } catch (SQLException e) {
            System.out.println( "Login error: " + e.getMessage() );
        }
    }

    static void adminLogin() {
        System.out.println( "Enter admin username:" );
        String username = scanner.nextLine();
        System.out.println( "Enter admin password:" );
        String password = scanner.nextLine();
        // Hardcoded admin for demo
        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println( "Admin Login successful." );
            adminMenu();
        } else {
            System.out.println( "Invalid admin credentials." );
        }
    }

    static void userMenu() {
        int choice = 0;
        while (choice != 2) {
            System.out.println( "\nSelect an option:");
            System.out.println("1. Select Temple");
            System.out.println("2. Logout");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: selectTemple(); break;
                case 2: System.out.println( "Logging out..." ); return;
                default: System.out.println( "Invalid choice." );
            }
        }
    }

    static void adminMenu() {
        int choice = 0;
        while (choice != 4) {
            System.out.println( "\nAdmin Menu:" );
            System.out.println("1. View Temples");
            System.out.println("2. Add Temple");
            System.out.println("3. Remove Temple");
            System.out.println("4. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: viewTemples(); break;
                case 2: addTemple(); break;
                case 3: removeTemple(); break;
                case 4: System.out.println( "Exiting admin menu."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void viewTemples() {
        System.out.println("\nAvailable Temples:");
        for (Temple temple : temples.values()) {
            temple.showInfo();
        }
    }

    static void addTemple() {
        System.out.println("Enter the name of the new temple:");
        String name = scanner.nextLine();
        System.out.println("Enter the info for the new temple:");
        String info = scanner.nextLine();
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "INSERT INTO temples(name, info) VALUES(?,?)")) {
            pst.setString(1, name);
            pst.setString(2, info);
            pst.executeUpdate();
            temples.put(name, new Temple(name, info));
            System.out.println("Temple added successfully.");
        } catch (SQLException e) {
            System.out.println( "Error adding temple: " + e.getMessage());
        }
    }

    static void removeTemple() {
        System.out.println("Enter the name of the temple to remove:");
        String name = scanner.nextLine();
        try (Connection con = DatabaseUtil.getConnection();
             PreparedStatement pst = con.prepareStatement(
                     "DELETE FROM temples WHERE name = ?")) {
            pst.setString(1, name);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                temples.remove(name);
                System.out.println("Temple removed successfully.");
            } else {
                System.out.println("Temple not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error removing temple: " + e.getMessage());
        }
    }

    static void selectTemple() {
        System.out.println("\nSelect a temple:");
        int i = 1;
        List<String> templeNames = new ArrayList<>(temples.keySet());
        for (String templeName : templeNames) {
            System.out.println(i + ". " + templeName);
            i++;
        }
        int templeChoice = scanner.nextInt();
        scanner.nextLine();
        if (templeChoice < 1 || templeChoice > templeNames.size()) {
            System.out.println( "Invalid temple selection.");
            return;
        }
        String selectedTempleName = templeNames.get(templeChoice - 1);
        viewTemple(selectedTempleName);
    }

    static void viewTemple(String templeName) {
        Temple temple = temples.get(templeName);
        temple.showInfo();
        int choice = 0;
        while (choice != 2) {
            System.out.println("\n1. View Services");
            System.out.println( "2. Back" );
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: temple.showServices(); chooseService(temple); break;
                case 2: return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void chooseService(Temple temple) {
        boolean anotherService = true;
        while (anotherService) {
            System.out.println("Select a service number:");
            int serviceChoice = scanner.nextInt();
            scanner.nextLine();
            Service service = temple.getService(serviceChoice);
            if (service != null) {
                System.out.println("Enter the number of persons:");
                int persons = scanner.nextInt();
                scanner.nextLine();
                totalCost += service.getPrice() * persons;
                System.out.println("Total cost: "+ totalCost);
                System.out.println("Do you want another service? (yes/no)");
                String answer = scanner.nextLine();
                anotherService = answer.equalsIgnoreCase("yes");
            } else {
                System.out.println( "Invalid service selection." );
            }
        }
    }

    static boolean isValidPassword(String password) {
        return password.length() >= 6 && password.matches(".*[A-Z].*") && password.matches(".*[!@#$%^&*()].*");
    }
    static boolean isValidMobileNumber(String mobile) {
        return mobile.matches("\\d{10}");
    }
}
