package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.Scanner;

public class Ticket {
	
	public static int ticketIDCounter = 1; // Counter to increment on ticket creation
	
	/**
	 * Ticket Severity Typing
	 * LOW/MEDIUM assigned to techLevel=1
	 * HIGH assigned to techLevel=2
	 */
	public enum TicketSeverity {
		LOW,
		MEDIUM,
		HIGH
	};

	// Attributes
	private String id;
	private String creatorFirstName;
	private String creatorLastName;
	private String creatorStaffNumber;
	private String creatorEmail;
	private String creatorContactNumber;
	private String description;
	private TicketSeverity severity;
	private boolean status; // True by default, set to false manually by technician or by expiration
	private String technicianId;
	
	/**
	 * Default constructor
	 * @param id - identification number - automatically generated using generateTicketId() using format: yyyyMMdd-ticketIDCounter
	 * @param creatorFirstName - ticket creator first name
	 * @param creatorLastName - ticket creator last name
	 * @param creatorStaffNumber - ticket creator identification number
	 * @param creatorEmail - ticket creator email address
	 * @param creatorContactNumber - ticket creator contact number
	 * @param description - creator's description of the issue
	 * @param severity - creator's classification of the severity
	 * @param technicianId - automatically assigned technician, N/A if not technician is available (currently can not occur in current implementation)
	 */
	public Ticket(String id, String creatorFirstName, String creatorLastName,
			String creatorStaffNumber, String creatorEmail, String creatorContactNumber,
			String description, TicketSeverity severity, String technicianId) {
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
		this.technicianId = technicianId;
		ticketIDCounter+=1;
	}
	
	/**
	 * get ticket id
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * get the first name of the ticket creator
	 * @return String
	 */
	public String getCreatorFirstName() {
		return creatorFirstName;
	}
	
	/**
	 * get the last name of the ticket creator
	 * @return String
	 */
	public String getCreatorLastName() {
		return creatorLastName;
	}
	
	/**
	 * get the staff number of the ticket creator
	 * @return String
	 */
	public String getCreatorStaffNumber() {
		return creatorStaffNumber;
	}
	
	/**
	 * get the email address of the ticket creator
	 * @return String
	 */
	public String getCreatorEmail() {
		return creatorEmail;
	}	
	
	/**
	 * get the contact number of the ticket creator
	 * @return String
	 */
	public String getCreatorContactNumber() {
		return creatorContactNumber;
	}
	
	/**
	 * get the issue description of the ticket
	 * @return String
	 */
	public String getDescription() {
		return description;
	}	
	
	/**
	 * get the ticket's severity
	 * @return TicketSeverity
	 */
	public TicketSeverity getSeverity() {
		return severity;
	}
	
	/**
	 * get the ticket's status
	 * @return boolean
	 */
	public boolean getStatus() {
		return status;
	}	
	
	/**
	 * get the identification number of the assigned technician
	 * @return String
	 */
	public String getTechnicianId() {
		return technicianId;
	}	
	
	/**
	 * set the identification number
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * set the creator's first name
	 * @param creatorFirstName
	 */
	public void setCreatorFirstName(String creatorFirstName) {
		this.creatorFirstName = creatorFirstName;
	}
	
	/**
	 * set the creator's last name
	 * @param creatorLastName
	 */
	public void setCreatorLastName(String creatorLastName) {
		this.creatorLastName = creatorLastName;
	}

	/**
	 * set the creator's staff number
	 * @param creatorStaffNumber
	 */
	public void setCreatorStaffNumber(String creatorStaffNumber) {
		this.creatorStaffNumber = creatorStaffNumber;
	}

	/**
	 * set the creator's email address
	 * @param creatorEmail
	 */
	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

	/**
	 * set the creator's contact number
	 * @param creatorContactNumber
	 */
	public void setCreatorContactNumber(String creatorContactNumber) {
		this.creatorContactNumber = creatorContactNumber;
	}

	/**
	 * set the description of the issue
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * set the ticket's severity
	 * @param severity
	 */
	public void setSeverity(TicketSeverity severity)
	{
		this.severity = severity;
	}
	
	/**
	 * set the ticket's status
	 * @param status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * set the technician identification number
	 * @param technicianId
	 */
	public void setTechnicianId(String technicianId)
	{
		this.technicianId = technicianId;
	}
	
	/**
	 * Helper method to print status in user-friendly format
	 * 
	 * @return String
	 */
	public String getStringStatus() {
		if (this.status) {
			return "Open";
		}
		return "Closed";
	}

	/**
	 * Helper method to print all ticket details
	 */
	public void print()
	{
		System.out.printf("ID: %s\nCreator Name: %s %s\nCreator Staff Number: %s\nCreator Email: %s\nCreator Contact Number: %s\nDescription: %s\nSeverity: %s\nStatus: %s\nTechnician ID: %s\n",
				this.getId(),
				this.getCreatorFirstName(),
				this.getCreatorLastName(),
				this.getCreatorStaffNumber(),
				this.getCreatorEmail(),
				this.getCreatorContactNumber(),
				this.getDescription(),
				this.getSeverity(),
				this.getStatus(),
				this.getTechnicianId());
	}
	
	/**
	 * Helper method to write ticket details to an output
	 * @param pw - Output stream to write to
	 */
	public void writeAttributes(PrintWriter pw){
		pw.println("TICKET");
		pw.println(this.id);
	    pw.println(this.creatorFirstName);
	    pw.println(this.creatorLastName);
	    pw.println(this.creatorStaffNumber);
	    pw.println(this.creatorEmail);
	    pw.println(this.creatorContactNumber);
	    pw.println(this.description);
	    pw.println(this.severity);
	    pw.println(this.technicianId);
	    pw.println(this.status);
	}
	
	/**
	 * Helper method to determine if id=this
	 * @param id - identification number for comparison
	 * @return boolean
	 */
	public boolean isTechnician(String id) {
		if (id.compareTo(this.getTechnicianId()) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Ticket constructor from file
	 * @param sc - Scanner to read attributes from
	 */
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
		this.technicianId = sc.nextLine();
		this.status = sc.nextBoolean();
		//clear trailing newline/scanner bug issue if present
		if (sc.hasNextLine()){
			sc.nextLine();
		}
	}
}
