package doctor_producer;

public class Doctor {
    private int id;
    private String name;
    private String email;
    private String phoneNo;
    private String specialization;

    public Doctor(int id, String name, String specialization, String email, String phoneNo) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
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
        return id + " \t\t " + name + " \t " + specialization + " \t\t " + email + " \t " + phoneNo ;
    }
}
