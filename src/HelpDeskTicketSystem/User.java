package HelpDeskTicketSystem;

import java.io.PrintWriter;

public class User {
	
	public enum UserType {
		STAFF,
		TECH
	}
	
	private final String id;
	private String pwd;
	private final String firstName;
	private final String lastName;

	public User(String id, String pwd, String firstName, String lastName)
	{
		this.id = id;
		this.pwd = pwd;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
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

	public boolean login(String pwd)
	{
		if (this.getPwd().equals(pwd))
		{
			return true;
		}
		return false;
	}
	public void writeAttributes(PrintWriter pw)
	{
		pw.println("USER");
		pw.println(this.id);
		pw.println(this.pwd);
		pw.println(this.firstName);
		pw.println(this.lastName);
	}
}


