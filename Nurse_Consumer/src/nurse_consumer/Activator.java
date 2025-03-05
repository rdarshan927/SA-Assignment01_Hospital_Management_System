package nurse_consumer;

import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import nurse_producer.NurseService;

public class NurseConsumerActivator implements BundleActivator {
    private ServiceReference<?> serviceReference;

    public void start(BundleContext context) throws Exception {
        serviceReference = context.getServiceReference(NurseService.class.getName());
        if (serviceReference != null) {
            NurseService nurseService = (NurseService) context.getService(serviceReference);
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n--- Nurse Management ---");
                System.out.println("1. Add Nurse");
                System.out.println("2. List Nurses");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter nurse name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter department: ");
                        String department = scanner.nextLine();
                        nurseService.addNurse(name, department);
                        break;
                    case 2:
                        nurseService.listNurses();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } else {
            System.out.println("⚠️ Nurse Service not found!");
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Nurse Consumer Stopped.");
    }
}
