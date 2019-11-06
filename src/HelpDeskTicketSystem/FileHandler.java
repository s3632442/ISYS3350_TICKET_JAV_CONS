package HelpDeskTicketSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
	
	private static String STAFF_TAG = "STAFF";
	
	public static Scanner reader(String filename) {
			Scanner sc;
			try {
				sc = new Scanner(new FileReader(filename));
			} catch (FileNotFoundException e) {
				return null;
			}
			return sc;
	}
	
	public static ArrayList<User> loadUserDatabase(String filename) {
		String read = "\0";
		Scanner sc = reader(filename);
		ArrayList<User> users = new ArrayList<User>();
		if (sc == null) { return null; };
		
		while (sc.hasNextLine()) {
			read = sc.nextLine();
			
			if (read.equals(STAFF_TAG)) {
				users.add(new StaffUser(sc));
			} else {
				users.add(new TechUser(sc));
			}
		}
		return users;
	}
	
	public static void writeUserDatabase(String filename, ArrayList<User> users) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			for (User tmp : users) {
				tmp.writeAttributes(pw);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Ticket> loadTicketDatabase(String filename, String tag) {
		String read = "\0";
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		Ticket tmp;
		
		Scanner sc = reader(filename);
		
		if (sc == null) { return tickets; }
		
		while (sc.hasNextLine()) {
			read = sc.nextLine();
			tmp = null;
			if (read.equals(tag)) {
				tmp = new Ticket(sc);
				if (tmp != null) {
					tickets.add(tmp);
					Ticket.ticketIDCounter = tickets.size() + 1;
				}
			}
		}
		
		return tickets;
	}
	
	public static void writeTicketDatabase(String filename, ArrayList<Ticket> tickets) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(filename);
			for (Ticket tmp : tickets) {
				tmp.writeDetails(pw);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
