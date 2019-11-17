package HelpDeskTicketSystem;

import java.io.PrintWriter;

public class User {

	
	private final String id;
	private String pwd;
	private final String firstName;
	private final String lastName; 

	
	/**
	 * Default constructor
	 * @param id - identification number
	 * @param pwd - plain text password
	 * @param firstName - user first name
	 * @param lastName - user last name
	 */
	public User(String id, String pwd, String firstName, String lastName)
	{
		this.id = id;
		this.pwd = pwd;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/**
	 * get user identification number
	 * @return String
	 */
	public String getId()
	{
		return this.id;
	}
	
	/**
	 * get user plain text password
	 * @return String
	 */
	public String getPwd()
	{
		return this.pwd;
	}
	
	/**
	 * get user first name
	 * @return String
	 */
	public String getFirstName()
	{
		return this.firstName;
	}
	
	/**
	 * get user last name
	 * @return String
	 */
	public String getLastName()
	{
		return this.lastName;
	}
	
	/**
	 * set user's password
	 * @param pwd - new password to set
	 */
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	/**
	 * Helper method to check for valid credentials on login.
	 * @param pwd - password for comparison
	 * @return boolean
	 */
	public boolean login(String pwd)
	{
		// if password is the same as stored password
		if (this.getPwd().equals(pwd))
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method to write user attributes to file
	 * @param pw - Output stream to write to
	 */
	public void writeAttributes(PrintWriter pw)
	{
		pw.println("USER");
		pw.println(this.id);
		pw.println(this.pwd);
		pw.println(this.firstName);
		pw.println(this.lastName);
	}
}


