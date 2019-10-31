package HelpDeskTicketSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	
	
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
	
	public static boolean verifyUser(String filename, String tag, String[] login)
	{
		String id, pwd, file_tag;
		
		Scanner sc = reader(filename);
		
		if (sc == null) { return false; };
		
		while (sc.hasNextLine())
		{
			file_tag = sc.nextLine();
			if (file_tag.equals(tag))
			{
				id = sc.nextLine();
				pwd = sc.nextLine();
				
				if (login[0].equals(id) && login[1].equals(pwd))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public static void writeUserDatabase(String filename, String tag, String[] login)
	{
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			pw.append("USER");
			pw.append(login[0]);
			pw.append(login[1]);
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
