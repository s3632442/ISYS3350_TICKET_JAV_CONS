package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.Scanner;

public class StaffUser extends User {
	
	private String email;	
	private String contactNumber;
	
	/**
	 * Default constructor
	 * @param id - identification number
	 * @param pwd - plaintext password
	 * @param firstName - user first name
	 * @param lastName - user last name
	 * @param email - user email address
	 * @param contactNumber - user contact number
	 */
	public StaffUser(String id, String pwd, String firstName, String lastName, String email, String contactNumber)
	{
		super(id, pwd, firstName, lastName);
		this.email = email;
		this.contactNumber = contactNumber;
	}
	
	/**
	 * Staff constructor from file
	 * @param sc - Scanner for reading user attributes from file
	 */
	public StaffUser(Scanner sc)
	{
		super(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
		this.email = sc.nextLine();
		this.contactNumber = sc.nextLine();
	}
	
	/**
	 * get user email
	 * @return String
	 */
	public String getEmail()
	{
		return this.email;
	}
	
	/**
	 * get user contact number
	 * @return String
	 */
	public String getContactNumber()
	{
		return this.contactNumber;
	}
	
	/**
	 * set user email
	 * @param email - email address to change to
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	/**
	 * set user contact number
	 * @param contactNumber - contact number to change to
	 */
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}

	/**
	 * Helper method to write staff attributes to file
	 * @param pw - Output stream to write to
	 */
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
