package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.Scanner;

public class StaffUser extends User {
	
	private String email;
	private String contactNumber;
	
	public StaffUser(String id, String pwd, String firstName, String lastName, String email, String contactNumber)
	{
		super(id, pwd, firstName, lastName);
		this.email = email;
		this.contactNumber = contactNumber;
	}
	
	public StaffUser(String id, String pwd, String firstName, String lastName, Scanner sc)
	{
		super(id, pwd, firstName, lastName);
		this.email = sc.nextLine();
		this.contactNumber = sc.nextLine();
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

	public void writeAttributes(PrintWriter pw)
	{
		pw.println("STAFF");
		pw.println(this.getId());
		pw.println(this.getPwd());
		pw.println(this.getFirstName());
		pw.println(this.getLastName());
		pw.println(this.getEmail());
		pw.println(this.getContactNumber());
	}
}
