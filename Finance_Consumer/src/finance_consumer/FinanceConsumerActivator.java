package finance_consumer;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import finance_producer.FinanceService;

public class FinanceConsumerActivator implements BundleActivator {
    private ServiceReference<?> serviceReference;
    private Scanner scanner;

    public void start(BundleContext context) throws Exception {
        serviceReference = context.getServiceReference(FinanceService.class.getName());

        if (serviceReference != null) {
            FinanceService financeService = (FinanceService) context.getService(serviceReference);
            
            if (financeService == null) {
                System.out.println("⚠️ Error: Finance Service could not be retrieved.");
                return;
            }

            scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                try {
                    System.out.println("\n--- Finance Management System ---");
                    System.out.println("1. Process Salary Payment");
                    System.out.println("2. List Transactions");
                    System.out.println("3. Update Transaction");
                    System.out.println("4. Remove Transaction");
                    System.out.println("5. Calculate Total Revenue");
                    System.out.println("6. Exit");
                    System.out.print("Enter choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            System.out.print("Enter Employee Name: ");
                            String employeeName = scanner.nextLine();
                            System.out.print("Enter Payment Amount: $");
                            double amount = scanner.nextDouble();
                            if (amount <= 0) {
                                System.out.println("⚠️ Error: Payment amount must be positive.");
                                break;
                            }
                            financeService.processSalaryPayment(employeeName, amount);
                            break;
                        case 2:
                            financeService.listTransactions();
                            break;
                        case 3:
                            System.out.print("Enter Transaction ID to Update: ");
                            int transactionId = scanner.nextInt();
                            System.out.print("Enter New Amount: $");
                            double newAmount = scanner.nextDouble();
                            if (newAmount <= 0) {
                                System.out.println("⚠️ Error: Amount must be positive.");
                                break;
                            }
                            financeService.updateTransaction(transactionId, newAmount);
                            break;
                        case 4:
                            System.out.print("Enter Transaction ID to Remove: ");
                            int removeId = scanner.nextInt();
                            financeService.removeTransaction(removeId);
                            break;
                        case 5:
                            financeService.calculateTotalRevenue();
                            break;
                        case 6:
                            running = false;
                            System.out.println("Exiting Finance Management System.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("⚠️ Invalid input! Please enter a valid number.");
                    scanner.nextLine(); // Clear invalid input
                }
            }
        } else {
            System.out.println("⚠️ Finance Service not found!");
        }
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Finance Consumer Stopped.");
        if (scanner != null) {
            scanner.close(); // Close scanner to prevent resource leak
        }
        if (serviceReference != null) {
            context.ungetService(serviceReference); // Release service reference
        }
    }
}
