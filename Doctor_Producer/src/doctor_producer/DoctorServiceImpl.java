package doctor_producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {
    private List<Doctor> doctors = new ArrayList<>();
    private int doctorIdCounter = 1; // Auto-increment ID for doctors

    @Override
    public synchronized void addDoctor(String name, String specialization, String email, String phoneNo) {
        if (name == null || name.trim().isEmpty() || specialization == null || specialization.trim().isEmpty()) {
            System.out.println("❌ Name and specialization cannot be empty.");
            return;
        }

        Doctor doctor = new Doctor(doctorIdCounter++, name, specialization, email, phoneNo);
        doctors.add(doctor);
        System.out.println("✅ Doctor added: " + doctor.getId() + " - " + name + " (Specialization: " + specialization + ")");
    }

    @Override
    public void listDoctors() {
        if (doctors.isEmpty()) {
            System.out.println("⚠️ No doctors available.");
        } else {
            System.out.println("\n📋 List of Doctors:");
            System.out.println("Doctor ID \t Name \t\t Specialization \t Email \t\t\t Phone No");
            
            for (Doctor doctor : doctors) {
                System.out.println(doctor);
            }
        }
    }

    @Override
    public synchronized void updateDoctor(int id, String newName, String newSpecialization, String email, String phoneNo) {
        Optional<Doctor> doctorOptional = findDoctorById(id);
        doctorOptional.ifPresentOrElse(doctor -> {
            doctor.setName(newName);
            doctor.setSpecialization(newSpecialization);
            System.out.println("✅ Doctor " + id + " updated successfully.");
        }, () -> System.out.println("❌ Doctor with ID " + id + " not found."));
    }

    @Override
    public synchronized void removeDoctor(int id) {
        Optional<Doctor> doctorOptional = findDoctorById(id);
        doctorOptional.ifPresentOrElse(doctor -> {
            doctors.remove(doctor);
            System.out.println("✅ Doctor with ID " + id + " removed successfully.");
        }, () -> System.out.println("❌ Doctor with ID " + id + " not found."));
    }

    @Override
    public void searchDoctor(int id) {
        Optional<Doctor> doctorOptional = findDoctorById(id);
        doctorOptional.ifPresentOrElse(doctor -> {
            System.out.println("🔎 Doctor Found: " + doctor);
        }, () -> System.out.println("❌ Doctor with ID " + id + " not found."));
    }

    // Helper method to find a doctor by ID
    private Optional<Doctor> findDoctorById(int id) {
        return doctors.stream().filter(doctor -> doctor.getId() == id).findFirst();
    }
}
