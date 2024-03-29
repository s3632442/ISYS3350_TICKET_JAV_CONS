package HelpDeskTicketSystem;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * File handling operations
 */
public class FileHandler {
	
	/**
	 * Helper method for building a file reader.
	 * @param filename - file to read from
	 * @return Scanner : null
	 */
	public static Scanner reader(String filename) {
			Scanner sc;
			try {
				sc = new Scanner(new FileReader(filename));
			} catch (FileNotFoundException e) {
				return null;
			}
			return sc;
	}
	
	/**
	 * Helper method to load the user database from the given filename
	 * @param filename - file to read from
	 * @return ArrayList<User> : null
	 */
	public static ArrayList<User> loadUserDatabase(String filename) {
		String read = "\0";
		Scanner sc = reader(filename);
		ArrayList<User> users = new ArrayList<User>();
		
		if (sc == null) { return users; }; // if no file with name exists
		
		// read user database into user object array
		while (sc.hasNextLine()) {
			read = sc.nextLine();
			
			/*
			 * Search for a leading identifier
			 * will either be STAFF / TECH
			 */
			if (read.equals("STAFF")) {
				users.add(new StaffUser(sc));
			} else {
				users.add(new TechUser(sc));
			}
		}
		return users;
	}
	
	/**
	 * Helper method for writing the current users to a database file
	 * @param filename - file to write to
	 * @param users - users database to write
	 */
	public static void writeUserDatabase(String filename, ArrayList<User> users) {
		PrintWriter pw;
		
		/*
		 * try write the user array to file
		 * error is thrown if the file does not exist /
		 * application does not have permission 
		 */
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
	
	/**
	 * Helper method for loading the tickets database from the given filename
	 * @param filename -  file to read from
	 * @return ArrayList<Ticket> : null
	 */
	public static ArrayList<Ticket> loadTicketDatabase(String filename) {
		String read = "\0";
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		Ticket tmp;
		String tag = "TICKET";
		Scanner sc = reader(filename);
		
		if (sc == null) { return tickets; }
		
		/*
		 * read through file looking for identifier TICKET
		 * pass the scanner to the ticket constructor for init
		 */
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
	
	/**
	 * Helper method for writing the tickets database to a file using the given filename
	 * @param filename - file to write to
	 * @param tickets - tickets database to write
	 */
	public static void writeTicketDatabase(String filename, ArrayList<Ticket> tickets) {
		PrintWriter pw;
		
		/*
		 * try to write each ticket using a print writer
		 * throws an error when the file does not exists &
		 * the application does not have permissions to open a new file
		 */
		try {
			pw = new PrintWriter(filename);
			for (Ticket tmp : tickets) {
				tmp.writeAttributes(pw);
			}
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
