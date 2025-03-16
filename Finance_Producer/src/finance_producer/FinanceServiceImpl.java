package finance_producer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FinanceServiceImpl implements FinanceService {
    private static final String FILE_NAME = "/home/rdarshan927/eclipse-workspace3/SA-Assignment01_Hospital_Management_System/Finance_Producer/src/finance_producer/finance_details.txt";
    private List<FinanceRecord> records = new ArrayList<>();
    private int recordIdCounter = 1;

    public FinanceServiceImpl() {
        loadFinanceRecords();
    }

    @Override
    public void addFinanceRecord(String type, double amount, String name, LocalDate date) {
        FinanceRecord record = new FinanceRecord(recordIdCounter++, type, amount, name, date);
        records.add(record);
        saveFinanceRecords();
        System.out.println("✅ Finance Record Added: " + record);
    }

    @Override
    public void listFinanceRecords() {
        if (records.isEmpty()) {
            System.out.println("⚠️ No finance records available.");
        } else {
            System.out.println("\n📋 List of Finance Records:");
            System.out.println("Finance Record ID \t Type \t\t Amount \t Name \t\t Date");
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
            record.setName(name);
            record.setDate(date);
            saveFinanceRecords();
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
            saveFinanceRecords();
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

    // Save records to file
    private void saveFinanceRecords() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (FinanceRecord record : records) {
                writer.write(record.getId() + "," + record.getType() + "," + record.getAmount() + "," + record.getName() + "," + record.getDate());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving finance records: " + e.getMessage());
        }
    }

    // Load records from file
    private void loadFinanceRecords() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String type = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    String name = parts[3];
                    LocalDate date = LocalDate.parse(parts[4]);

                    records.add(new FinanceRecord(id, type, amount, name, date));
                    recordIdCounter = Math.max(recordIdCounter, id + 1); // Ensure ID counter stays correct
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading finance records: " + e.getMessage());
        }
    }
}
