package doctor_producer;

public interface DoctorService {
    void addDoctor(String name, String specialization, String email, String phoneNo);
    void listDoctors();
    void updateDoctor(int id, String newName, String newSpecialization, String email, String phoneNo);
    void removeDoctor(int id);
    void searchDoctor(int id);
}
