import java.util.*;

class Temple {
    private String name;
    private String info;
    private Map<Integer, Service> services = new HashMap<>();

    public Temple(String name, String info) {
        this.name = name;
        this.info = info;
    }

    public void addService(Service service) {
        services.put(services.size() + 1, service);
    }

    public void showInfo() {
        System.out.println("Temple Name: " + name);
        System.out.println("Temple Info: " + info);
    }

    public void showServices() {
	System.out.println("");
        System.out.println("\n                                                            Available Services at " + name + ":"            );
System.out.println("");
        int i = 1;
        for (Service service : services.values()) {
            System.out.println(i + ". " + service.getName()+" - Cost: " + service.getPrice());
            i++;
        }
    }

    public Service getService(int serviceNumber) {
        return services.get(serviceNumber);
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }
}

abstract class Service {
    protected String name;
    protected int price;

    public Service(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class ArchanaService extends Service {
    public ArchanaService() {
        super("Archana", 100);
    }
}

class AbhishekamService extends Service {
    public AbhishekamService() {
        super("Abhishekam", 150);
    }
}

class HomamService extends Service {
    public HomamService() {
        super("Homam", 200);
    }
}

class DarshanamService extends Service {
    public DarshanamService() {
        super("Darshanam", 100);
    }
}

class PoojaService extends Service {
    public PoojaService() {
        super("Pooja", 120);
    }
}

class PrasadamService extends Service {
    public PrasadamService() {
        super("Prasadam", 50);
    }
}

public class TempleManagementSystem{
    //ANSI color codes
	 public static final String RESET = "\033[0m";
         public static final String BOLD = "\033[1m";
    	 public static final String GREEN = "\033[32m";
   	 public static final String RED = "\033[31m";
   	 public static final String BLUE = "\033[34m";
   	 public static final String CYAN = "\033[36m";
	 public static final String YELLOW = "\033[33m";
	 public static final String CYAN_BG = "\033[46m";
	 public static final String MAGENTA = "\033[35m";
   	 public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
   	 public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
	 public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
	 public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
   	 public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
   	 public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	 static String DARK_BLUE= "\u001B[34m";
	 static String backred="\u001B[101m";
         static String blink="\u001B[5m";
	 static String bw="\u001B[107m";


    private static Map<String, Temple> temples = new HashMap<>();
    private static Map<String, String> users = new HashMap<>();
    private static Map<String, String> adminCredentials = new HashMap<>();
    private static int totalCost = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
	System.out.println(YELLOW+blink+""+RESET);
	
        System.out.println(blink+GREEN_BOLD_BRIGHT+"                                                                                                                                 ");
        System.out.println("                              		##-  ##*  ##++#######  ##+       +###*     +####.   ####  -###- ########                  ");
        System.out.println("                              		##* +### :## +#######  ##+      #######   #######-  ####  ####- ########                  ");
        System.out.println("                              		### #### +## +##.      ##+     ###=  ##+ ###=  ###  ####. ####- ###                       ");
        System.out.println("                              		=## #### ## +##-::::  ##+     ###       ###   +##  ##:# #*##- ###::::                   ");
        System.out.println("                              		:##-##+####+ +#######  ##+     ###       ###   :##- ## ##=#=##- #######:                  ");
        System.out.println("                               		 ####=:####: +##=----  ##+     ###   -   ###   =##. ## #### ##- ###----.                  ");
        System.out.println("                               		 ####. ####  +##.      ##+     ###   ### ###   *##  ## #### ##- ###                       ");
        System.out.println("                               		 *###  ####  +#######  ####### .#######- .########  ##  ##+ ##- ########                  ");
        System.out.println("                               		 -###  :##*  +#######  #######  +#####+   +######   ##  ##= ##- ########                  ");
        System.out.println("                                                                                                     ");
        System.out.println("                                                                      ########=  =####.                                               ");
        System.out.println("                                                                      ########= #######-                                              ");
        System.out.println("                                                                      ...###.. +##= .###                                              ");
        System.out.println("                                                                         ###   ###   -##-                                             ");
        System.out.println("                                                                         ###   ###    ##+                                             ");
        System.out.println("                                                                         ###   ###    ##+                                             ");
        System.out.println("                                                                         ###   ###   ###                                              ");
        System.out.println("                                                      l                  ###   .########                                              ");
        System.out.println("                                                                         ###    :######                                               ");
        System.out.println("                                                                         :::      :::.                                                ");
        System.out.println("                                                                                                     ");
        System.out.println("                                 =######## ####### -###:  ####  ######   *##     .#######-	  +####   *####### ###    ##+  +##     ");
        System.out.println("                                 +######## *####### -####  ####  #######  ###     .#######-	 *######. *####### ###   :##:  ####     ");
        System.out.println("                                 :--###--- *##      -#### .####  ##+ :##+ ###     .##=     	 ##=  ##= *##      :##-  ###   ####=    ");
        System.out.println("                                    *##    *##----- -#### +#.##  ##+  ##+ ###     .##+---- 	 ####=:   *##-----  ###  ##=  ###+##    ");
        System.out.println("                                    ##    *####### -###=## ##  #######. ###     .####### 	  ######  *#######  +## :##   ##-:##.   ");
        System.out.println("                                    ##    *##===== -## #### ##  ######-  ###     .##==== 	   .##### *##=====   ##=###  .##  ###   ");
        System.out.println("                                    *##    *##      -## #### ##  ##+      ###     .##=     	:##:  ### *##        #####.  ########   ");
        System.out.println("                                    ##    *##**** -## ##  ##  ##+      #######=.###-	 ###.:### *##****   :####   ########-  ");
        System.out.println("                                    *##    *####### -## =##  ##  ##+      #######=.#######=	 -######  *#######    ####  =##.   ###  ");
        System.out.println("                                    .::    .::::::: .::  ::  ::  ::.      :::::::. :::::::.	   -==.   .:::::::    .::   :::    :::  "+RESET);
	System.out.println(YELLOW+blink+""+RESET);

        initializeTemples();
        adminCredentials.put("admin", "admin123");

        int choice = 0;
        while (choice != 4) {
            System.out.println(YELLOW_BOLD_BRIGHT+"\nSelect an option:"+RESET);
            System.out.println(PURPLE_BOLD_BRIGHT +"1. Register"+RESET);
            System.out.println(PURPLE_BOLD_BRIGHT +"2. Login"+RESET);
            System.out.println(PURPLE_BOLD_BRIGHT +"3. Admin Login"+RESET);
            System.out.println(PURPLE_BOLD_BRIGHT +"4. Exit"+RESET);
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println(RED+"Exiting..."+RESET);
                    return;
                default:
                    System.out.println(RED+"Invalid choice"+RESET);
            }
        }
    }

