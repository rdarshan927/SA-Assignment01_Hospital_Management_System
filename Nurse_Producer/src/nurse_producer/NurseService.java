package nurse_producer;

public interface NurseService {
    void addNurse(String name, String department);
    void listNurses();
}
