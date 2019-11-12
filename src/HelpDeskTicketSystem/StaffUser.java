package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.Scanner;

public class StaffUser extends User {
	
	private String email;			// staff email address
	private String contactNumber;	// staff contact number
	
	
	// default constructor
	public StaffUser(String id, String pwd, String firstName, String lastName, String email, String contactNumber)
	{
		super(id, pwd, firstName, lastName);
		this.email = email;
		this.contactNumber = contactNumber;
	}
	
	// file staff initialization
	public StaffUser(Scanner sc)
	{
		super(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
		this.email = sc.nextLine();
		this.contactNumber = sc.nextLine();
	}
	
	/*
	 * getters and setters
	 */
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

	// write staff attributes out to file
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
