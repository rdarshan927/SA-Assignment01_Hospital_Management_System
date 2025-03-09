package security_producer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class SecurityServiceImpl implements SecurityService {
	private static final String FILE_NAME = ".\\Security_info.txt";
    private Scanner scan;
    private String ANSI_BOLD = "\u001B[1m";
    private String ANSI_RESET = "\u001B[0m";
    private String ANSI_YELLOW = "\u001B[33m";
    private String ANSI_BLUE = "\u001B[34m";
    private String ANSI_GREEN = "\u001B[32m";
    private String ANSI_RED = "\u001B[31m";
    
    private List<Security> registeredSecuritys;

    public SecurityServiceImpl() {
    	scan = new Scanner(System.in);
    	registeredSecuritys = new ArrayList<>();
    }

    @Override
    public void createSecurity() {
        String SecurityID, name, email, contact;

        System.out.println("\n \u001B[33m \u001B[1mRegister New Security To System\u001B[0m");

        System.out.print("\n \u001B[1m Security's ID: \u001B[0m");
        SecurityID = scan.nextLine();

        System.out.print(" \u001B[1m Security's Name: \u001B[0m");
        name = scan.nextLine();

        System.out.print(" \u001B[1m Security's Email: \u001B[0m");
        email = scan.nextLine();

        System.out.print(" \u001B[1m Security's Contact Number: \u001B[0m");
        contact = scan.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(SecurityID + "," + name + "," + email + "," + contact + "\n");

            System.out.println("\n \u001B[32m Security with ID " + SecurityID + " registered successfully.\u001B[0m");
        } catch (IOException e) {
            System.err.println("\n \u001B[31m Error registering new Security to system: " + e.getMessage() + "\u001B[0m");
        }
    }

    
    
    @Override
    public void displaySecuritys() {
        // Define the content width and the title
        int contentWidth = 80; // Adjust as needed
        String title = "  Securitys' Details";

        // Calculate the padding for center alignment
        int titlePadding = (contentWidth - title.length()) / 2;
        
        System.out.println();
        System.out.print(ANSI_BOLD + ANSI_YELLOW);
        for (int i = 0; i < titlePadding; i++) {
            System.out.print(" ");
        }
        System.out.print(title);
        System.out.println(ANSI_RESET);

        String lineSeparator = "  +------------+----------------------+---------------------------+-----------------+%n";
        String widthFormat = "  | %-10s | %-20s | %-25s | %-15s |%n";

        System.out.printf(lineSeparator);
        System.out.printf(widthFormat, "SecurityID", "Name", "Email", "Contact No");
        System.out.printf(lineSeparator);

        List<Security> SecurityList = getAllSecuritys();

        for (Security Security : SecurityList) {
            String formattedString = String.format(widthFormat, Security.getSecurityID(), Security.getName(), Security.getEmail(), Security.getContact());
            System.out.printf(formattedString);
        }

        System.out.printf(lineSeparator);
    }



    @Override
    public void editSecurity() {
        String SecurityID, newName, newContact, newEmail;
        
        System.out.println("\n \u001B[33m \u001B[1mEdit Security Profile\u001B[0m \n");
       
        System.out.print(ANSI_BOLD +"  Enter Security's ID: " + ANSI_RESET);
        SecurityID = scan.nextLine();

        Security SecurityToEdit = findSecurityById(SecurityID);
        if (SecurityToEdit != null) {
            // Display existing details
            System.out.println("\n  Current Details:");
            System.out.println(ANSI_BOLD + "  1. Name" + ANSI_RESET + "  - " + SecurityToEdit.getName());
            System.out.println(ANSI_BOLD + "  2. Email" + ANSI_RESET + " - " + SecurityToEdit.getEmail());
            System.out.println(ANSI_BOLD + "  3. Contact" + ANSI_RESET + " - " + SecurityToEdit.getContact());

            // Prompt user for changes
            System.out.print("\n" + ANSI_BLUE + "  Enter the indexes of the properties you want to edit (comma-separated): " + ANSI_RESET);
            String indexesInput = scan.nextLine();
            String[] indexes = indexesInput.split(",");

            // Edit the chosen properties
            for (String index : indexes) {
                int propertyIndex = Integer.parseInt(index.trim());
                switch (propertyIndex) {
                    case 1:
                        System.out.print(ANSI_BOLD + "  Enter new name" + ANSI_RESET + ": ");
                        newName = scan.nextLine();
                        SecurityToEdit.setName(newName);
                        break;
                    case 2:
                        System.out.print(ANSI_BOLD + "  Enter new email" + ANSI_RESET + ": ");
                        newEmail = scan.nextLine();
                        SecurityToEdit.setEmail(newEmail);
                        break;
                    case 3:
                        System.out.print(ANSI_BOLD + "  Enter new contact" + ANSI_RESET + ": ");
                        newContact = scan.nextLine();
                        SecurityToEdit.setContact(newContact);
                        break;
                    default:
                        System.out.println("  Invalid index: " + propertyIndex);
                }
            }
            
            // Update the file with the edited details
            updateSecurityInFile(SecurityToEdit);
            System.out.println();
            System.out.println(ANSI_GREEN +"  Security details updated for ID " + SecurityID + ":" + ANSI_RESET);
            System.out.println("  Name: " + SecurityToEdit.getName());
            System.out.println("  Email: " + SecurityToEdit.getEmail());
            System.out.println("  Contact: " + SecurityToEdit.getContact());
        } else {
        	 System.err.println("\n \u001B[31m Security not found with ID: " + SecurityID + "\u001B[0m");
        }
    }

   
    
    @Override
    public void deleteSecurity(String SecurityID) {
        try {
            List<Security> SecurityListToDelete = getAllSecuritys();

            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, false));

            for (Security Security : SecurityListToDelete) {
                if (!Security.getSecurityID().equals(SecurityID)) {
                    writer.write(Security.getSecurityID() + "," + Security.getName() + "," + Security.getEmail() + "," + Security.getContact() + "\n");
                }
            }

            writer.close();
            System.out.println("\n \u001B[32m Security with ID " + SecurityID + " Deleted Successfully.\u001B[0m");
        } catch (IOException e) {
        	 System.err.println("\n \u001B[31m Error deleting Security: " + e.getMessage() + "\u001B[0m");
        }
    }
    
    
    @Override
    public List<Security> getAllSecuritys() {
        List<Security> SecurityList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_NAME)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String SecurityID = parts[0];
                String name = parts[1];
                String email = parts[2];
                String contact = parts[3];
                Security Security = new Security(SecurityID, name, email, contact);
                SecurityList.add(Security);
            }
        } catch (IOException e) {
            System.err.println("Error reading Security data from file: " + e.getMessage());
        }
        return SecurityList;
    }
    
    @Override
    public void assignCourses(String SecurityID) {
        Security Security = findSecurityById(SecurityID);
        if (Security != null) {
                System.out.print("\n" + ANSI_BLUE + "  Enter Shifts to assign (comma-separated): " + ANSI_RESET);
                String ShiftsInput = scan.nextLine();
                List<String> Shifts = Arrays.asList(ShiftsInput.split("\\s*,\\s*"));

                // Update the assigned Shifts for the Security
                List<String> currentShifts = Security.getAssignedCourses();
                currentShifts.addAll(Shifts);
                Security.setAssignedCourses(currentShifts);

                // Update the file with the assigned Shifts
                updateSecurityInFile(Security);

                System.out.println(ANSI_GREEN + "  Shifts assigned successfully." + ANSI_RESET);
            
        } else {
            System.err.println("\n" + ANSI_RED + "  Security not found with ID: " + SecurityID + ANSI_RESET);
        }
    }

   
    private Security findSecurityById(String SecurityID) {
        List<Security> SecurityList = getAllSecuritys();
        for (Security Security : SecurityList) {
            if (Security.getSecurityID().equals(SecurityID)) {
                return Security;
            }
        }
        return null;
    }
    
    private void updateSecurityInFile(Security updatedSecurity) {
        List<Security> SecurityList = getAllSecuritys();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_NAME), false))) {
            for (Security Security : SecurityList) {
                if (Security.getSecurityID().equals(updatedSecurity.getSecurityID())) {
                    // Write the updated record with assigned Shifts
                    writer.write(updatedSecurity.getSecurityID() + "," +
                            updatedSecurity.getName() + "," +
                            updatedSecurity.getEmail() + "," +
                            updatedSecurity.getContact() + "," +
                            String.join(",", updatedSecurity.getAssignedCourses()) + "\n");
                } else {
                    // Write the unchanged record
                    writer.write(Security.getSecurityID() + "," +
                            Security.getName() + "," +
                            Security.getEmail() + "," +
                            Security.getContact() + "," +
                            String.join(",", Security.getAssignedCourses()) + "\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error updating Security data in file: " + e.getMessage());
        }
    }

	@Override
	public void searchSecurity(String SecurityID) {
        Security Security = findSecurityById(SecurityID);
        if (Security != null) {
        	System.out.println("\n  Security Details:");
            System.out.println(ANSI_BOLD + "  Security ID" + ANSI_RESET + "    - " + Security.getSecurityID());
            System.out.println(ANSI_BOLD + "  Name" + ANSI_RESET + "          - " + Security.getName());
            System.out.println(ANSI_BOLD + "  Email" + ANSI_RESET + "         - " + Security.getEmail());
            System.out.println(ANSI_BOLD + "  Contact" + ANSI_RESET + "       - " + Security.getContact());

            List<String> assignedShifts = Security.getAssignedCourses();
            if (!assignedShifts.isEmpty()) {
                System.out.println(ANSI_BOLD + "  Assigned Shifts" + ANSI_RESET + " - " + String.join(", ", assignedShifts));
            }
        } else {
            System.err.println("\n \u001B[31m Security not found with ID: " + SecurityID + "\u001B[0m");
        }
    }
    

}
