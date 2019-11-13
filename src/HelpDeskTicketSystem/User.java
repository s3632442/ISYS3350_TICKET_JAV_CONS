package HelpDeskTicketSystem;

import java.io.PrintWriter;

public class User {
	
	// types of users
	public enum UserType {
		STAFF,
		TECH
	}
	
	private final String id; 		// unique identifier
	private String pwd; 			// plaintext password
	private final String firstName; // user first name
	private final String lastName;  // user last name

	// default constructor
	public User(String id, String pwd, String firstName, String lastName)
	{
		this.id = id;
		this.pwd = pwd;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	/*
	 * getters and setters
	 */
	public String getId()
	{
		return this.id;
	}
	
	public String getPwd()
	{
		return this.pwd;
	}
	
	public String getFirstName()
	{
		return this.firstName;
	}
	
	public String getLastName()
	{
		return this.lastName;
	}
	
	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	// login 
	public boolean login(String pwd)
	{
		// if password is the same as stored password
		if (this.getPwd().equals(pwd))
		{
			return true;
		}
		return false;
	}
	
	// default write user attributes out to file 
	public void writeAttributes(PrintWriter pw)
	{
		pw.println("USER");
		pw.println(this.id);
		pw.println(this.pwd);
		pw.println(this.firstName);
		pw.println(this.lastName);
	}
}


