package HelpDeskTicketSystem;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TechUser extends User {

	private int level;
	private int activeCount;
	private int inActiveCount;
	
	/**
	 * Default constructor
	 * @param id - identification number
	 * @param pwd - plain text password
	 * @param firstName - user first name
	 * @param lastName - user last name
	 * @param level - technician level
	 * @param activeCount - active amount of tickets
	 * @param inActiveCount - inactive amount of tickets
	 */
	public TechUser(String id, String pwd, String firstName, String lastName, int level, int activeCount,
			int inActiveCount) {
		super(id, pwd, firstName, lastName);
		this.level = level;
		this.activeCount = activeCount;
		this.inActiveCount = inActiveCount;
	}

	/**
	 * Tech constructor from file
	 * @param sc - Scanner for reading user attributes from file
	 */
	public TechUser(Scanner sc) {
		super(sc.nextLine(), sc.nextLine(), sc.nextLine(), sc.nextLine());
		this.level = Integer.parseInt(sc.nextLine());
		this.activeCount = Integer.parseInt(sc.nextLine());
		this.inActiveCount = Integer.parseInt(sc.nextLine());
	}

	/**
	 * get Tech level
	 * @return Integer
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * get Tech active amount of tickets.
	 * @return Integer
	 */
	public int getActiveCount() {
		return this.activeCount;
	}
	
	/**
	 * set technician level
	 * @param level - level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * set technician's active amount of tickets
	 * @param activeCount - active count to set
	 */
	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}
	
	/**
	 * get technician's inactive amount of tickets
	 * @return Integer
	 */
	public int getInActiveCount() {
		return inActiveCount;
	}
	
	/**
	 * set technician's inactive amount of tickets
	 * @param inActiveCount - inactive count to set
	 */
	public void setInActiveCount(int inActiveCount) {
		this.inActiveCount = inActiveCount;
	}

	/**
	 * Helper method to print list of technician's active ticket details to output.
	 * @param tickets - tickets database
	 */
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

	/**
	 * Helper method to print technician's inactive ticket details to output
	 * @param tickets - tickets database
	 */
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

	/**
	 * Helper method to write tech attributes to file
	 * @param pw - Output stream to write to
	 */
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
