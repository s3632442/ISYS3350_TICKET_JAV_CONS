package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TechUser extends User {
	
	private int level;
	private int activeCount;
	
	public TechUser(String id, String pwd, String firstName, String lastName, int level, int activeCount)
	{
		super(id, pwd, firstName, lastName);
		this.level = level;
		this.activeCount = activeCount;
	}
	
	public TechUser(String id, String pwd, String firstName, String lastName, Scanner sc)
	{
		super(id, pwd, firstName, lastName);
		this.level = Integer.parseInt(sc.nextLine());
		this.activeCount = Integer.parseInt(sc.nextLine());
	}
	
	public int getLevel()
	{
		return this.level;
	}
	
	public int getActiveCount() 
	{
		return this.activeCount;
	}
	
	public void setLevel(int level)
	{
		this.level = level;
	}
	
	public void setActiveCount(int activeCount)
	{
		this.activeCount = activeCount;
	}

	public void writeAttributes(PrintWriter pw)
	{
		pw.println("TECH");
		pw.println(this.getId());
		pw.println(this.getPwd());
		pw.println(this.getFirstName());
		pw.println(this.getLastName());
		pw.println(this.getLevel());
	}
}
