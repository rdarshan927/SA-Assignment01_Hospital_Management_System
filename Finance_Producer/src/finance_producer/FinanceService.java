package finance_producer;

public interface FinanceService {
    void processSalaryPayment(String employeeName, double amount);
    void listTransactions();
    void updateTransaction(int id, double newAmount);
    void removeTransaction(int id);
    double calculateTotalRevenue();
}
