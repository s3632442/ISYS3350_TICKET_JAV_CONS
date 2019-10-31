package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.Scanner;

public class Ticket {
	public enum TicketSeverity {
		LOW,
		MEDIUM,
		HIGH
	};
	// Attribute
	private String id;
	// Counter to increment on ticket creation
	public static int ticketIDCounter = 1;
	private String creatorFirstName;
	private String creatorLastName;
	private String creatorStaffNumber;
	private String creatorEmail;
	private String creatorContactNumber;
	// Description of IT Issue
	private String description;
	private TicketSeverity severity;
	//	Initial setup setting ticket as true for open on creation
	//	False for when ticket is closed
	private boolean status;
	// Technician Details added once ticket allocated to technician
	private String technicianFirstName;
	private String technicianLastName;
	
	//Constructor for Create Ticket 
	public Ticket(String id, String creatorFirstName, String creatorLastName,
			String creatorStaffNumber, String creatorEmail, String creatorContactNumber,
			String description, TicketSeverity severity) {
		super();
		this.id = id;
		this.creatorFirstName = creatorFirstName;
		this.creatorLastName = creatorLastName;
		this.creatorStaffNumber = creatorStaffNumber;
		this.creatorEmail = creatorEmail;
		this.creatorContactNumber = creatorContactNumber;
		this.description = description;
		this.severity = severity;
		this.status = true;
		this.technicianFirstName = null;
		this.technicianLastName = null;
		ticketIDCounter+=1;
	}
	
	public String getId() {
		return id;
	}

	public String getCreatorFirstName() {
		return creatorFirstName;
	}
	public String getCreatorLastName() {
		return creatorLastName;
	}
	public String getCreatorStaffNumber() {
		return creatorStaffNumber;
	}
	
	public String getCreatorEmail() {
		return creatorEmail;
	}	
	
	public String getCreatorContactNumber() {
		return creatorContactNumber;
	}
	
	public String getDescription() {
		return description;
	}	
	
	public TicketSeverity getSeverity() {
		return severity;
	}
	
	public String getStatus() {
		if (status) {
			return "Open";
		} else {
			return "Closed";
		}
	}	
	
	public String getTechnicianFirstName() {
		return technicianFirstName;
	}	
	
	public String getTechnicianLastName() {
		return technicianLastName;
	}
	
	// Setters for Create Ticket
	
	public void setId(String id) {
		this.id = id;
	}

	public void setCreatorFirstName(String creatorFirstName) {
		this.creatorFirstName = creatorFirstName;
	}
	 
	public void setCreatorLastName(String creatorLastName) {
		this.creatorLastName = creatorLastName;
	}

	public void setCreatorStaffNumber(String creatorStaffNumber) {
		this.creatorStaffNumber = creatorStaffNumber;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	public void setCreatorContactNumber(String creatorContactNumber) {
		this.creatorContactNumber = creatorContactNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setSeverity(TicketSeverity severity)
	{
		this.severity = severity;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setTechnicianFirstName(String technicianFirstName) {
		this.technicianFirstName = technicianFirstName;
	}

	public void setTechnicianLastName(String technicianLastName) {
		this.technicianLastName = technicianLastName;
	}
	
	public void print()
	{
		System.out.printf("ID: %s\nCreator Name: %s %s\nCreator Staff Number: %s\nCreator Email: %s\nCreator Contact Number: %s\nDescription: %s\nSeverity: %s\nStatus: %s\nTechnician Name: %s %s\n",
				this.getId(),
				this.getCreatorFirstName(),
				this.getCreatorLastName(),
				this.getCreatorStaffNumber(),
				this.getCreatorEmail(),
				this.getCreatorContactNumber(),
				this.getDescription(),
				this.getSeverity(),
				this.getStatus(),
				this.getTechnicianFirstName(),
				this.getTechnicianLastName());
	}
	
	/* File Handling helper methods compile all attributes for CreateTicket 
    object to be written out to file*/
	public void writeAttributes(PrintWriter pw){
		pw.println(this.id);
	    pw.println(this.creatorFirstName);
	    pw.println(this.creatorLastName);
	    pw.println(this.creatorStaffNumber);
	    pw.println(this.creatorEmail);
	    pw.println(this.creatorContactNumber);
	    pw.println(this.description);
	    pw.println(this.severity);
	    pw.println(this.status);
	    pw.println(this.technicianFirstName);
	    pw.println(this.technicianLastName);
	}
	
	public void writeDetails(PrintWriter pw){
		// tag for CreateTicket object for writing data out to file
		pw.println("TICKET");
		// adds the attributes for CreateTicket object to write data out to file
		this.writeAttributes(pw);
	}
	
	// constructor for reading in data from file and creating CreateTicket objects
	public Ticket(Scanner sc){
		String tempSeverity;
		this.id = sc.nextLine();
		this.creatorFirstName = sc.nextLine();
		this.creatorLastName = sc.nextLine();
		this.creatorStaffNumber = sc.nextLine();
		this.creatorEmail = sc.nextLine();
		this.creatorContactNumber = sc.nextLine();
		this.description = sc.nextLine();
		tempSeverity = sc.nextLine();
		// Convert String to Enum for reading severity from file to object
		this.severity = TicketSeverity.valueOf(tempSeverity.toUpperCase());
		this.status = sc.nextBoolean();
		this.technicianFirstName = sc.nextLine();
		this.technicianLastName = sc.nextLine();
		//clear trailing newline/scanner bug issue if present
		if (sc.hasNextLine()){
			sc.nextLine();
		}
	}
}
