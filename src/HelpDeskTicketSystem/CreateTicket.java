package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.Scanner;

public class CreateTicket {
	protected enum TicketSeverity {
		LOW,
		MEDIUM,
		HIGH
	};
	// Attribute
	private String ticketId;
	// Counter to increment on ticket creation
	static int ticketIDCounter = 1;
	private String ticketCreatorFirstName;
	private String ticketCreatorLastName;
	private String ticketCreatorStaffNumber;
	private String ticketCreatorEmail;
	private String ticketCreatorContactNumber;
	// Description of IT Issue
	private String descriptionIssue;
	private TicketSeverity ticketSeverity;
	//	Initial setup setting ticket as true for open on creation
	//	False for when ticket is closed
	private boolean ticketStatus;
	// Technician Details added once ticket allocated to technician
	private String ticketTechnicianFirstName;
	private String ticketTechnicianLastName;
	
	//Constructor for Create Ticket 
	public CreateTicket(String ticketId, String ticketCreatorFirstName, String ticketCreatorLastName,
			String ticketCreatorStaffNumber, String ticketCreatorEmail, String ticketCreatorContactNumber,
			String descriptionIssue, TicketSeverity ticketSeverity) {
		super();
		this.ticketId = ticketId;
		this.ticketCreatorFirstName = ticketCreatorFirstName;
		this.ticketCreatorLastName = ticketCreatorLastName;
		this.ticketCreatorStaffNumber = ticketCreatorStaffNumber;
		this.ticketCreatorEmail = ticketCreatorEmail;
		this.ticketCreatorContactNumber = ticketCreatorContactNumber;
		this.descriptionIssue = descriptionIssue;
		this.ticketSeverity = ticketSeverity;
		this.ticketStatus = true;
		this.ticketTechnicianFirstName = null;
		this.ticketTechnicianLastName = null;
		ticketIDCounter+=1;
	}
	
	
	// Getters for Create Ticket
	
	public int getTicketCounter() {
		return ticketIDCounter;
	}
	
	public String getTicketId() {
		return ticketId;
	}

	public String getTicketCreatorFirstName() {
		return ticketCreatorFirstName;
	}
	public String getTicketCreatorLastName() {
		return ticketCreatorLastName;
	}
	public String getTicketCreatorStaffNumber() {
		return ticketCreatorStaffNumber;
	}
	
	public String getTicketCreatorEmail() {
		return ticketCreatorEmail;
	}	
	
	public String getTicketCreatorContactNumber() {
		return ticketCreatorContactNumber;
	}
	
	public String getDescriptionIssue() {
		return descriptionIssue;
	}	
	
	public TicketSeverity getTicketSeverity() {
		return ticketSeverity;
	}
	
	public String getTicketStatus() {
		if (ticketStatus) {
			return "Open";
		} else {
			return "Closed";
		}
	}	
	
	public String getTicketTechnicianFirstName() {
		return ticketTechnicianFirstName;
	}	
	
	public String getTicketTechnicianLastName() {
		return ticketTechnicianLastName;
	}
	
	// Setters for Create Ticket
	
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public void setTicketCreatorFirstName(String ticketCreatorFirstName) {
		this.ticketCreatorFirstName = ticketCreatorFirstName;
	}
	 
	public void setTicketCreatorLastName(String ticketCreatorLastName) {
		this.ticketCreatorLastName = ticketCreatorLastName;
	}

	public void setTicketCreatorStaffNumber(String ticketCreatorStaffNumber) {
		this.ticketCreatorStaffNumber = ticketCreatorStaffNumber;
	}

	public void setTicketCreatorEmail(String ticketCreatorEmail) {
		this.ticketCreatorEmail = ticketCreatorEmail;
	}

	public void setTicketCreatorContactNumber(String ticketCreatorContactNumber) {
		this.ticketCreatorContactNumber = ticketCreatorContactNumber;
	}

	public void setDescriptionIssue(String descriptionIssue) {
		this.descriptionIssue = descriptionIssue;
	}

	public void setTicketSeverity(TicketSeverity ticketSeverity)
	{
		this.ticketSeverity = ticketSeverity;
	}
	public void setTicketStatus(boolean ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public void setTicketTechnicianFirstName(String ticketTechnicianFirstName) {
		this.ticketTechnicianFirstName = ticketTechnicianFirstName;
	}

	public void setTicketTechnicianLastName(String ticketTechnicianLastName) {
		this.ticketTechnicianLastName = ticketTechnicianLastName;
	}
	/* File Handling helper methods compile all attributes for CreateTicket 
    object to be written out to file*/
	public void writeAttributes(PrintWriter pw){
		pw.println(this.ticketId);
	    pw.println(this.ticketCreatorFirstName);
	    pw.println(this.ticketCreatorLastName);
	    pw.println(this.ticketCreatorStaffNumber);
	    pw.println(this.ticketCreatorEmail);
	    pw.println(this.ticketCreatorContactNumber);
	    pw.println(this.descriptionIssue);
	    pw.println(this.ticketSeverity);
	    pw.println(this.ticketStatus);
	    pw.println(this.ticketTechnicianFirstName);
	    pw.println(this.ticketTechnicianLastName);
	}
	public void writeDetails(PrintWriter pw){
		// tag for CreateTicket object for writing data out to file
		pw.println("CreateTicket");
		// adds the attributes for sale object to write data out to file
		this.writeAttributes(pw);
	}
	
	// constructor for reading in data from file and creating CreateTicket objects
	public CreateTicket(Scanner fileScanner){
		String tempSeverity;
		this.ticketId = fileScanner.nextLine();
		this.ticketCreatorFirstName = fileScanner.nextLine();
		this.ticketCreatorLastName = fileScanner.nextLine();
		this.ticketCreatorStaffNumber = fileScanner.nextLine();
		this.ticketCreatorEmail = fileScanner.nextLine();
		this.ticketCreatorContactNumber = fileScanner.nextLine();
		this.descriptionIssue = fileScanner.nextLine();
		 tempSeverity = fileScanner.nextLine();
		// Convert String to Enum for reading severity from file to object
		this.ticketSeverity = TicketSeverity.valueOf(tempSeverity.toUpperCase());
		this.ticketStatus = fileScanner.nextBoolean();
		this.ticketTechnicianFirstName = fileScanner.nextLine();
		this.ticketTechnicianLastName = fileScanner.nextLine();
		//clear trailing newline/scanner bug issue if present
		if (fileScanner.hasNextLine()){
			fileScanner.nextLine();
		}
	}
}
