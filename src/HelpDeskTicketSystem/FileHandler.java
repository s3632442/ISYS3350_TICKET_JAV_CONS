package HelpDeskTicketSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	
	private static String TECH_TAG = "TECH";
	private static String STAFF_TAG = "STAFF";
	
	public static Scanner reader(String filename)
	{
			Scanner sc;
			try {
				sc = new Scanner(new FileReader(filename));
			} catch (FileNotFoundException e) {
				return null;
			}
			return sc;
	}
	
	public static User loginUser(String filename, String[] login)
	{
		String id, pwd, firstName, lastName, file_tag;
		
		Scanner sc = reader(filename);
		
		if (sc == null) { return null; };
		
		while (sc.hasNextLine())
		{
			file_tag = sc.nextLine();
			if (file_tag.equals(TECH_TAG) || file_tag.equals(STAFF_TAG))
			{
				id = sc.nextLine();
				pwd = sc.nextLine();
				
				if (login[0].equals(id) && login[1].equals(pwd))
				{
					firstName = sc.nextLine();
					lastName = sc.nextLine();
					
					if (file_tag.equals(STAFF_TAG))
					{
						return new StaffUser(id, pwd, firstName, lastName, sc);
					}
					return new TechUser(id, pwd, firstName, lastName, sc);
				}
			}
		}
		return null;
	}
	
	public static void writeUserDatabase(String filename, String tag, User user)
	{
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			user.writeAttributes(pw);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Ticket> loadTicketDatabase(String filename, String tag)
	{
		String read = "\0";
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		Ticket tmp;
		
		Scanner sc = reader(filename);
		
		if (sc == null) { return null; }
		
		while (sc.hasNextLine())
		{
			read = sc.nextLine();
			tmp = null;
			if (read.equals(tag))
			{
				tmp = new Ticket(sc);
			}
			if (tmp == null) {
				System.out.println("File tag " + tag + " is invalid.");
				return null;
			} else {
				tickets.add(tmp);
				Ticket.ticketIDCounter = tickets.size() + 1;
			}
		}
		
		return tickets;
	}
	
	public static void writeTicketDatabase(String filename, ArrayList<Ticket> tickets)
	{
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			for (int i = 0; i < tickets.size(); i++)
			{
				tickets.get(i).writeDetails(pw);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
