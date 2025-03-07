package nurse_producer;

public interface NurseService {
    void addNurse(String name, String department);
    void listNurses();
    void updateNurse(int id, String newName, String newDepartment);
    void removeNurse(int id);
    void searchNurse(int id);
}
