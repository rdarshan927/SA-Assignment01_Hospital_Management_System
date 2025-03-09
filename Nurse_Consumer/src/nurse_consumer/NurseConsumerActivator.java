package nurse_consumer;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import nurse_producer.NurseService;

public class NurseConsumerActivator implements BundleActivator {
    private ServiceReference<?> serviceReference;
    private Scanner scanner;

    public void start(BundleContext context) throws Exception {
        serviceReference = context.getServiceReference(NurseService.class.getName());

        if (serviceReference != null) {
            NurseService nurseService = (NurseService) context.getService(serviceReference);
            scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                try {
                    System.out.println("\n--- Nurse Management ---");
                    System.out.println("1. Add Nurse");
                    System.out.println("2. List Nurses");
                    System.out.println("3. Update Nurse");
                    System.out.println("4. Remove Nurse");
                    System.out.println("5. Search Nurse");
                    System.out.println("6. Exit");
                    System.out.print("Enter choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.print("Enter nurse name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter email: ");
                            String email = scanner.nextLine();
                            System.out.print("Enter phone no: ");
                            String phoneNo = scanner.nextLine();
                            System.out.print("Enter department: ");
                            String department = scanner.nextLine();
                            nurseService.addNurse(name, email, phoneNo, department);
                            break;
                        case 2:
                            nurseService.listNurses();
                            break;
                        case 3:
                            System.out.print("Enter nurse ID to update: ");
                            int nurseId = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter new name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter new email: ");
                            String newEmail = scanner.nextLine();
                            System.out.print("Enter new phone no: ");
                            String newPhoneNo = scanner.nextLine();
                            System.out.print("Enter new department: ");
                            String newDepartment = scanner.nextLine();
                            nurseService.updateNurse(nurseId, newName, newEmail, newPhoneNo, newDepartment);
                            break;
                        case 4:
                            System.out.print("Enter nurse ID to remove: ");
                            int removeId = scanner.nextInt();
                            nurseService.removeNurse(removeId);
                            break;
                        case 5:
                            System.out.print("Enter nurse ID to search: ");
                            int searchId = scanner.nextInt();
                            nurseService.searchNurse(searchId);
                            break;
                        case 6:
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.nextLine(); // Clear invalid input
                }
            }
        } else {
            System.out.println("⚠️ Nurse Service not found!");
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Nurse Consumer Stopped.");
        
     // Now explicitly start the 'all_consumer' bundle when Nurse Consumer stops
        Bundle allConsumerBundle = context.getBundle("all_consumer_bundle_id"); // Use actual bundle ID for all_consumer
        if (allConsumerBundle != null && allConsumerBundle.getState() != Bundle.ACTIVE) {
            allConsumerBundle.start();
            System.out.println("All Consumer Started.");
        }

        if (scanner != null) {
            scanner.close(); // Close scanner to prevent resource leak
        }
    }
}
