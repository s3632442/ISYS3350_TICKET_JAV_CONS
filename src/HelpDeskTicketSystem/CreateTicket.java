

public class CreateTicket {
	// Attribute
	private String ticketId;
	// Counter to increment on ticket creation
	/*TODO For persistence counter number will need to included date 
	e.g. 
	import java.time.LocalDateTime;
	import java.time.format.DateTimeFormatter;
	LocalDateTime currentDate = LocalDateTime.now();
	DateTimeFormatter ticketDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
	String ticketDate = currentDate.format(ticketDateFormat);
	ticketId = ticketDate + "-" + ticketIDCounter;
	*/
	static int ticketIDCounter = 0;
	private String ticketCreatorFirstName;
	private String ticketCreatorLasttName;
	private String ticketCreatorStaffNumber;
	private String ticketCreatorEmail;
	private String ticketCreatorContactNumber;
	// Description of IT Issue
	private String descriptionIssue;
	private enum ticketSeverity {
		LOW,
		MEDIUM,
		HIGH
	};
	//	Initial setup setting ticket as true for open on creation
	//	False for when ticket is closed
	private boolean ticketStatus = true;
	// Technician Details added once ticket allocated to technician
	private String ticketTechnicianFirstName;
	private String ticketTechnicianLastName;
	
	// Getters for Create Ticket
	
	public String getTicketId() {
		return ticketId;
	}
	public String getTicketCreatorFirstName() {
		return ticketCreatorFirstName;
	}
	public String getTicketCreatorLasttName() {
		return ticketCreatorLasttName;
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
	
	public String getTicketStatus() {
		if (ticketStatus = true) {
			return "Open";
		}
		else
		return "Closed";
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
	 
	public void setTicketCreatorLasttName(String ticketCreatorLasttName) {
		this.ticketCreatorLasttName = ticketCreatorLasttName;
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

	public void setTicketStatus(boolean ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public void setTicketTechnicianFirstName(String ticketTechnicianFirstName) {
		this.ticketTechnicianFirstName = ticketTechnicianFirstName;
	}

	public void setTicketTechnicianLastName(String ticketTechnicianLastName) {
		this.ticketTechnicianLastName = ticketTechnicianLastName;
	}
	
}
