package finance_producer;

import java.time.LocalDate;

public interface FinanceService {
    void addFinanceRecord(String type, double amount, String name, LocalDate date);
    void listFinanceRecords();
    void updateFinanceRecord(int id, String newType, double newAmount, String name, LocalDate date);
    void removeFinanceRecord(int id);
    void searchFinanceRecord(int id);
}
