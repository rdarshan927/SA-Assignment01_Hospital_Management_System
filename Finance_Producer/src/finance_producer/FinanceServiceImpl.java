package finance_producer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinanceServiceImpl implements FinanceService {
    private List<FinanceRecord> records = new ArrayList<>();
    private int recordIdCounter = 1;

    @Override
    public void addFinanceRecord(String type, double amount, String name, LocalDate date) {
        FinanceRecord record = new FinanceRecord(recordIdCounter++, type, amount, name, date);
        records.add(record);
        System.out.println("✅ Finance Record Added: " + record);
    }

    @Override
    public void listFinanceRecords() {
        if (records.isEmpty()) {
            System.out.println("⚠️ No finance records available.");
        } else {
            System.out.println("\n📋 List of Finance Records:");
            System.out.println("Finance Record ID \t Type \t\t Amount \t\t Name \t\t Date");
            for (FinanceRecord record : records) {
                System.out.println(record);
            }
        }
    }

    @Override
    public void updateFinanceRecord(int id, String newType, double newAmount, String name, LocalDate date) {
        Optional<FinanceRecord> recordOptional = findFinanceRecordById(id);
        if (recordOptional.isPresent()) {
            FinanceRecord record = recordOptional.get();
            record.setType(newType);
            record.setAmount(newAmount);
            System.out.println("✅ Finance Record " + id + " updated successfully.");
        } else {
            System.out.println("❌ Finance Record with ID " + id + " not found.");
        }
    }

    @Override
    public void removeFinanceRecord(int id) {
        Optional<FinanceRecord> recordOptional = findFinanceRecordById(id);
        if (recordOptional.isPresent()) {
            records.remove(recordOptional.get());
            System.out.println("✅ Finance Record with ID " + id + " removed successfully.");
        } else {
            System.out.println("❌ Finance Record with ID " + id + " not found.");
        }
    }

    @Override
    public void searchFinanceRecord(int id) {
        Optional<FinanceRecord> recordOptional = findFinanceRecordById(id);
        if (recordOptional.isPresent()) {
            System.out.println("🔎 Finance Record Found: " + recordOptional.get());
        } else {
            System.out.println("❌ Finance Record with ID " + id + " not found.");
        }
    }

    // Helper method to find a finance record by ID
    private Optional<FinanceRecord> findFinanceRecordById(int id) {
        return records.stream().filter(record -> record.getId() == id).findFirst();
    }
}