    static void initializeTemples() {
        Temple durgaTemple = new Temple(YELLOW+"Durga", "Durga Temple is dedicated to Goddess Durga."+RESET);
        durgaTemple.addService(new ArchanaService());
        durgaTemple.addService(new AbhishekamService());
        durgaTemple.addService(new HomamService());
        durgaTemple.addService(new DarshanamService());
        durgaTemple.addService(new PoojaService());
        durgaTemple.addService(new PrasadamService());

        Temple shivaTemple = new Temple(YELLOW+"Shiva", "Shiva Temple is dedicated to Lord Shiva."+RESET);
        shivaTemple.addService(new ArchanaService());
        shivaTemple.addService(new AbhishekamService());
        shivaTemple.addService(new PoojaService());

        Temple vishnuTemple = new Temple(YELLOW+"Vishnu", "Vishnu Temple is dedicated to Lord Vishnu."+RESET);
        vishnuTemple.addService(new HomamService());
        vishnuTemple.addService(new DarshanamService());
        vishnuTemple.addService(new PoojaService());

        Temple ganeshaTemple = new Temple(YELLOW+"Ganesha", "Ganesha Temple is dedicated to Lord Ganesha."+RESET);
        ganeshaTemple.addService(new ArchanaService());
        ganeshaTemple.addService(new PrasadamService());

        temples.put(durgaTemple.getName(), durgaTemple);
        temples.put(shivaTemple.getName(), shivaTemple);
        temples.put(vishnuTemple.getName(), vishnuTemple);
        temples.put(ganeshaTemple.getName(), ganeshaTemple);
    }

