package doctor_consumer;

import java.util.Scanner;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import doctor_producer.DoctorService;

public class DoctorConsumerActivator implements BundleActivator {
    private ServiceReference<?> serviceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        // Retrieve the DoctorService reference from the OSGi context
        serviceReference = context.getServiceReference(DoctorService.class.getName());
        
        if (serviceReference != null) {
            DoctorService doctorService = (DoctorService) context.getService(serviceReference);
            try (Scanner scanner = new Scanner(System.in)) {  // Ensuring scanner is closed automatically after use
                boolean running = true;

                while (running) {
                    System.out.println("\n--- Doctor Management ---");
                    System.out.println("1. Add Doctor");
                    System.out.println("2. List Doctors");
                    System.out.println("3. Update Doctor");
                    System.out.println("4. Remove Doctor");
                    System.out.println("5. Search Doctor");
                    System.out.println("6. Exit");
                    System.out.print("Enter choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.print("Enter doctor name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter specialization: ");
                            String specialization = scanner.nextLine();
                            System.out.println("Enter email id: ");
                            String email = scanner.nextLine();
                            System.out.println("Enter phone no: ");
                            String phoneNo = scanner.nextLine();
                            doctorService.addDoctor(name, specialization, email, phoneNo);
                            break;
                        case 2:
                            doctorService.listDoctors();
                            break;
                        case 3:
                            System.out.print("Enter doctor ID to update: ");
                            int updateId = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            System.out.print("Enter new doctor name: ");
                            String newName = scanner.nextLine();
                            System.out.print("Enter new specialization: ");
                            String newSpecialization = scanner.nextLine();
                            System.out.println("Enter new email id: ");
                            String newemail = scanner.nextLine();
                            System.out.println("Enter new phone no: ");
                            String newphoneNo = scanner.nextLine();
                            doctorService.updateDoctor(updateId, newName, newSpecialization, newemail, newphoneNo);
                            break;
                        case 4:
                            System.out.print("Enter doctor ID to remove: ");
                            int removeId = scanner.nextInt();
                            doctorService.removeDoctor(removeId);
                            break;
                        case 5:
                            System.out.print("Enter doctor ID to search: ");
                            int searchId = scanner.nextInt();
                            doctorService.searchDoctor(searchId);
                            break;
                        case 6:
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid choice. Try again.");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        } else {
            System.out.println("⚠️ Doctor Service not found!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Doctor Consumer Stopped.");
        
     // Now explicitly start the 'all_consumer' bundle when Nurse Consumer stops
        Bundle allConsumerBundle = context.getBundle("all_consumer_bundle_id"); // Use actual bundle ID for all_consumer
        if (allConsumerBundle != null && allConsumerBundle.getState() != Bundle.ACTIVE) {
            allConsumerBundle.start();
            System.out.println("All Consumer Started.");
        }
        
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}
