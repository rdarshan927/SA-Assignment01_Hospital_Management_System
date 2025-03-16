package nurse_producer;

public class Nurse {
    private int id;
    private String name;
    private String email;
    private String phoneNo;
    private String department;

    public Nurse(int id, String name, String email, String phoneNo, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Override
    public String toString() {
        return id + " \t\t " + name + " \t\t\t " + email + " \t " + phoneNo +" \t\t " + department;
    }
}
