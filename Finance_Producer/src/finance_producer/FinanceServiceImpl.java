package finance_producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinanceServiceImpl implements FinanceService {
    private List<FinanceTransaction> transactions = new ArrayList<>();
    private int transactionIdCounter = 1; // Auto-increment ID for transactions

    @Override
    public void processSalaryPayment(String employeeName, double amount) {
        FinanceTransaction transaction = new FinanceTransaction(transactionIdCounter++, employeeName, amount);
        transactions.add(transaction);
        System.out.println("✅ Salary Processed: " + employeeName + " - $" + amount);
    }

    @Override
    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("⚠️ No financial transactions recorded.");
        } else {
            System.out.println("\n📋 List of Financial Transactions:");
            for (FinanceTransaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    @Override
    public void updateTransaction(int id, double newAmount) {
        Optional<FinanceTransaction> transactionOptional = findTransactionById(id);
        if (transactionOptional.isPresent()) {
            FinanceTransaction transaction = transactionOptional.get();
            transaction.setAmount(newAmount);
            System.out.println("✅ Transaction " + id + " updated successfully.");
        } else {
            System.out.println("❌ Transaction with ID " + id + " not found.");
        }
    }

    @Override
    public void removeTransaction(int id) {
        Optional<FinanceTransaction> transactionOptional = findTransactionById(id);
        if (transactionOptional.isPresent()) {
            transactions.remove(transactionOptional.get());
            System.out.println("✅ Transaction with ID " + id + " removed successfully.");
        } else {
            System.out.println("❌ Transaction with ID " + id + " not found.");
        }
    }

    @Override
    public double calculateTotalRevenue() {
        double totalRevenue = transactions.stream().mapToDouble(FinanceTransaction::getAmount).sum();
        System.out.println("💰 Total Revenue: $" + totalRevenue);
        return totalRevenue;
    }

    // Helper method to find a transaction by ID
    private Optional<FinanceTransaction> findTransactionById(int id) {
        return transactions.stream().filter(transaction -> transaction.getId() == id).findFirst();
    }
}
