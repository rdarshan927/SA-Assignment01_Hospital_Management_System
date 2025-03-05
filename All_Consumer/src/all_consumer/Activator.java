package all_consumer;

import finance_consumer.FinanceConsumerActivator;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import doctor_consumer.DoctorConsumerActivator;
import nurse_consumer.NurseConsumerActivator;
import security_consumer.SecurityConsumerActivator;

public class Activator implements BundleActivator {

    private final DoctorConsumerActivator doctor = new DoctorConsumerActivator();
    private final NurseConsumerActivator nurse = new NurseConsumerActivator();
    private final SecurityConsumerActivator security = new SecurityConsumerActivator();
    private final FinanceConsumerActivator finance = new FinanceConsumerActivator();
    private Scanner scanner;

    public void start(BundleContext bundleContext) {
        scanner = new Scanner(System.in);
        boolean running = true;
        
        String logo = """
        _    _             _       _   _                
        | |  | |           | |     | | (_)              
        | |__| |_   _ _ __ | | ___ | |_ _  ___  _ __    
        |  __  | | | | '_ \\| |/ _ \\| __| |/ _ \\| '_ \\   
        | |  | | |_| | |_) | | (_) | |_| | (_) | | | |  
        |_|  |_|\\__,_| .__/|_|\\___/ \\__|_|\\___/|_| |_|  
                    | |                                  
                    |_|                                  
        """;

        System.out.println(logo);
        
        while (running) {
            System.out.println("\t\t\t(¯`·._.··¸.-~*´¨¯¨`*·~-.SELECT AN ACTION.-~*´¨¯¨`*·~-.¸··._.·´¯)");
            System.out.println("");
            System.out.println("\t\t\t\t   1. Doctor Management");
            System.out.println("\t\t\t\t   2. Nurse Management");
            System.out.println("\t\t\t\t   3. Security Management");
            System.out.println("\t\t\t\t   4. Financial Management");
            System.out.println("\t\t\t\t   5. Exit");
            System.out.println("\t\t\t----------------------------------------------------------------");
            System.out.print("Enter your choice: ");

            try {
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Consume invalid input
                    continue;
                }

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> doctor.start(bundleContext);
                    case 2 -> nurse.start(bundleContext);
                    case 3 -> security.start(bundleContext);
                    case 4 -> finance.start(bundleContext);
                    case 5 -> running = false;
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(BundleContext bundleContext) {
        System.out.println("Hospital Management System is shutting down...");
        if (scanner != null) {
            scanner.close(); // Close scanner properly
        }
    }
}
