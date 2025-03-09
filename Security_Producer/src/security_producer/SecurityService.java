package security_producer;

import java.util.List;


public interface SecurityService {
    public void displaySecuritys();

    void createSecurity();

    void editSecurity();

    void assignCourses(String SecurityID);

    void deleteSecurity(String SecurityID);
    
    void searchSecurity(String SecurityID);
    
    List<Security> getAllSecuritys();
}
