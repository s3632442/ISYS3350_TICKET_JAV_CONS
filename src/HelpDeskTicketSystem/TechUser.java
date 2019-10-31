package HelpDeskTicketSystem;

import java.util.ArrayList;

public class TechUser extends User {
	
	private int activeCount;
	private ArrayList<String> ticketIds;
	
	public TechUser(String id, String pwd, String firstName, String lastName, int activeCount, ArrayList<String> ticketIds)
	{
		super(id, pwd, firstName, lastName);
		this.activeCount = activeCount;
		this.ticketIds = ticketIds;
	}
	
	public int getActiveCount() 
	{
		return this.activeCount;
	}
	
	public ArrayList<String> getTicketIds() 
	{
		return this.ticketIds;
	}
	
	public void setActiveCount(int activeCount)
	{
		this.activeCount = activeCount;
	}
	
	public void setTicketIds(ArrayList<String> ticketIds)
	{
		this.ticketIds = ticketIds;
	}
	
	public void addTicketId(String ticketId)
	{
		ticketIds.add(ticketId);
	}
	
}