    static void registerUser() {
        String username = "";
        while (true) {
            System.out.println(BLUE+"Enter your username (at least 5 characters):"+RESET);
            username = scanner.nextLine();
            if (username.equalsIgnoreCase("exit")) {
                System.out.println(RED+"Exiting registration process."+RESET);
                return;
            }
            if (username.length() < 5 || users.containsKey(username)) {
                System.out.println(CYAN+"Invalid username or username already taken. Please re-enter or type 'exit' to cancel."+RESET);
            } else {
                break;
            }
        }

        String password = "";
        while (true) {
            System.out.println(BLUE+"Enter your password (min 6 chars, at least one uppercase letter, one special symbol):"+RESET);
            password = scanner.nextLine();
            if (password.equalsIgnoreCase("exit")) {
                System.out.println(RED+"Exiting registration process."+RESET);
                return;
            }
            if (isValidPassword(password)) {
                break;
            } else {
                System.out.println(CYAN+"Password doesn't meet the criteria. Please re-enter or type 'exit' to cancel."+RESET);
            }
        }

        String mobile = "";
        while (true) {
            System.out.println(BLUE+"Enter your mobile number (10 digits):"+RESET);
            mobile = scanner.nextLine();
            if (mobile.equalsIgnoreCase("exit")) {
                System.out.println(RED+"Exiting registration process."+RESET);
                return;
            }
            if (isValidMobileNumber(mobile)) {
                break;
            } else {
                System.out.println(CYAN+"Invalid mobile number. Please re-enter or type 'exit' to cancel."+RESET);
            }
        }

        String otp = generateOTP();
        System.out.println(DARK_BLUE+"OTP sent to your mobile: "+RESET + otp);

        String enteredOTP = "";
        while (true) {
            System.out.println(CYAN+"Enter the OTP to verify your registration:"+RESET);
            enteredOTP = scanner.nextLine();
            if (enteredOTP.equalsIgnoreCase("exit")) {
                System.out.println(RED+"Exiting registration process."+RESET);
                return;
            }
            if (enteredOTP.equals(otp)) {
                break;
            } else {
                System.out.println(CYAN+"Incorrect OTP. Please re-enter or type 'exit' to cancel."+RESET);
            }
        }

        users.put(username, password);
        System.out.println(GREEN+"Registration successful."+RESET);
    }

    static void loginUser() {
        String username = "";
        String password = "";
        while (true) {
            System.out.println(YELLOW+"Enter username:"+RESET);
            username = scanner.nextLine();
            if (username.equalsIgnoreCase("exit")) {
                System.out.println(RED+"Exiting login process."+RESET);
                return;
            }
            System.out.println(YELLOW+"Enter password:"+RESET);
            password = scanner.nextLine();
            if (password.equalsIgnoreCase("exit")) {
                System.out.println(RED+"Exiting login process."+RESET);
                return;
            }

            if (users.containsKey(username) && users.get(username).equals(password)) {
                System.out.println(GREEN+"Login successful."+RESET);
                userMenu();
                return;
            } else {
                System.out.println(RED+"Invalid username or password. Please try again or type 'exit' to cancel."+RESET);
            }
        }
    }

    static void adminLogin() {
        String username = "";
        String password = "";
        while (true) {
            System.out.println(MAGENTA+"Enter admin username:"+RESET);
            username = scanner.nextLine();
            if (username.equalsIgnoreCase("exit")) {
                System.out.println(CYAN_BOLD_BRIGHT+"Exiting admin login process."+RESET);
                return;
            }
            System.out.println(MAGENTA+"Enter admin password:"+RESET);
            password = scanner.nextLine();
            if (password.equalsIgnoreCase("exit")) {
                System.out.println(CYAN_BOLD_BRIGHT+"Exiting admin login process."+RESET);
                return;
            }

            if (adminCredentials.containsKey(username) && adminCredentials.get(username).equals(password)) {
                System.out.println(GREEN_BOLD_BRIGHT+"Admin Login successful."+RESET);
                adminMenu();
                return;
            } else {
                System.out.println(DARK_BLUE+"Invalid admin credentials. Please try again or type 'exit' to cancel."+RESET);
            }
        }
    }

