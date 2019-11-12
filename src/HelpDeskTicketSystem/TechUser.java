package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TechUser extends User {

	private int level; // technician level i.e. 1 / 2
	private int activeCount; // active amount of tickets
	private int inActiveCount; // inactive amount of tickets
	
	// default constructor
	public TechUser(String id, String pwd, String firstName, String lastName, int level, int activeCount,
			int inActiveCount) {
		super(id, pwd, firstName, lastName);
		this.level = level;
		this.activeCount = activeCount;
		this.inActiveCount = inActiveCount;
	}

	// file technician initialization
	public TechUser(Scanner sc) {
		super(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
		this.level = Integer.parseInt(sc.nextLine());
		this.activeCount = Integer.parseInt(sc.nextLine());
		this.inActiveCount = Integer.parseInt(sc.nextLine());
	}

	/*
	 * getters and setters
	 */
	public int getLevel() {
		return this.level;
	}
	public int getActiveCount() {
		return this.activeCount;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}
	public int getInActiveCount() {
		return inActiveCount;
	}
	public void setInActiveCount(int inActiveCount) {
		this.inActiveCount = inActiveCount;
	}

	// print list of technician's active tickets to console
	public void printActiveTickets(ArrayList<Ticket> tickets) {
		if (tickets != null) {
			for (Ticket tmp : tickets) {
				if (tmp.getTechnicianId().equals(this.getId()) && tmp.getStatus()) {
					System.out.println(tmp.getId());
				}
			}
		}
		System.out.printf("You currently have %s active tickets\n", this.getActiveCount());
		System.out.print("\n---------------------------\n\n");
	}

	// print technician's inactive ticket details to console
	public void printInActiveTickets(ArrayList<Ticket> tickets) {
		if (tickets != null) {
			for (Ticket tmp : tickets) {
				if (tmp.getTechnicianId().equals(this.getId()) && tmp.getStatus()) {
					if (tmp.getTechnicianId().equals(this.getId())) {
						System.out.println("ID: " + tmp.getId() + "| Status:" + tmp.getStringStatus() + "| Severity:"
								+ tmp.getSeverity());
					}
				}
			}
		}
		System.out.printf("You currently have %s inactive tickets\n", this.getInActiveCount());
		System.out.print("\n---------------------------\n\n");
	}

	// write technician attributes out to file 
	public void writeAttributes(PrintWriter pw) {
		pw.println("TECH");
		pw.println(this.getId());
		pw.println(this.getPwd());
		pw.println(this.getFirstName());
		pw.println(this.getLastName());
		pw.println(this.getLevel());
		pw.println(this.getActiveCount());
		pw.println(this.getInActiveCount());
	}

}
