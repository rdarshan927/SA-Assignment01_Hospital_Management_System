package doctor_producer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {
    private static final String FILE_NAME = "/home/rdarshan927/eclipse-workspace3/SA-Assignment01_Hospital_Management_System/Doctor_Producer/src/doctor_producer/doctor_details.txt";
    private List<Doctor> doctors = new ArrayList<>();
    private int doctorIdCounter = 1; // Auto-increment ID for doctors

    public DoctorServiceImpl() {
        loadDoctorsFromFile(); // Load doctors from file at startup
    }

    @Override
    public synchronized void addDoctor(String name, String specialization, String email, String phoneNo) {
        if (name == null || name.trim().isEmpty() || specialization == null || specialization.trim().isEmpty()) {
            System.out.println("❌ Name and specialization cannot be empty.");
            return;
        }

        Doctor doctor = new Doctor(doctorIdCounter++, name, specialization, email, phoneNo);
        doctors.add(doctor);
        saveDoctorsToFile(); // Save to file
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
        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setName(newName);
            doctor.setSpecialization(newSpecialization);
            doctor.setEmail(email);
            doctor.setPhoneNo(phoneNo);
            saveDoctorsToFile(); // Save changes to file
            System.out.println("✅ Doctor " + id + " updated successfully.");
        } else {
            System.out.println("❌ Doctor with ID " + id + " not found.");
        }
    }

    @Override
    public synchronized void removeDoctor(int id) {
        Optional<Doctor> doctorOptional = findDoctorById(id);
        if (doctorOptional.isPresent()) {
            doctors.remove(doctorOptional.get());
            saveDoctorsToFile(); // Save changes to file
            System.out.println("✅ Doctor with ID " + id + " removed successfully.");
        } else {
            System.out.println("❌ Doctor with ID " + id + " not found.");
        }
    }

    @Override
    public void searchDoctor(int id) {
        Optional<Doctor> doctorOptional = findDoctorById(id);
        if (doctorOptional.isPresent()) {
            System.out.println("🔎 Doctor Found: " + doctorOptional.get());
        } else {
            System.out.println("❌ Doctor with ID " + id + " not found.");
        }
    }

    // Helper method to find a doctor by ID
    private Optional<Doctor> findDoctorById(int id) {
        return doctors.stream().filter(doctor -> doctor.getId() == id).findFirst();
    }

    // Load doctors from the file
    private void loadDoctorsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String specialization = parts[2];
                    String email = parts[3];
                    String phoneNo = parts[4];
                    Doctor doctor = new Doctor(id, name, specialization, email, phoneNo);
                    doctors.add(doctor);
                    doctorIdCounter = Math.max(doctorIdCounter, id + 1); // Ensure the ID counter is correct
                }
            }
        } catch (IOException e) {
            System.out.println("⚠️ Error loading doctor data from file: " + e.getMessage());
        }
    }

    // Save all doctors to the file
    private void saveDoctorsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Doctor doctor : doctors) {
                writer.write(doctor.getId() + "," + doctor.getName() + "," + doctor.getSpecialization() + "," +
                        doctor.getEmail() + "," + doctor.getPhoneNo() + "\n");
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving doctor data to file: " + e.getMessage());
        }
    }
}
