package HelpDeskTicketSystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
				if (tickets != null && !tickets.isEmpty()) {
					FileHandler.writeTicketDatabase("tickets.txt", tickets);
				}
				if (users != null && !users.isEmpty()) {
					FileHandler.writeUserDatabase("users.txt", users);
				}
			}
		});
		// variable declaration
		String input = "\0";
		Boolean exit = false, nestedExit = false;
		Integer intInput;
		// Initialise scanner
		Scanner sc = new Scanner(System.in);
		// Initialise selection variable to ASCII null to keep compiler happy
		char selection = '\0';

		// Check to see is persistent data for tickets and load if present
		tickets = FileHandler.loadTicketDatabase("tickets.txt");
		users = FileHandler.loadUserDatabase("users.txt");
		
		// System title screen
		System.out.println("  -----------------------------------------");
		System.out.println("|  CCCCC   IIIII  NN    NN   CCCCC    OOOOO  |");  
		System.out.println("| CC   CC   III   NNN   NN  CC   CC  OO   OO |"); 
		System.out.println("| CC        III   NN N  NN  CC       OO   OO |");
		System.out.println("| CC        III   NN  N NN  CC       OO   OO |"); 
		System.out.println("| CC   CC   III   NN   NNN  CC   CC  OO   OO |"); 
		System.out.println("|  CCCCC   IIIII  NN    NN   CCCCC    OOOOO  |");
		System.out.println("  -----------------------------------------\n");
		
		//Call for closing tickets older than 7	that are still open.
		if (tickets != null && !tickets.isEmpty()){
			closeActiveTicketsMoreThanSevenDaysOld(tickets);
		}
		
		do {
			// initial system menu
			if (user == null) {
				nestedExit = false;
				List<String> menu = Arrays.asList("Create Account", "Login", "Exit Program");
				List<String> menuSelections = Arrays.asList("C", "L", "X");
				LinkedHashMap<String, String> map = new LinkedHashMap<>();			
				// title
				printMenu("IT HELP DESK SYSTEM", menu, menuSelections);
				input = getInput(sc);
				System.out.println();
				// if invalid selection is made
				if (input.length() != 1) {
					System.out.println("Error - invalid selection!");
				} else {
					// select first character
					selection = Character.toUpperCase(input.charAt(0));
					// menu selection switch case
					switch (selection) {
					// create user
					case 'C':
						user = userMenu(sc, map, false);
						if (user != null) {
							users.add(user);
						}
						break;
					// login
					case 'L':
						user = userMenu(sc, map, true);
						break;
					// exit case
					case 'X':
						exit = getExit(sc);
					}
				}
			//staff user menu logic
			} else if (user instanceof StaffUser) {
				do {
					// declare menu selection lists
					List<String> menu = Arrays.asList("Create Ticket", "Logout");
					List<String> menuSelections = Arrays.asList("C", "X");

					// staff menu title
					printMenu("STAFF MENU", menu, menuSelections);
					input = getInput(sc);
					System.out.println();
					// invalid menu selection message
					if (input.length() != 1) {
						System.out.println("Error - invalid selection!");
					} else {
						// take user input and make it case insensitive
						selection = Character.toUpperCase(input.charAt(0));
						switch (selection) {
						// create ticket case
						case 'C':
							Ticket ticket = createTicketMenu(sc);
							if (ticket != null) {
								tickets.add(ticket);
							}
							break;
						//
						case 'P':
							for (Ticket tmp : tickets) {
								tmp.print();
							}
							break;
						// exit case
						case 'X':
							nestedExit = getExit(sc);
							if (nestedExit) {
								user = null;
							}
							break;
						// invalid selection case
						default:
							System.out.println("Error - invalid selection!");
						}
					}
				} while (user != null && !nestedExit);
				
			} else {
				do {
					// set menu selections
					List<String> menu = Arrays.asList("View Active Tickets", "Close Active Ticket", "Change Ticket Status",
							"View Inactive Tickets", "Logout");
					List<String> menuSelections = Arrays.asList("A", "C", "S", "I", "X");

					// menu title
					printMenu("TECH MENU", menu, menuSelections);
					input = getInput(sc);

					System.out.println();

					// validate selection input length
					if (input.length() != 1 && !compareString(input, "EXIT_RESUME")) {
						System.out.println("Error - invalid selection!");
					} else {
						// make selection case insensitive
						selection = Character.toUpperCase(input.charAt(0));

						// process user menu selection
						switch (selection) {
						// Close a active ticket
						case 'C':
							do {
								input = getInput(sc, "ticket number to be changed (eg:12345678-1) and press enter");
								if (!compareString(input, "EXIT_RESUME")) {
									if (tickets != null) {
										intInput = Integer.parseInt(input) - 1;
										Ticket tmp = tickets.get(intInput);
										if (intInput < tickets.size()) {
											System.out.println("Error - invalid ticket number!");
											break;
										} else if (!tmp.getStatus()) {
											System.out.println("Error - ticket is already closed!");
											break;
										} else if (tmp.isTechnician(user.getId())) {
											tmp.setStatus(false);
											((TechUser) user).setActiveCount(((TechUser) user).getActiveCount() - 1);
											((TechUser) user).setInActiveCount(((TechUser) user).getInActiveCount() + 1);
											System.out.printf("Ticket %s has been closed!", tmp.getId());
											break;
										} else {
											System.out.println("Error - Can not close another technician's ticket!");
										}
									} else {
										System.out.println("Error - There are currently no tickets in the database!");
										break;
									}
								}
								break;
							} while(!compareString(input, "EXIT_RESUME"));
							if (compareString(input, "EXIT_RESUME")) {
								input = "\0";
							}	
							break;
						// Display active tickets allocated to current logged in Tech	
						case 'A':
							((TechUser) user).printActiveTickets(tickets);
							break;
						/* Allow current logged in Tech to change the
						 *  status of a active ticket from open to closed
						 */
						case 'S':
							do {
								input = getInput(sc, "ticket number");
								if (!compareString(input, "EXIT_RESUME")) {
									if (tickets != null) {
										intInput = Integer.parseInt(input) - 1;
										Ticket tmp = tickets.get(intInput);
										if (intInput < tickets.size()) {
											System.out.println("Error - invalid ticket number!");
											break;
										} else if (!tmp.getStatus()) {
											System.out.println("Error - ticket is already closed!");
											break;
										} else if (tmp.isTechnician(user.getId())) {
											input = getInput(sc, "severity");
											TicketSeverity severity = checkTicketSeverity(input);
											tmp.setSeverity(severity);
											System.out.printf("Status of ticket %s has been changed!", tmp.getId());
											break;
										} else {
											System.out.println("Error - Can not change the status of another technician's ticket!");
										}
									}
								}
							} while(!compareString(input, "EXIT_RESUME"));
							break;
						case 'I':
							((TechUser) user).printInActiveTickets(tickets);
							break;
						// exit case
						case 'X':
							nestedExit = getExit(sc);
							if (nestedExit) {
								user = null;
							}
							break;
						// invalid selection case
						default:
							System.out.println("Error - invalid selection!");
						}
						System.out.println();
					}
				} while (user != null && !nestedExit);
			}
		} while (user != null || !exit);
		System.exit(0);
	}

	// print menu method
	protected static void printMenu(String title, List<String> menu, List<String> menuSelections) {
		System.out.printf("%s\n----------------------------------\n", title);
		System.out.printf("%-20s%s", "Menu Options", "Selection Key");
		System.out.println("\n----------------------------------\n");

		for (int i = 0; i < menu.size(); i++) {
			System.out.printf("%-30s%s\n", menu.get(i), menuSelections != null ? menuSelections.get(i) : i + 1);
		}
		System.out.println();
	}
	
	// method to print new ticket for user to check details
	protected static void printTicket(String title, List<String> menu, String[] newTicket) {
		System.out.printf("\n----------------------------------\n%s\n", title);
		System.out.println("----------------------------------\n");
		for (int i = 0, x=1; i < menu.size()-2; i++, x++) {
			System.out.printf("%-18s: %s\n", menu.get(i), newTicket != null ? newTicket[x]  : i+1);
		}
		System.out.println();
		System.out.println("----------------------------------\n");
	}
			

	// helper method to check ticket severity level string against Enum
	protected static Ticket.TicketSeverity checkTicketSeverity(String input) {
		if (compareString(input, Ticket.TicketSeverity.HIGH.name())) {
			return Ticket.TicketSeverity.HIGH;
		}
		if (compareString(input, Ticket.TicketSeverity.MEDIUM.name())) {
			return Ticket.TicketSeverity.MEDIUM;
		}
		if (compareString(input, Ticket.TicketSeverity.LOW.name())) {
			return Ticket.TicketSeverity.LOW;
		}
		return null;
	}

	protected static Boolean getExit(Scanner scanner) {
		String input = "\0";
		Boolean exit = false;
		System.out.println("Are you sure you want to exit? Yes / No");
		do {
			System.out.println("Enter your selection: ");
			input = scanner.nextLine();
			if (compareString(input, "Y") || compareString(input, "YES")) {
				exit = true;
			} else if (!compareString(input, "N") && !compareString(input, "NO")) {
				System.out.println("Error - invalid input, must select (Y)es or (N)o");
			} else {
				break;
			}
		} while (!exit && !compareString(input, "N") || !exit && !compareString(input, "NO"));
		return exit;
	}
	
	protected static String getInput(Scanner scanner) {
		return getInput(scanner, "selection");
	}

	// helper method to compare user input for correctness again field in use
	protected static String getInput(Scanner scanner, String request) {
		String input = "\0";
		Boolean exit = false;
		// Regex that matches email addresses
		String emailPattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
		// Regex that matches Australian formatted phone numbers
		String phonePattern = "^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$";

		if (compareString(request, "EXIT")) {
			exit = getExit(scanner);
			if (exit) {
				return "EXIT_RESUME";
			} else {
				return null;
			}
		}
		
		// request input
		System.out.printf("Enter your %s: ", request);
		input = scanner.nextLine();
		
		// if exit command
		if (compareString(input, "!X") && !compareString(request, "selection")) {
			System.out.println("Exiting will cause any progress to be lost");
			exit = getExit(scanner);
			if (exit) {
				return "EXIT_RESUME";
			} else {
				input = getInput(scanner, request);
			}
		}
		// if empty
		if (compareString(input, "")) {
			System.out.println("Error - input can not be empty!");
			input = getInput(scanner, request);
		}
		if (!compareString(input, "EXIT_RESUME")) {
			// if employee number
			if (compareString(request, "employee no")) {
				if (!input.matches("-?\\d+")) {
					System.out.println("Error - invalid input, must enter an integer!");
					input = getInput(scanner, request);
				}
			}
			// if ticket number
			if (compareString(request, "ticket number")) {
				if (input.length() < 10) { 
					System.out.println("Error - invalid ticket length!");
					input = getInput(scanner, request);
				} else {
					return input.substring(input.lastIndexOf('-') + 1, input.length());
				}
			}
			// if employee type
			if (compareString(request, "employee type")) {
				if (!compareString(input, "staff") && !compareString(input, "tech")) {
					System.out.println("Error - must enter either staff or tech!");
					input = getInput(scanner, request);
				}
			}
			// if invalid email
			if (compareString(request, "email") && !input.matches(emailPattern)) {
				System.out.println("Error - invalid email address!");
				input = getInput(scanner, request);
			}
			// if invalid phone number
			if (compareString(request, "contact number") && !input.matches(phonePattern)) {
				System.out.println("Error - invalid phone number, must use Australian format! e.g. +61290001234");
				input = getInput(scanner, request);
			}
			// if valid ticket severity
			if (compareString(request, "severity")) {
				if (checkTicketSeverity(input.toUpperCase()) == null) {
					System.out.println("Error - invalid severity, must be LOW, MEDIUM, OR HIGH!");
					input = getInput(scanner, request);
				}
				input = input.toUpperCase();
			}
		}
		
		return input;
	}
	// helper method to compare strings to verify user input
	protected static boolean compareString(String selection, String comparison) {
		if (selection.equalsIgnoreCase(comparison)) {
			return true;
		}
		return false;
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
	
	// Method to identify ticket allocation to available level Techs
	public static TechUser getAvailableTechnician(TicketSeverity severity) {
		Entry<TechUser, Integer> technician = null;

		Map<TechUser, Integer> availableTechnicians = new HashMap<TechUser, Integer>();

		for (User usr : users) {
			if (usr instanceof TechUser) {
				TechUser tmp = ((TechUser) usr);
				if (severity == TicketSeverity.HIGH) {
					if (tmp.getLevel() == 2) {
						availableTechnicians.put(tmp, tmp.getActiveCount());
					}
				} else {
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
	
	// method to create an initial user Id for new account
	protected static String generateUserId() {
		return String.valueOf(users.size() + 1);
	}

	protected static User userMenu(Scanner sc, LinkedHashMap<String, String> details, Boolean login) {
		Integer counter;
		User user = null;
		String employeeType = "\0";
		if (login) {
			details.put("Employee No", "");
			details.put("Password", "");
		} else {
			details.put("Password", "");
			details.put("First name", "");
			details.put("Last name", "");
			employeeType = getInput(sc, "Employee Type");
			if (compareString(employeeType, "staff")) {
				details.put("Email", "");
				details.put("Contact Number", "");
			} else {
				details.put("Tech Level", "");
			}
		}

		
		List<String> keys = new ArrayList<String>(details.keySet());
		for (counter = 0; counter < details.size(); counter++) {
			String key = keys.get(counter);
			String value = details.get(key).toString();
			value = getInput(sc, key);
			if (compareString(value, "EXIT_RESUME")) {
				System.out.println("Returning to login menu..");
				break;
			}
			details.remove(key);
			details.put(key, value);
		}

		if (counter == details.size()) {
			if (login) {
				Integer userId = Integer.parseInt(details.get("Employee No")) - 1;
				if (users.size() > userId && users.get(userId).login(details.get("Password"))) {
					user = users.get(userId);
					System.out.printf("Welcome %s %s!\n", user.getFirstName(), user.getLastName());
				} else {
					System.out.println("Error - invalid credentials, please try again!");
				}
				
			} else if (compareString(employeeType, "staff")) {
				user = new StaffUser(generateUserId(),
							details.get("Password"),
							details.get("First name"),
							details.get("Last name"),
							details.get("Email"),
							details.get("Contact Number"));
			} else {
				Integer techLevel = Integer.parseInt(details.get("Tech Level"));
				user = new TechUser(generateUserId(),
							details.get("Password"),
							details.get("First name"),
							details.get("Last name"),
							techLevel, 0, 0);
			}
		}
		return user;
	}
	
	// method for create ticket menu
	protected static Ticket createTicketMenu(Scanner sc) {
		String input = "\0", technicianId = "N\\A";
		Boolean exit = false;
		Ticket ticket = null;
		TechUser technician = null;
		String[] strTicket = new String[8];
		int ticketCounter = 0;
		Ticket.TicketSeverity severity = null;
		// create ticket menu options array
		List<String> menu = Arrays.asList("Surname", "Given name", "Staff number", "Email", "Contact number",
				"Description", "Severity", "Confirm", "Exit");
		// create ticket menu selection key array
		List<String> menuSelections = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "C", "X");

		do {
			strTicket[ticketCounter + 1] = getInput(sc, menu.get(ticketCounter));
			if (compareString(strTicket[ticketCounter + 1], "EXIT_RESUME")) {
				exit = true;
			} else {
				ticketCounter += 1;
			}
		} while (ticketCounter != (menu.size() - 2) && !exit);
	

		if (!exit) {
			severity = checkTicketSeverity(strTicket[7]);
			do {
//				System.out.println("If below Ticket is correct press C\n else select menu option to change details");
//				Arrays.stream(ticket).skip(1).forEach(System.out::println);
				printTicket("\nIf below Ticket is correct press C\n"
						+ "else select menu option to change details.\n\n"
						+ "NEW TICKET", menu, strTicket);
				printMenu("CREATE TICKET MENU", menu, menuSelections);
				input = getInput(sc);
				if (input.length() == 1) {
					switch (input.toUpperCase()) {
					case "1":
						strTicket[1] = getInput(sc, menu.get(0));
						break;
					case "2":
						strTicket[2] = getInput(sc, menu.get(1));
						break;
					case "3":
						strTicket[3] = getInput(sc, menu.get(2));
						break;
					case "4":
						strTicket[4] = getInput(sc, menu.get(3));
						break;
					case "5":
						strTicket[5] = getInput(sc, menu.get(4));
						break;
					case "6":
						strTicket[6] = getInput(sc, menu.get(5));
						break;
					case "7":
						strTicket[7] = getInput(sc, menu.get(6));
						severity = checkTicketSeverity(strTicket[7]);
						break;
					case "C":
						strTicket[0] = generateTicketId();
						technician = getAvailableTechnician(severity);
						if (technician != null) {
							technicianId = technician.getId();
							technician.setActiveCount(technician.getActiveCount() + 1);
						}
						ticket = new Ticket(strTicket[0], strTicket[1], strTicket[2], strTicket[3], strTicket[4], strTicket[5], strTicket[6],
								severity, technicianId);
						exit = true;
						break;
					case "X":
						exit = getExit(sc);
						break;
					default:
						System.out.println("Error - invalid selection!");
					}
				} else {
					System.out.println("Error - invalid selection!");
				}
			} while (!exit);
		}
		return ticket;
	}
	
	/* helper method to check ticket creation date is greater
	 *  than 7 days for auto ticket closure*/
	public static boolean dateChecker(String ticketId) {
		int difference = 0;
		String stripTicketId = ticketId.substring(0, 8);
		int ticketDate = Integer.parseInt(stripTicketId);
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		int currentDate = Integer.parseInt(date.format(dateFormat));
		difference = currentDate - ticketDate;
		if (difference > 7) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Method for closing open tickets older than seven days
	public static void closeActiveTicketsMoreThanSevenDaysOld(ArrayList<Ticket> tickets) {
		int closedTicketCount = 0;
		if (tickets != null) {
			for (Ticket tmp : tickets) {
				if (compareString(tmp.getStringStatus(), "OPEN") && dateChecker(tmp.getId())) {
//					System.out.println(tmp.getId());
//					System.out.println(tmp.getStringStatus());
					tmp.setStatus(false);
					// Adjust the tech active verse inactive count
					String techID = tmp.getTechnicianId();
					int techIdChangeStatus = Integer.parseInt(techID)-1;
					User tmpTechUser = users.get(techIdChangeStatus);
					((TechUser) tmpTechUser).setActiveCount(((TechUser) tmpTechUser).getActiveCount() - 1);
					((TechUser) tmpTechUser).setInActiveCount(((TechUser) tmpTechUser).getInActiveCount() + 1);
					closedTicketCount++;
					
				}
			}
		}
		if (closedTicketCount>0) {
			System.out.printf("Number of active tickets closed: %s\n"
					+ "(due to age older than 7 days)\n", closedTicketCount);
			System.out.print("\n----------------------------------\n\n");
		}
				
	}
}
