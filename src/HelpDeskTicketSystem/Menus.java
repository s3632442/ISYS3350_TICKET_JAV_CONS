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
		// Initialize scanner
		Scanner sc = new Scanner(System.in);
		// Initialize selection variable to ASCII null to keep compiler happy
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
				// title
				printMenu("IT HELP DESK SYSTEM", menu, menuSelections);
				input = getInput(sc);
				System.out.println();
				// if invalid selection is made
				if (input.length() != 1) {
					System.out.println("Error - invalid selection");
				} else {
					// select first character
					selection = Character.toUpperCase(input.charAt(0));
					// menu selection switch case
					switch (selection) {
					// create user
					case 'C':
						user = userMenu(sc, false);
						if (user != null) {
							users.add(user);
						}
						break;
					// login
					case 'L':
						user = userMenu(sc, true);
						break;
					// exit case
					case 'X':
						exit = getConfirmInput(sc, "exit");
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
						System.out.println("Error - invalid selection");
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
						// exit case
						case 'X':
							nestedExit = getConfirmInput(sc, "exit");
							if (nestedExit) {
								user = null;
							}
							break;
						// invalid selection case
						default:
							System.out.println("Error - invalid selection");
						}
					}
				} while (user != null && !nestedExit);
				
			} else {
				do {
					// set menu selections
					List<String> menu = Arrays.asList("View Active Tickets", "Close Active Ticket",
							"View Inactive Tickets", "Logout");
					List<String> menuSelections = Arrays.asList("A", "C", "I", "X");

					// menu title
					printMenu("TECH MENU", menu, menuSelections);
					input = getInput(sc);

					System.out.println();

					// validate selection input length
					if (input.length() != 1 && !compareString(input, "EXIT_RESUME")) {
						System.out.println("Error - invalid selection");
					} else {
						// make selection case insensitive
						selection = Character.toUpperCase(input.charAt(0));

						// process user menu selection
						switch (selection) {
						// Close a active ticket
						case 'C':
							Boolean confirm = false;
							do {
								input = getInput(sc, "ticket number to be changed (eg 12345678-1) and press enter");
								if (!compareString(input, "EXIT_RESUME")) {
									if (tickets != null) {
										intInput = Integer.parseInt(input) - 1;
										if (intInput >= tickets.size()) {
											System.out.println("Error - invalid ticket number, please try again");
										} else {
											Ticket tmp = tickets.get(intInput);
											if (!tmp.getStatus()) {
												System.out.println("Error - ticket is already closed");
												enterToContinue(sc);
												confirm = true;
											} else if (tmp.isTechnician(user.getId())) {
												System.out.printf("Attempting to remove ticket: %s\n");
												confirm = getConfirmInput(sc, "confirm");
												if (confirm) {
													tmp.setStatus(false);
													((TechUser) user)
															.setActiveCount(((TechUser) user).getActiveCount() - 1);
													((TechUser) user)
															.setInActiveCount(((TechUser) user).getInActiveCount() + 1);
													System.out.printf("Ticket %s has been closed", tmp.getId());
													enterToContinue(sc);
												}
											} else {
												System.out.println("Error - Can not close another technician's ticket");
												enterToContinue(sc);
												confirm = true;
											}
										}
									} else {
										System.out.println("Error - There are currently no tickets in the database");
										enterToContinue(sc);
										break;
									}
								}
							} while(!confirm  && !compareString(input, "EXIT_RESUME"));
							break;
						// Display active tickets allocated to current logged in Tech	
						case 'A':
							((TechUser) user).printActiveTickets(tickets);
							enterToContinue(sc);
							break;
						// print technician's archived tickets case
						case 'I':
							((TechUser) user).printInActiveTickets(tickets);
							enterToContinue(sc);
							break;
						// exit case
						case 'X':
							nestedExit = getConfirmInput(sc, "exit");
							if (nestedExit) {
								user = null;
							}
							break;
						// invalid selection case
						default:
							System.out.println("Error - invalid selection");
						}
						System.out.println();
					}
				} while (user != null && !nestedExit);
			}
		} while (user != null || !exit);
		System.exit(0);
	}

	protected static void printHeader(String title) {
		System.out.printf("%s\n----------------------------------\n", title);
		System.out.printf("%-20s%s", "Menu Options", "Selection Key");
		System.out.println("\n----------------------------------");
	}
	/**
	 * Helper method for printing complex and consistent menus
	 * @param title - header to print
	 * @param menu - array of user-friendly terms to print
	 * @param menuSelections - array of input items the user can enter
	 */
	protected static void printMenu(String title, List<String> menu, List<String> menuSelections) {
		printHeader(title);

		for (int i = 0; i < menu.size(); i++) {
			System.out.printf("%-30s%s\n", menu.get(i), menuSelections != null ? menuSelections.get(i) : i + 1);
		}
		System.out.println();
	}
	
	/**
	 * Helper method to print new ticket for user to check details.
	 * @param title - 
	 * @param menu
	 * @param newTicket
	 */
	protected static void printTicket(String title, List<String> menu, String[] newTicket) {
		System.out.printf("\n----------------------------------\n%s\n", title);
		System.out.println("----------------------------------\n");
		for (int i = 0, x=1; i < menu.size()-2; i++, x++) {
			System.out.printf("%-18s: %s\n", menu.get(i), newTicket != null ? newTicket[x]  : i+1);
		}
		System.out.println();
		System.out.println("----------------------------------\n");
	}
			

	/**
	 * Helper method to check string for ticket severity.
	 * @param input
	 * @return TicketSeverity : null
	 */
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
	
	protected static void enterToContinue(Scanner sc) {
		System.out.println("Press Enter key to continue..");
		sc.nextLine();
	}

	/**
	 * Helper method for user input to check for exit/confirm conditions
	 * @param scanner
	 * @param type
	 * @return boolean
	 */
	protected static Boolean getConfirmInput(Scanner scanner, String type) {
		String input = "\0";
		Boolean exit = false;
		System.out.printf("Did you want to %s? [Y]es or [N]o\n", type);
		// retrieve input until user gives valid input of [Y]es or [N]o
		do {
			System.out.print("Enter your selection: ");
			input = scanner.nextLine();
			if (compareString(input, "Y") || compareString(input, "YES")) {
				exit = true;
			} else if (!compareString(input, "N") && !compareString(input, "NO")) {
				System.out.println("Error - invalid input, must select [Y]es or [N]o");
			} else {
				break;
			}
		} while (!exit && !compareString(input, "N") || !exit && !compareString(input, "NO"));
		return exit;
	}
	
	/**
	 * Wrapper method for default getInput usage with request="selection"
	 * @param scanner
	 * @return String
	 */
	protected static String getInput(Scanner scanner) {
		return getInput(scanner, "selection");
	}

	/**
	 * Recursive helper method to compare user input for corrections.
	 * All input is passed and parsed via this function.
	 * @param scanner
	 * @param request
	 * @return String : null
	 */
	protected static String getInput(Scanner scanner, String request) {
		String input = "\0";
		Boolean exit = false;
		// Regex that matches email addresses
		String emailPattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
		// Regex that matches Australian formatted phone numbers
		String phonePattern = "^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$";

		if (compareString(request, "exit") || compareString(request, "confirm")) {
			exit = getConfirmInput(scanner, request);
			if (exit) {
				if (compareString(request, "exit")) {
					return "EXIT_RESUME";
				}
				return "SUCCESS";
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
			exit = getConfirmInput(scanner, "exit");
			if (exit) {
				return "EXIT_RESUME";
			} else {
				input = getInput(scanner, request);
			}
		}
		// if empty
		if (compareString(input, "")) {
			System.out.println("Error - input can not be empty");
			input = getInput(scanner, request);
		}
		if (!compareString(input, "EXIT_RESUME")) {
			// if employee number
			if (compareString(request, "employee id number")) {
				if (!input.matches("-?\\d+")) {
					System.out.println("Error - invalid input, must enter an integer");
					input = getInput(scanner, request);
				}
			}
			// if ticket number
			if (request.contains("ticket number")) {
				if (input.length() < 10) { 
					System.out.println("Error - invalid ticket length, ticket must be 10 or more digits long (eg 12345678-1)");
					input = getInput(scanner, request);
				} else {
					String out = input.substring(input.lastIndexOf('-') + 1, input.length());
					System.out.println(out);
					return out;
				}
			}
			// if employee type
			if (request.contains("type of employment")) {
				if (!compareString(input, "staff") && !compareString(input, "s") &&
					!compareString(input, "tech") && !compareString(input, "t")) {
					System.out.println("Error - invalid type of employment, must enter either [S]taff or [T]ech");
					input = getInput(scanner, request);
				} else if (compareString(input, "s")) {
					input = "staff";
				} else if (compareString(input, "t")) {
					input = "tech";
				}
			}
			// if invalid email
			if (compareString(request, "email address") && !input.matches(emailPattern)) {
				System.out.println("Error - invalid email address");
				input = getInput(scanner, request);
			}
			// if invalid phone number
			if (request.contains("contact number") && !input.matches(phonePattern)) {
				System.out.println("Error - invalid phone number, must use Australian format e.g. +61290001234");
				input = getInput(scanner, request);
			}
			// if valid ticket severity
			if (compareString(request, "issue severity")) {
				if (checkTicketSeverity(input.toUpperCase()) == null) {
					System.out.println("Error - invalid severity, must be LOW, MEDIUM, OR HIGH");
					input = getInput(scanner, request);
				}
				input = input.toUpperCase();
			}
		}
		
		return input;
	}

	/**
	 * Helper method to compare strings.
	 * @param selection
	 * @param comparison
	 * @return boolean
	 */
	protected static boolean compareString(String selection, String comparison) {
		if (selection.equalsIgnoreCase(comparison)) {
			return true;
		}
		return false;
	}

	/**
	 * Generate a ticket identification number
	 * String Format: yyyyMMdd-ticketIDCounter
	 * @return String
	 */
	protected static String generateTicketId() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = date.format(dateFormat);
		return formattedDate + "-" + Ticket.ticketIDCounter;
	}
	
	/**
	 * Method to identify ticket allocation to available tech.
	 * Allocates to tech with appropriate techLevel and with the least
	 * amount of active tickets.
	 * @param severity
	 * @return TechUser : null;
	 */
	public static TechUser getAvailableTechnician(TicketSeverity severity) {
		Entry<TechUser, Integer> technician = null;

		Map<TechUser, Integer> availableTechnicians = new HashMap<TechUser, Integer>();

		/**
		 * 	get a list of technicians with the appropriate level for severity
		 *  HIGH=2
		 * 	MEDIUM/LOW=1
		 */
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
		// test whether any technicians were found
		if (availableTechnicians == null || availableTechnicians.isEmpty()) {
			return null;
		}
		/**
		 * Iterate over the available technicians to find the
		 * technician with the least amount of currently active tickets
		 */
		for (Entry<TechUser, Integer> entry : availableTechnicians.entrySet()) {
			if (technician == null || technician.getValue() > entry.getValue()) {
				technician = entry;
			}
		}
		return technician.getKey();
	}
	
	/**
	 * Method to create an initial user Id for new account
	 * @return String
	 */
	protected static String generateUserId() {
		if (users != null && !users.isEmpty()) {
			return String.valueOf(users.size() + 1);
		} else {
			return "1";
		}
	}

	/**
	 * Helper method to copy the keys and values of a map to a new map
	 * @param map - Map to copy
	 * @return LinkedHashMap<String, String>
	 */
	protected static LinkedHashMap<String, String> copyMap(LinkedHashMap<String, String> map) {
		LinkedHashMap<String, String> newMap = new LinkedHashMap<>();
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = map.get(key).toString();
			newMap.put(key, value);
		}
		return newMap;
	}

	/**
	 * Helper method to print a map out like a menu
	 * @param map - Map to print
	 */
	protected static void printMapMenu(String title, LinkedHashMap<String, String> map) {
		printHeader(title);

		ArrayList<String> keys = new ArrayList<String>(map.keySet());

		for (int i = 0; i < keys.size(); i++) {
			String s = keys.get(i);
			String value = map.get(s);
			if (value.contains("contact number")) {
				String[] str = value.split(" ");
				value = str[0] + str[1];
			}
			System.out.printf("%-20s%s\n", value != null ? value : "N\\A", s);
		}
	}

	/**
	 * Helper method to edit the values stored in a linkedhashmap
	 * @param sc - Scanner to read input
	 * @param originalMap - Copy of the original map for menu printing
	 * @param map - altered map that may see further alteration
	 * @return LinkedHashMap<String, String> : null
	 */
	protected static LinkedHashMap<String, String> editMap(Scanner sc, String title, LinkedHashMap<String, String> originalMap,
			LinkedHashMap<String, String> map) {
		boolean exit = false;
		String input = "\0";
		do {
			printMapMenu(title, originalMap);
			input = getInput(sc);
			System.out.println(input);
			boolean exists = map.containsKey(input);
			if (!exists || input.length() != 1) {
				System.out.println("Error - invalid selection, try again");
			} else if (compareString(input, "EXIT_RESUME")) {
				System.out.println("Returning to menu..");
				map = null;
				exit = true;
			} else {
				String key = input;
				String value = originalMap.get(key);
				input = getInput(sc, value);
				if (compareString(key, "c")) {
					if (input != null) {
						exit = true;
					} else {
						return null;
					}
				} else {
					map.remove(key);
					map.put(key, input);
				}
			}

		} while (!exit && !compareString(input, "EXIT_RESUME"));
		return map;
	}

	/**
	 * Helper method to traverse over linkedhashmap and retrieve input
	 * @param sc - Scanner to read input
	 * @param map - Map to traverse and update
	 * @return LinkedHashMap<String, String> : null
	 */
	protected static LinkedHashMap<String, String> traverseMap(Scanner sc, LinkedHashMap<String, String> map) {
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		String input = "\0";
		Integer counter = 0;
		do {
			String key = keys.get(counter);
			input = map.get(key).toString();
			if (compareString(key, "c")) {
				System.out.print("| ");
				for (int i = 0; i < keys.size() - 1; i++) {
					String s = keys.get(i);
					System.out.printf("%s: %s | ", s, map.get(s).toString());
				}
				System.out.println();
			}

			if (map.size() > 1 && compareString(key, "staff") || compareString(key, "tech") && map.size() > 1) { counter++; } 
			else { 
				input = getInput(sc, input);
				if (input != null && compareString(input, "EXIT_RESUME")) {
					System.out.println("Returning to menu..");
					return null;
				} else {
					map.remove(key);
					map.put(key, input);
					counter++;
				} 
			}
			
		} while (map.size() > counter && !compareString(input, "EXIT_RESUME"));
		return map;
	}

	/**
	 * Helper method to create/login a user and allow for editing
	 * @param sc
	 * @param login
	 * @return User : null
	 */
	protected static User userMenu(Scanner sc, Boolean login) {
		
		String type =  "\0";
		Boolean exit = false;
		User user = null; 		// user to return, always returns
		LinkedHashMap<String, String> map = new LinkedHashMap<>(), 
			originalMap = new LinkedHashMap<>();	// details needed from user

		/**
		 * setup the information needed from the user
		 */
		if (login) {
			originalMap.put("employee no", "employee id number");
			originalMap.put("password", "password");
		} else {
			type = getInput(sc, "type of employment, [S]taff or [T]ech");
			if (type != null && !compareString(type, "EXIT_RESUME")) {
				originalMap.put("1", "new password");
				originalMap.put("2", "given name");
				originalMap.put("3", "surname");
				if (compareString(type, "staff")) {
					originalMap.put("4", "email address");
					originalMap.put("5", "contact number using Australian format (eg 61290001234)");
				} else {
					originalMap.put("4", "technician level (Can be either 1 or 2)");
				}
				originalMap.put("c", "confirm");
			} else {
				return null;
			}
		}

		map = copyMap(originalMap);
		map = traverseMap(sc, map);

		/**
		 * if counter has become equal to map.size()
		 * checking if the user details were filled out and not exited early
		 */
		do {
			if (map != null) {
				if (map.get("c") != null || login) {
					if (login) {
						Integer userId = Integer.parseInt(map.get("employee no")) - 1;
						if (users.size() > userId && users.get(userId).login(map.get("password"))) {
							user = users.get(userId);
							System.out.printf("Welcome %s %s!\n", user.getFirstName(), user.getLastName());
							return user;
						} else {
							System.out.println("Error - invalid credentials, please try again");
							user = userMenu(sc, true);
						}
					} else {
						if (compareString(type, "staff")) {
							user = new StaffUser(generateUserId(), 
										map.get("1"), map.get("2"), map.get("3"),
										map.get("4"), map.get("5"));
						} else {
							Integer techLevel = Integer.parseInt(map.get("4"));
							user = new TechUser(generateUserId(), 
										map.get("1"), map.get("2"), map.get("3"),
										techLevel, 0, 0);
						}
						return user;
					}
				} else {
					map.remove("c");
					map.put("c", "confirm");
					map = editMap(sc, "EDIT USER MENU", originalMap, map);
				}
			} else {
				exit = true;
			}
		} while (!exit);
		return user; // will return null
	}
	
	/**
	 * Helper method to create a ticket and allow for editing
	 * @param sc - Scanner to read input
	 * @return Ticket : null
	 */
	protected static Ticket createTicketMenu(Scanner sc) {

		Boolean exit = false;
		Ticket ticket = null;

		LinkedHashMap<String, String> map = new LinkedHashMap<>(),
			originalMap = new LinkedHashMap<>();

		originalMap.put("1", "surname");
		originalMap.put("2", "given name");
		originalMap.put("3", "email address");
		originalMap.put("4", "contact number");
		originalMap.put("5", "description");
		originalMap.put("6", "issue severity");
		originalMap.put("c", "confirm");

		map = copyMap(originalMap);
		map = traverseMap(sc, map);

		do {
			if (map != null) {
				if (map.get("c") != null) {
					String ticketId = generateTicketId();
					Ticket.TicketSeverity severity = checkTicketSeverity(map.get("6"));
					String techId = "N\\A";
					TechUser technician = getAvailableTechnician(severity);

					if (technician != null) {
						techId = technician.getId();
						technician.setActiveCount(technician.getActiveCount() + 1);
					}
					ticket = new Ticket(ticketId, map.get("1"), map.get("2"), user.getId(), map.get("3"), map.get("4"),
							map.get("5"), severity, techId);
					exit = true;
				} else {
					map.remove("c");
					map.put("c", "confirm");
					map = editMap(sc, "EDIT TICKET MENU", originalMap, map);
				}
			} else {
				exit = true;
			}
		} while (!exit);
		
		return ticket;
	}


	/**
	 * helper method to check ticket creation date is greater
	 * than 7 days for auto ticket closure.
	 * @param ticketId
	 * @return boolean
	 */
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
	
	/**
	 * method for closing open tickets older than seven days
	 * @param tickets
	 */
	public static void closeActiveTicketsMoreThanSevenDaysOld(ArrayList<Ticket> tickets) {
		int closedTicketCount = 0;
		if (tickets != null) {
			for (Ticket tmp : tickets) {
				if (compareString(tmp.getStringStatus(), "OPEN") && dateChecker(tmp.getId())) {
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
