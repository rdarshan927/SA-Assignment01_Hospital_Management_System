package security_consumer;

import java.util.Scanner;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import security_producer.SecurityService;

public class Security_Consumer_Activator implements BundleActivator {
    
    private ServiceReference serviceRef;
    private Scanner scanner;
    
    // ANSI Color Codes for Professional Styling
    private static final String BOLD = "\033[1m";
    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[32m";
    private static final String WHITE = "\033[37m";
    private static final String CYAN = "\033[1;35m";
    
    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Security Consumer Module Activated");
        
        serviceRef = context.getServiceReference(SecurityService.class.getName());
        SecurityService securityService = (SecurityService) context.getService(serviceRef);
        scanner = new Scanner(System.in);
        
        String header = "SECURITY MANAGEMENT PANEL";
        System.out.println("\n" + BOLD + CYAN + "╔════════════════════════════════════════════════╗");
        System.out.println("║               " + header + "        ║");
        System.out.println("╚════════════════════════════════════════════════╝" + RESET);
        
        while (true) {
            displayMenu();
            
            System.out.print("\n" + BOLD + "Enter your choice: " + RESET);
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            System.out.println("============================================================");
            
            switch (option) {
                case 1:
                    securityService.createSecurity();
                    break;
                case 2:
                    securityService.displaySecuritys();
                    break;
                case 3:
                    securityService.editSecurity();
                    break;
                case 4:
                    System.out.print("\n" + BOLD + GREEN + "Enter Security ID to remove: " + RESET);
                    String removeId = scanner.nextLine();
                    securityService.deleteSecurity(removeId);
                    break;
                case 5:
                    System.out.print("\n" + BOLD + GREEN + "Enter Security ID for shift assignment: " + RESET);
                    String shiftId = scanner.nextLine();
                    securityService.assignCourses(shiftId);
                    break;
                case 6:
                    System.out.print("\n" + BOLD + GREEN + "Enter Security ID to search: " + RESET);
                    String searchId = scanner.nextLine();
                    securityService.searchSecurity(searchId);
                    break;
                case 7:
                    System.out.println("\n" + GREEN + "Exiting..." + RESET);
                    return;
                default:
                    System.out.println("\n" + GREEN + "Invalid selection! Please choose a valid option." + RESET);
            }
        }
    }
    
    private void displayMenu() {
        System.out.println("\n" + BOLD + GREEN + "╔═════════════════════════════════════════════════════════╗");
        System.out.println("║                 SECURITY MANAGEMENT MENU                ║");
        System.out.println("╠═════╦═══════════════════════════════════════════════════╣");
        System.out.println("║  1  ║ Register a New Security Personnel                 ║");
        System.out.println("║  2  ║ View Security Personnel List                      ║");
        System.out.println("║  3  ║ Modify Security Personnel Details                 ║");
        System.out.println("║  4  ║ Remove a Security Personnel                       ║");
        System.out.println("║  5  ║ Allocate Shifts                                   ║");
        System.out.println("║  6  ║ Search a Security Personnel                       ║");
        System.out.println("║  7  ║ Exit                                              ║");
        System.out.println("╚═════╩═══════════════════════════════════════════════════╝" + RESET);
    }
    
    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Security Consumer Module Deactivated");
        
     // Now explicitly start the 'all_consumer' bundle when Nurse Consumer stops
        Bundle allConsumerBundle = context.getBundle("all_consumer_bundle_id"); // Use actual bundle ID for all_consumer
        if (allConsumerBundle != null && allConsumerBundle.getState() != Bundle.ACTIVE) {
            allConsumerBundle.start();
            System.out.println("All Consumer Started.");
        }
        
        if (scanner != null) {
            scanner.close(); // Close scanner to prevent resource leak
        }
        context.ungetService(serviceRef);
    }
}
