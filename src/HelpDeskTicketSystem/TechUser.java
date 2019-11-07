package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TechUser extends User {

	private int level;
	private int activeCount;
	private int inActiveCount;

	public TechUser(String id, String pwd, String firstName, String lastName, int level, int activeCount,
			int inActiveCount) {
		super(id, pwd, firstName, lastName);
		this.level = level;
		this.activeCount = activeCount;
		this.inActiveCount = inActiveCount;
	}

	public TechUser(Scanner sc) {
		super(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
		this.level = Integer.parseInt(sc.nextLine());
		this.activeCount = Integer.parseInt(sc.nextLine());
		this.inActiveCount = Integer.parseInt(sc.nextLine());
	}

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

	public void printActiveTickets(ArrayList<Ticket> tickets) {
		if (tickets != null) {
			for (Ticket tmp : tickets) {
				if ((tmp.getStringStatus().toUpperCase()).equals("OPEN")) {
					if (tmp.getTechnicianId().equals(this.getId())) {
						System.out.println("ID: " + tmp.getId() + "| Status:" + tmp.getStringStatus() + "| Severity:"
								+ tmp.getSeverity());
					}
				}
			}
		}
		System.out.printf("You currently have %s active tickets\n", this.getActiveCount());
		System.out.print("\n---------------------------\n\n");
	}

	public void printInActiveTickets(ArrayList<Ticket> tickets) {
		if (tickets != null) {
			for (Ticket tmp : tickets) {
				if ((tmp.getStringStatus().toUpperCase()).equals("CLOSED")) {
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

	public void writeAttributes(PrintWriter pw) {
		pw.println("TECH");
		pw.println(this.getId());
		pw.println(this.getPwd());
		pw.println(this.getFirstName());
		pw.println(this.getLastName());
		pw.println(this.getLevel());
		pw.println(this.getActiveCount());
	}

}
