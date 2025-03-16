package nurse_producer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NurseServiceImpl implements NurseService {
    private static final String FILE_NAME = "/home/rdarshan927/eclipse-workspace3/SA-Assignment01_Hospital_Management_System/Nurse_Producer/src/nurse_producer/nurse_details.txt";
    private List<Nurse> nurses = new ArrayList<>();
    private int nurseIdCounter = 1; // Auto-increment ID for nurses

    public NurseServiceImpl() {
        loadNursesFromFile();
    }

    @Override
    public void addNurse(String name, String email, String phoneNo, String department) {
        Nurse nurse = new Nurse(nurseIdCounter++, name, email, phoneNo, department);
        nurses.add(nurse);
        saveNursesToFile();  // Save to file
        System.out.println("✅ Nurse added: " + nurse.getId() + " - " + name + " (Department: " + department + ")");
    }

    @Override
    public void listNurses() {
        if (nurses.isEmpty()) {
            System.out.println("⚠️ No nurses available.");
        } else {
            System.out.println("\n📋 List of Nurses:");
            System.out.println("Nurse ID \t Name \t\t\t Email \t\t\t Phone No \t\t Department");
            for (Nurse nurse : nurses) {
                System.out.println(nurse);
            }
        }
    }

    @Override
    public void updateNurse(int id, String newName, String newEmail, String newPhoneNo, String newDepartment) {
        Optional<Nurse> nurseOptional = findNurseById(id);
        if (nurseOptional.isPresent()) {
            Nurse nurse = nurseOptional.get();
            nurse.setName(newName);
            nurse.setEmail(newEmail);
            nurse.setPhoneNo(newPhoneNo);
            nurse.setDepartment(newDepartment);
            saveNursesToFile();  // Save to file after update
            System.out.println("✅ Nurse " + id + " updated successfully.");
        } else {
            System.out.println("❌ Nurse with ID " + id + " not found.");
        }
    }

    @Override
    public void removeNurse(int id) {
        Optional<Nurse> nurseOptional = findNurseById(id);
        if (nurseOptional.isPresent()) {
            nurses.remove(nurseOptional.get());
            saveNursesToFile();  // Save to file after removal
            System.out.println("✅ Nurse with ID " + id + " removed successfully.");
        } else {
            System.out.println("❌ Nurse with ID " + id + " not found.");
        }
    }

    @Override
    public void searchNurse(int id) {
        Optional<Nurse> nurseOptional = findNurseById(id);
        if (nurseOptional.isPresent()) {
            System.out.println("🔎 Nurse Found: " + nurseOptional.get());
        } else {
            System.out.println("❌ Nurse with ID " + id + " not found.");
        }
    }

    // Helper method to find a nurse by ID
    private Optional<Nurse> findNurseById(int id) {
        return nurses.stream().filter(nurse -> nurse.getId() == id).findFirst();
    }

    // Load nurses from the file
    private void loadNursesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String email = parts[2];
                    String phoneNo = parts[3];
                    String department = parts[4];
                    Nurse nurse = new Nurse(id, name, email, phoneNo, department);
                    nurses.add(nurse);
                    nurseIdCounter = Math.max(nurseIdCounter, id + 1);  // Ensure the ID counter is correct
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error loading nurse data from file: " + e.getMessage());
        }
    }

    // Save all nurses to the file
    private void saveNursesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Nurse nurse : nurses) {
                writer.write(nurse.getId() + "," + nurse.getName() + "," + nurse.getEmail() + "," +
                        nurse.getPhoneNo() + "," + nurse.getDepartment() + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving nurse data to file: " + e.getMessage());
        }
    }
}