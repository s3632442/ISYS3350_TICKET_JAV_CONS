package HelpDeskTicketSystem;

public class StaffUser extends User {
	
	private String email;
	private String contactNumber;
	
	public StaffUser(String id, String pwd, String firstName, String lastName, String email, String contactNumber)
	{
		super(id, pwd, firstName, lastName);
		this.email = email;
		this.contactNumber = contactNumber;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getContactNumber()
	{
		return this.contactNumber;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}
	
}
