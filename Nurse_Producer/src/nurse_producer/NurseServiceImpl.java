package nurse_producer;

import java.util.ArrayList;
import java.util.List;

public class NurseServiceImpl implements NurseService {
    private List<String> nurses = new ArrayList<>();

    @Override
    public void addNurse(String name, String department) {
        nurses.add(name + " - " + department);
        System.out.println("Nurse added: " + name + " (Department: " + department + ")");
    }

    @Override
    public void listNurses() {
        if (nurses.isEmpty()) {
            System.out.println("No nurses available.");
        } else {
            System.out.println("List of Nurses:");
            for (String nurse : nurses) {
                System.out.println(nurse);
            }
        }
    }
}
