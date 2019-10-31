package HelpDeskTicketSystem;

public class User {
	
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
}


