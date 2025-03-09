package nurse_producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NurseServiceImpl implements NurseService {
    private List<Nurse> nurses = new ArrayList<>();
    private int nurseIdCounter = 1; // Auto-increment ID for nurses

    @Override
    public void addNurse(String name, String email, String phoneNo, String department) {
        Nurse nurse = new Nurse(nurseIdCounter++, name, email, phoneNo, department);
        nurses.add(nurse);
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
            nurse.setDepartment(newDepartment);
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
}
