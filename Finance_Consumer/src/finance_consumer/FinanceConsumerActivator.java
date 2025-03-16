package finance_consumer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import finance_producer.FinanceService;

public class FinanceConsumerActivator implements BundleActivator {
    private ServiceReference<?> serviceReference;

    public void start(BundleContext context) throws Exception {
        serviceReference = context.getServiceReference(FinanceService.class.getName());
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (serviceReference != null) {
            FinanceService financeService = (FinanceService) context.getService(serviceReference);
            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n--- 💰 Finance Management ---");
                System.out.println("1. Add Finance Record");
                System.out.println("2. List Finance Records");
                System.out.println("3. Update Finance Record");
                System.out.println("4. Remove Finance Record");
                System.out.println("5. Search Finance Record");
                System.out.println("6. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter record type (Income/Expense): ");
                        String type = scanner.nextLine();
                        System.out.print("Enter amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter name/description: ");
                        String name = scanner.nextLine();

                        LocalDate date = null;
                        
                        while (date == null) {
                            System.out.print("Enter date (YYYY-MM-DD): ");
                            String dateInput = scanner.nextLine();
                            try {
                                date = LocalDate.parse(dateInput, dateFormatter); 
                            } catch (DateTimeParseException e) {
                                System.out.println("❌ Invalid date format! Please enter again (YYYY-MM-DD).");
                            }
                        }
//                        System.out.print("Enter date (YYYY-MM-DD): ");
//                        String dateInput = scanner.nextLine();
//                        LocalDate date = LocalDate.parse(dateInput, dateFormatter); 
                        financeService.addFinanceRecord(type, amount, name, date);
                        break;
                    case 2:
                        financeService.listFinanceRecords();
                        break;
                    case 3:
                        System.out.print("Enter record ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new type: ");
                        String newType = scanner.nextLine();
                        System.out.print("Enter new amount: ");
                        double newAmount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter name/description: ");
                        String newName = scanner.nextLine();

                        LocalDate newDate = null;
                        while (newDate == null) {
                            System.out.print("Enter date (YYYY-MM-DD): ");
                            String dateInput = scanner.nextLine();
                            try {
                                newDate = LocalDate.parse(dateInput, dateFormatter); 
                            } catch (DateTimeParseException e) {
                                System.out.println("❌ Invalid date format! Please enter again (YYYY-MM-DD).");
                            }
                        }
                        
                        financeService.updateFinanceRecord(updateId, newType, newAmount, newName, newDate);
                        break;
                    case 4:
                        System.out.print("Enter record ID to remove: ");
                        int removeId = scanner.nextInt();
                        scanner.nextLine();
                        financeService.removeFinanceRecord(removeId);
                        break;
                    case 5:
                        System.out.print("Enter record ID to search: ");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();
                        financeService.searchFinanceRecord(searchId);
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
            scanner.close();
        } else {
            System.out.println("⚠️ Finance Service not found!");
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Finance Consumer Stopped.");
        
        Bundle allConsumerBundle = context.getBundle("all_consumer_bundle_id"); // Use actual bundle ID for all_consumer
        if (allConsumerBundle != null && allConsumerBundle.getState() != Bundle.ACTIVE) {
            allConsumerBundle.start();
            System.out.println("All Consumer Started.");
        }

    }
}
