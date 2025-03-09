package security_producer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Security {
    private String SecurityID;
    private String name;
    private String contact;
    private String email;
    private List<String> assignedShifts;

    public Security(String SecurityID, String name, String email,String contact) {
        super();
    	this.SecurityID = SecurityID;
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.assignedShifts = new ArrayList<>();
    }

	public String getSecurityID() {
		return SecurityID;
	}

	public void setSecurityID(String SecurityID) {
		this.SecurityID = SecurityID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAssignedCourses(List<String> Shifts) {
		this.assignedShifts = Shifts;
	}

	public List<String> getAssignedCourses() {
		return this.assignedShifts;
	}


}