    static void userMenu() {
        int choice = 0;
        while (choice != 3) {
	    System.out.println(bw+GREEN +"\n....................................................................Select an option.......................................................:"+RESET);
	System.out.println(blink+MAGENTA+"                                #%@");
        System.out.println("                               ####         @@%###%@@@@ ###%@@@@@  ####@   ####@  %#%@@%###  ###@      ###%@@@@@  ##%@@@@");
        System.out.println("                            @@##+#+            #+#     ##+#       ##++#+  #####   ##+   ##% ####      ##+#       %##");
        System.out.println("                               ###             ###     #++###%    #####  ## ##=  ###  %###  ###       #++###%    %###");
        System.out.println("                              ###             ###     #######%   ##  ##### ###  ### -@%    ###       #######%     +###@");
        System.out.println("                              ###    ##%     ###     ###        ###  ####  ##   ###       ##+#      ##+#           ###:");
        System.out.println("                             @##    @##%    @###    #%##%@@@@  @##  @###  @#%  @##       #%###%@@@ %%###%@@@  @@@@%##"+RESET);
        System.out.println("                                                                                                                                 ");
        System.out.println(blink+CYAN_BOLD_BRIGHT+"                                        ######");
        System.out.println("                                     @%## .###          ###%@@@@@@  #####   #%@   ###@ %@%%####%@@@");
        System.out.println("                                           ####         ##+#          ##+#####    ####      #+##");
        System.out.println("                                         ####          ##++####%       #++##     ##+#      ####");
        System.out.println("                                      #####            #########      ##++#      #*##     ####");
        System.out.println("                                   #####              ###           #####+##    ####      #+#");
        System.out.println("                                 @#######%%@  #%%@   %#####%%%@  #%##   ####=  %###      %###"+RESET);

            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    selectTemple();
                    break;
                case 2:
                    System.out.println(RED+"Exiting..."+RESET);
                    return;
                default:
                    System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }

    static void adminMenu() {
        int choice = 0;
        while (choice != 4) {
            System.out.println(bw+DARK_BLUE+"\nAdmin Menu:"+RESET);
            System.out.println(CYAN_BOLD_BRIGHT+"1. View Temples"+RESET);
            System.out.println(CYAN_BOLD_BRIGHT+"2. Add Temple"+RESET);
            System.out.println(CYAN_BOLD_BRIGHT+"3. Remove Temple"+RESET);
            System.out.println(CYAN_BOLD_BRIGHT+"4. Exit"+RESET);
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    viewTemples();
                    break;
                case 2:
                    addTemple();
                    break;
                case 3:
                    removeTemple();
                    break;
                case 4:
                    System.out.println(MAGENTA+"Exiting admin menu."+RESET);
                    return;
                default:
                    System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }

    static void viewTemples() {
        System.out.println(RED_BOLD_BRIGHT +"\nAvailable Temples:"+RESET);
        for (Temple temple : temples.values()) {
            temple.showInfo();
        }
    }

    static void addTemple() {
        System.out.println(DARK_BLUE+"Enter the name of the new temple:"+RESET);
        String name = scanner.nextLine();
        System.out.println(DARK_BLUE+"Enter the info for the new temple:"+RESET);
        String info = scanner.nextLine();

        Temple newTemple = new Temple(name, info);
        temples.put(name, newTemple);
        System.out.println(CYAN+"Temple added successfully."+RESET);
    }

    static void removeTemple() {
        System.out.println(MAGENTA+"Enter the name of the temple to remove:"+RESET);
        String name = scanner.nextLine();

        if (temples.containsKey(name)) {
            temples.remove(name);
            System.out.println(GREEN_BOLD_BRIGHT+"Temple removed successfully."+RESET);
        } else {
            System.out.println(YELLOW+"Temple not found."+RESET);
        }
    }

    static void selectTemple() {
        System.out.println(bw+YELLOW+"\nSelect a temple:"+RESET);
        int i = 1;
        for (String templeName : temples.keySet()) {
            System.out.println(CYAN_BOLD_BRIGHT+i + ". " + templeName);
            i++;
        }

        int templeChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String selectedTempleName = (String) temples.keySet().toArray()[templeChoice - 1];
        viewTemple(selectedTempleName);
    }

    static void viewTemple(String templeName) {
        Temple temple = temples.get(templeName);
        temple.showInfo();

        int choice = 0;
        while (choice != 2) {
            System.out.println(BLUE+"\n1. View Services"+RESET);
            System.out.println(BLUE+"2. Back"+RESET);
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    temple.showServices();
                    chooseService(temple);
                    break;
                case 2:
                    return;
                default:
                    System.out.println(RED+"Invalid choice."+RESET);
            }
        }
    }

    static void chooseService(Temple temple) {
        boolean anotherService = true;
        while (anotherService) {
            System.out.println(PURPLE_BOLD_BRIGHT+"Select a service number:"+RESET);
            int serviceChoice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Service service = temple.getService(serviceChoice);

            if (service != null) {
                System.out.println(DARK_BLUE+"Enter the number of persons:"+RESET);
                int persons = scanner.nextInt();
                totalCost += service.getPrice() * persons;
                System.out.println(CYAN+"Total cost: "+RESET + totalCost);

                System.out.println(CYAN_BOLD_BRIGHT+"Do you want another service? (yes/no)"+RESET);
                String answer = scanner.next();
                if (answer.equalsIgnoreCase("no")) {
                    anotherService = false; // Exit the loop and proceed to payment
                    proceedToPayment();
                }
            } else {
                System.out.println(RED+"Invalid service choice."+RESET);
            }
        }
    }

    static void proceedToPayment() {
        System.out.println(GREEN_BOLD_BRIGHT+"\nProceeding to payment."+RESET);
        System.out.println(PURPLE_BOLD_BRIGHT+"Total cost: "+RESET + totalCost);
        
        // Ask for payment mode
        System.out.println(BLUE+"Select a payment method:"+RESET);
        System.out.println(MAGENTA+"1. GPay"+RESET);
        System.out.println(MAGENTA+"2. PhonePe"+RESET);
        System.out.println(MAGENTA+"3. Paytm"+RESET);
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        String paymentMode = "";
        switch (paymentChoice) {
            case 1:
                paymentMode = "GPay";
                break;
            case 2:
                paymentMode = "PhonePe";
                break;
            case 3:
                paymentMode = "Paytm";
                break;
            default:
                System.out.println(CYAN_BOLD_BRIGHT+"Invalid choice. Payment failed."+RESET);
                return;
        }

        // Ask for mobile number and UPI ID
        String mobile = "";
        String upiId = "";
        boolean validPayment = false;

        while (!validPayment) {
            System.out.println(DARK_BLUE+"Enter your mobile number (10 digits):"+RESET);
            mobile = scanner.nextLine();
            System.out.println(DARK_BLUE+"Enter your UPI ID (mobilenumber@upi):"+RESET);
            upiId = scanner.nextLine();

            if (isValidMobileNumber(mobile) && isValidUpiId(upiId)) {
                validPayment = true;
                System.out.println(GREEN_BOLD_BRIGHT+"Payment successful via "+RESET + paymentMode);
                displayBillingDetails(paymentMode);
                break;
            } else {
                System.out.println(CYAN+"Invalid details. Please re-enter."+RESET);
            }
        }
    }

    static void displayBillingDetails(String paymentMode) {
	System.out.println(DARK_BLUE+BOLD+"\n--- ============================================================= BILL SUMMARY ============================================================ ---"+RESET);
         System.out.println(CYAN_BOLD_BRIGHT+"Services: "+RESET + totalCost); // You can add more specific details here
        System.out.println(CYAN_BOLD_BRIGHT+"Payment Mode: "+RESET + paymentMode);
        System.out.println(CYAN_BOLD_BRIGHT+"Total Amount: "+RESET + totalCost);
        
        System.out.println(BLUE+"\nDo you want to see temples again? (yes/no)"+RESET);
        String viewAgain = scanner.nextLine();
        if (viewAgain.equalsIgnoreCase("yes")) {
            viewTemples();
        } else {
            System.out.println(GREEN_BOLD_BRIGHT+"Thank you! Exiting..."+RESET);
        }
    }

    static boolean isValidPassword(String password) {
    return password.length() >= 6 &&
           password.matches(".*[A-Z].*") &&      // at least one uppercase
           password.matches(".*[a-z].*") &&      // at least one lowercase
           password.matches(".*[!@#$%^&].*");    // at least one special char
}


    static boolean isValidMobileNumber(String mobile) {
        return mobile.matches("\\d{10}");
    }

    static boolean isValidUpiId(String upiId) {
        return upiId.matches("\\d{10}@upi");
    }

    static String generateOTP() {
        Random rand = new Random();
        int otp = rand.nextInt(9000) + 1000;  // Generates a 4-digit OTP
        return String.valueOf(otp);
    }
}