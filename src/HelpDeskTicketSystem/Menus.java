package HelpDeskTicketSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import HelpDeskTicketSystem.Ticket.TicketSeverity;

public class Menus {
	// array of created ticket objects
	private static ArrayList<Ticket> tickets = new ArrayList<Ticket>();
	private static ArrayList<User> users = new ArrayList<User>();
	private static User user = null;

	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (tickets != null && !tickets.isEmpty()){
					FileHandler.writeTicketDatabase("tickets.txt", tickets);
				}
				if (users != null && !users.isEmpty()) {
					FileHandler.writeUserDatabase("users.txt", users);
				}
			}
		});
		
		String input = "\0";
		Integer intInput;
		
		Scanner sc = new Scanner(System.in);
		// Initialize selection variable to ASCII null to keep compiler happy
		char selection = '\0';

		String userId, userPwd;

		// Check to see is persistent data for tickets and load if present
		tickets = FileHandler.loadTicketDatabase("tickets.txt", "TICKET");
		users = FileHandler.loadUserDatabase("users.txt");

		do {
			if (user == null) {
				List<String> menu = Arrays.asList("Create Account", "Login", "Exit Program");
				List<String> menuSelections = Arrays.asList("C", "L", "X");
				
				printMenu("IT HELP DESK SYSTEM", menu, menuSelections);
				input = sc.nextLine();
				System.out.println();
				
				if (input.length() != 1) {
					System.out.println("Error - invalid selection!");
				} else {
					selection = Character.toUpperCase(input.charAt(0));
					
					switch(selection) {
					case 'C':
						System.out.println("Not implemented");
						break;
					case 'L':
						userId = getInput(sc, "Employee No");
						userPwd = getInput(sc, "Password");
						
						for (User tmp: users){
							if (tmp.getId() != null && compareString(tmp.getId(), userId)){
								if (tmp.login(userPwd)){
									user = tmp;
								}
							}
						}
						if (user == null) {
							System.out.println("Error - Invalid credentials");
						} else {
							System.out.printf("Welcome %s %s!\n", user.getFirstName(), user.getLastName());
						}
						break;
					case 'X':
						input = exit(sc, "selection", false);
						if (compareString(input, "EXIT_RESUME")) {
							System.exit(0);
						}
						break;
					}
				}
				
			} else if (user instanceof StaffUser) {
				List<String> menu = Arrays.asList("Create Ticket", "Logout");
				List<String> menuSelections = Arrays.asList("C", "X");
				
				// displays open tickets
				displayOpenTickets();
				printMenu("STAFF MENU", menu, menuSelections);
				input = sc.nextLine();
				System.out.println();

				if (input.length() != 1) {
					System.out.println("Error - invalid selection!");
				} else {
					// make selection case insensitive
					selection = Character.toUpperCase(input.charAt(0));

					// process user menu selection
					switch (selection) {
					// create ticket case
					case 'C': 
						Ticket ticket = createTicketMenu(sc);
						if (ticket != null) {
							tickets.add(ticket);
						}
						break;
					case 'P': 
						for (Ticket tmp : tickets) {
							tmp.print();
						}
						break;
					// exit case
					case 'X': 
						input = exit(sc, "selection", false);
						if (compareString(input, "EXIT_RESUME")) {
							user = null;
						}
						break;
					// invalid selection case
					default: 
						System.out.println("Error - invalid selection!");
					}
				}
			} else {
				do {
					// set menu selections
					List<String> menu = Arrays.asList("Close Ticket", "View Active Tickets", "Logout");
					List<String> menuSelections = Arrays.asList("C", "V", "X");
					
					// menu title
					printMenu("TECH MENU", menu, menuSelections);
					input = sc.nextLine();
					
					System.out.println();

					// validate selection input length
					if (input.length() != 1) {
						System.out.println("Error - invalid selection!");
					} else {
						// make selection case insensitive
						selection = Character.toUpperCase(input.charAt(0));

						// process user menu selection
						switch (selection) {
						case 'C': 					
							intInput = getInteger(sc, "ticket number");
							if (tickets != null) {
								Ticket tmp = tickets.get(intInput);
								if (!tmp.getStatus()) {
									System.out.println("Error - ticket is already closed!");
								} else if (tmp.isTechnician(user.getId())) {
									tmp.setStatus(false);
									System.out.printf("Ticket %s has been closed!", tmp.getId());
									break;
								} else {
									System.out.println("Error - Can not close another technician's ticket!");
									break;
								}		
							} else{
								System.out.println("Error - There are currently no tickets in the database!");
							} 
							break;
						case 'V':
							((TechUser)user).printActiveTickets(tickets);
							break;
						// exit case
						case 'X': 
							input = exit(sc, "selection", false);
							if (compareString(input, "EXIT_RESUME")) {
								user = null;
							}
							break;
						// invalid selection case
						default: 
							System.out.println("Error - invalid selection!");
						}
						System.out.println();
					}
				} while (!compareString(input, "EXIT_RESUME"));
			}
		} while (user == null || !compareString(input, "EXIT_RESUME"));
		System.exit(0);
	}
	
	protected static void displayOpenTickets() {
		if (tickets != null && !tickets.isEmpty()) {
			System.out.println("Open tickets");
			for (Ticket tmp : tickets) {
				System.out.print("ID: " + tmp.getId() + "| Status:" + tmp.getStringStatus()
						+ "| Severity:" + tmp.getSeverity());
				System.out.print("\n---------------------------\n\n");
			}
		}
	}

	// print menu method
	protected static void printMenu(String title, List<String> menu, List<String> menuSelections) {
		System.out.printf("%s\n---------------------------\n", title);
	
		for (int i = 0; i < menu.size(); i++) {
			System.out.printf("%-25s%s\n", menu.get(i), menuSelections != null ? menuSelections.get(i) : i + 1);
		}
		System.out.println();
		System.out.println("Enter selection: ");
	}

	protected static Ticket.TicketSeverity checkTicketSeverity(String input) {
		if (input.equals(Ticket.TicketSeverity.HIGH.name())) {
			return Ticket.TicketSeverity.HIGH;
		}
		if (input.equals(Ticket.TicketSeverity.MEDIUM.name())) {
			return Ticket.TicketSeverity.MEDIUM;
		}
		if (input.equals(Ticket.TicketSeverity.LOW.name())) {
			return Ticket.TicketSeverity.LOW;
		}
		return null;
	}

	protected static String getInput(Scanner scanner, String request) {
		String input = "\0";
		// Regex that matches email addresses
		String emailPattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
		// Regex that matches Australian formatted phone numbers
		String phonePattern = "^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$";

		// request input
		System.out.printf("Enter your %s: ", request);
		input = scanner.nextLine();

		// if exit command
		if ("X!".equals(input.toUpperCase())) {
			return input;
		}
		// if empty
		if ("".equals(input)) {
			System.out.println("Error - input can not be empty!");
			input = getInput(scanner, request);
		}
		// if invalid email
		if (request.equalsIgnoreCase("email") && !input.matches(emailPattern)) {
			System.out.println("Error - invalid email address!");
			input = getInput(scanner, request);
		}
		// if invalid phone number
		if (request.equalsIgnoreCase("contact number") && !input.matches(phonePattern)) {
			System.out.println("Error - invalid phone number, must use Australian format! e.g. +61290001234");
			input = getInput(scanner, request);
		}
		// if valid ticket severity
		if (request.equalsIgnoreCase("severity")) {
			if (checkTicketSeverity(input.toUpperCase()) == null) {
				System.out.println("Error - invalid severity, must be LOW, MEDIUM, OR HIGH!");
				input = getInput(scanner, request);
			}
			input = input.toUpperCase();
		}
		return input;
	}
	
	protected static boolean compareString(String selection, String comparison) {
		if (selection.equals(comparison)) {
			return true;
		}
		return false;
	}
	
	protected static Integer getInteger(Scanner sc, String request)
	{
		String userInput = "\0", substr = "\0";
		Integer out = -1;
		try {
			userInput = getInput(sc, request);
			
			if ("ticket number".equals(request)) {
				if (userInput.length() < 10)
				{
					throw new NumberFormatException();
				}
				substr = userInput.substring(userInput.lastIndexOf('-') + 1, userInput.length());
				out = Integer.parseInt(substr);
				out -= 1;
			}
		} catch(NumberFormatException ex) {
			System.out.printf("Error - must enter a valid %s\n", request);
			out = getInteger(sc, request);
		}
		return out;
	}
	
	protected static String exit(Scanner sc, String request, boolean earlyExit) {
		String exit = "EXIT_ABORT";
		String input = "\0";
		if (earlyExit) {
			System.out.println("Exiting will cause any progress to be lost");
		}
		System.out.println("Are you sure you want to exit? Y/N");
		do {	
			input = getInput(sc, request);
			if (input.length() != 1) {
				System.out.println("Error - too many characters, must select Y or N");
			} else {
				exit = input.toUpperCase();
				if (compareString(exit, "Y")) {
					exit = "EXIT_RESUME";
				} else if (compareString(exit, "N")) {
					return exit;
				} else {
					System.out.println("Error - invalid selection, must select Y or N");
				}
			}
		} while (!compareString(exit, "EXIT_RESUME") && !compareString(exit, "N"));
		return exit;
	}
	/*
	 * Generate a ticket identification number Ticket ID Format:
	 * yyyyMMdd-ticketIDCounter
	 */
	protected static String generateTicketId() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = date.format(dateFormat);
		return formattedDate + "-" + Ticket.ticketIDCounter;
	}

	public static TechUser getAvailableTechnician(TicketSeverity severity) {
		Entry<TechUser, Integer> technician = null;
		
		Map<TechUser, Integer> availableTechnicians = new HashMap<TechUser, Integer>();
		
		for (User usr : users) {
			if (usr instanceof TechUser) {
				TechUser tmp = ((TechUser)usr);
				if (severity == TicketSeverity.HIGH) {
					if (tmp.getLevel() == 2) {
						availableTechnicians.put(tmp, tmp.getActiveCount());
					}
				}
				else {
					if (tmp.getLevel() == 1) {
						availableTechnicians.put(tmp, tmp.getActiveCount());
					}
				}
			}
		}
		
		if (availableTechnicians == null || availableTechnicians.isEmpty()) {
			return null;
		}
		
		for (Entry<TechUser, Integer> entry : availableTechnicians.entrySet()) {
			if (technician == null || technician.getValue() > entry.getValue()) {
				technician = entry;
			}
		}
		
		return technician.getKey();
	}
	
	protected static Ticket createTicketMenu(Scanner sc) {
		String input = "\0", technicianId = "N\\A";
		TechUser technician = null;
		String[] ticket = new String[8];
		int ticketCounter = 0;
		Ticket.TicketSeverity severity = null;

		List<String> menu = Arrays.asList("Surname", "Given name", "Staff number", "Email", "Contact number",
				"Description", "Severity", "Confirm", "Exit");
		List<String> menuSelections = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "C", "X");

		System.out.println("Type X! at any time to cancel the ticket");
		
		do {
			ticket[ticketCounter + 1] = getInput(sc, menu.get(ticketCounter));
			if (ticket[ticketCounter + 1].equals("X!")) {
				input = exit(sc, "selection", false);
				if (compareString(input, "Y")) {
					return null;
				}
			} else {
				//menu.set(ticketCounter, ticket[ticketCounter + 1]); 
				ticketCounter += 1;
			}
		} while (ticketCounter != menu.size() - 2 || compareString(input, "Y"));

		severity = checkTicketSeverity(ticket[7]);
		
		if (!compareString(input, "Y")) {
			do {
				printMenu("CREATE TICKET MENU", menu, menuSelections);
				input = sc.nextLine();
				if (input.length() == 1) {
					switch (input.toUpperCase()) {
					case "1": 
						ticket[1] = getInput(sc, menu.get(0));
						break;
					case "2": 
						ticket[2] = getInput(sc, menu.get(1));
						break;
					case "3": 
						ticket[3] = getInput(sc, menu.get(2));
						break;
					case "4": 
						ticket[4] = getInput(sc, menu.get(3));
						break;
					case "5": 
						ticket[5] = getInput(sc, menu.get(4));
						break;
					case "6": 
						ticket[6] = getInput(sc, menu.get(5));
						break;
					case "7": 
						ticket[7] = getInput(sc, menu.get(6));
						severity = checkTicketSeverity(ticket[7]);
						break;
					case "C": 
						ticket[0] = generateTicketId();
						technician = getAvailableTechnician(severity);
						if (technician != null) {
							technicianId = technician.getId();
							technician.setActiveCount(technician.getActiveCount() + 1);
						}
						return new Ticket(ticket[0], ticket[1], ticket[2], ticket[3], ticket[4], ticket[5], ticket[6],
							severity, technicianId);
					case "X": 
						input = exit(sc, "selection", true);
						if (compareString(input, "EXIT_RESUME")) {
							return null;
						}
						break;
					default: 
						System.out.println("Error - invalid selection!");
					}
				} else {
					System.out.println("Error - invalid selection!");
				}
			} while (!compareString(input, "EXIT_RESUME"));
		}
	return null;
	}
}
