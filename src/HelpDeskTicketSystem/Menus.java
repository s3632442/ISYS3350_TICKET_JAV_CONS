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
			closeActiveTicketsMoreThanSevenDaysOld(tickets, users);
			fixMissingTechnicians();
		}
		
		do {
			// initial system menu
			if (user == null) {
				nestedExit = false;
				List<String> menu = Arrays.asList("Create Account", "Login", "Exit Program");
				List<String> menuSelections = Arrays.asList("C", "L", "X");		
				// title
				printMenu("IT HELP DESK SYSTEM", menu, menuSelections);
				System.out.println("To exit out of a input field type \"!x\" and press enter.\n");
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
				} while (user != null);
				
			} else {
				do {
					// set menu selections
					List<String> menu = Arrays.asList("View Active Tickets", "Close Active Ticket", "Change Ticket Severity",
							"View Inactive Tickets", "Logout");
					List<String> menuSelections = Arrays.asList("A", "C", "S", "I", "X");
					Ticket ticket = null;
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
							ticket = ticketTechMenu(sc, true);
							break;
						/**
						 * Change ticket severity
						 */
						case 'S':
							ticket = ticketTechMenu(sc, false);
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
				} while (user != null);
			}
		} while (user != null || !exit);
		System.exit(0);
	}

	/**
	 * Helper method for printing the header on every select-style menu
	 * @param title - Menu title
	 */
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
	
	/**
	 * Helper method to halt error output and wait for the user to press enter
	 * @param sc - Scanner to read enter from
	 */
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
			System.out.print("Type your selection and press enter: ");
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

		// if user is attempting to exit or needs to confirm input
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
		System.out.printf("Type your %s and press enter: ", request);
		input = scanner.nextLine();
		
		// if user is attempting an early exit
		if (compareString(input, "!X") && !compareString(request, "selection")) {
			System.out.println("Exiting will cause any progress to be lost");
			exit = getConfirmInput(scanner, "exit");
			if (exit) {
				return "EXIT_RESUME";
			} else {
				input = getInput(scanner, request);
			}
		}
		// if user has inputed nothing
		if (compareString(input, "")) {
			System.out.println("Error - input can not be empty");
			input = getInput(scanner, request);
		}

		// if user is not attempting to early exit
		if (!compareString(input, "EXIT_RESUME")) {
			// if user is inputting an employee id
			if (compareString(request, "employee id number")) {
				if (!input.matches("-?\\d+")) {
					System.out.println("Error - invalid input, must enter an integer");
					input = getInput(scanner, request);
				}
			}
			// if user is inputting a ticket number e.g. 20190101-1
			if (request.contains("ticket number")) {
				if (input.length() < 10) { 
					System.out.println("Error - invalid ticket length, ticket must be 10 or more digits long (eg 12345678-1)");
					input = getInput(scanner, request);
				} else {
					String out = input.substring(input.lastIndexOf('-') + 1, input.length());
					return out;
				}
			}
			// if user is inputting their employment type e.g. tech or staff
			if (request.contains("type of employment")) {
				// if user has inputted neither staff or tech callback to getInput()
				if (!compareString(input, "staff") && !compareString(input, "s") &&
					!compareString(input, "tech") && !compareString(input, "t")) {
					System.out.println("Error - invalid type of employment, must enter either [S]taff or [T]ech");
					input = getInput(scanner, request);
				} else if (compareString(input, "s")) {
					input = "staff";					// return staff if s for consistency
				} else if (compareString(input, "t")) {
					input = "tech";						// return tech if t for consistency
				}
			}
			// if user is inputting an invalid email address callback to getInput()
			if (compareString(request, "email address") && !input.matches(emailPattern)) {
				System.out.println("Error - invalid email address");
				input = getInput(scanner, request);
			}
			// if user is inputting an invalid phone number callback to getInput()
			if (request.contains("contact number") && !input.matches(phonePattern)) {
				System.out.println("Error - invalid phone number, must use Australian format e.g. +61290001234");
				input = getInput(scanner, request);
			}
			// if user is inputting a ticket severity
			if (compareString(request, "issue severity")) {
				// if user is inputting an invalid ticket severity callback to getInput()
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
		if (selection != null && comparison != null && selection.equalsIgnoreCase(comparison)) {
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
	 * Fix the tickets array if it contains a ticket that has
	 * not been assigned a technician
	 * Will return false if it is unable to allocate a technician to any ticket
	 * @return Boolean
	 */
	protected static Boolean fixMissingTechnicians() {
		Boolean result = false;
		Integer counter = 0;
		for (Ticket tmp : tickets) {
			if (compareString(tmp.getTechnicianId(), "N\\A")) {
				TechUser technician = getAvailableTechnician(tmp.getSeverity());
				if (technician == null) {
					counter++;
					continue;
				} else {
					tmp.setTechnicianId(technician.getId());
					technician.setActiveCount(technician.getActiveCount() + 1);
				}
			}
		}
		if (counter > 0) { result = false; }
		return result;
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
			if (technician == null || technician.getValue() < entry.getValue()) {
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
	 * Helper method to split a menu string when it is too long in length
	 * @param item - Menu item to split
	 * @return String
	 */
	protected static String splitMenuString(String item) {
		String[] str = item.split(" ");
		return str[0] + " " + str[1];
	}

	/**
	 * Helper method to capitalize the 
	 * @param str
	 * @return
	 */
	protected static String capitalize(String str) {
		if (str == null) return str;
		String out = "";
		String[] tmp = str.split(" ");
		if (tmp.length > 1) {
			for (int i = 0; i < tmp.length; i++) {
				tmp[i] = tmp[i].substring(0,1).toUpperCase() + tmp[i].substring(1);
			}
			out = tmp[0] + " " + tmp[1];
		} else {
			out = str.substring(0,1).toUpperCase() + str.substring(1);
		}
		return out;
	}
	
	/**
	 * Helper method to copy the keys and values of a map to a new map
	 * @param map - Map to copy
	 * @return LinkedHashMap<String, String>
	 */
	protected static LinkedHashMap<String, String> copyMap(LinkedHashMap<String, String> map) {
		LinkedHashMap<String, String> newMap = new LinkedHashMap<>();

		// indexed keys to iterate over LinkedHashMap to retain order
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		
		// going over hashmap and copy the keys/values to be the exact same
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

		// indexed keys to iterate over LinkedHashMap to retain order
		ArrayList<String> keys = new ArrayList<String>(map.keySet());

		// going over hashmaps to print a menu to output
		for (int i = 0; i < keys.size(); i++) {
			String r = keys.get(i);
			String l = map.get(r);
			if (l.length() > 15) {
				l = splitMenuString(l);
			}
			System.out.printf("%-30s%s\n", capitalize(l), capitalize(r));
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

		// edit map until user confirms no/yes 
		do {
			printMapMenu(title, originalMap);
			input = getInput(sc);
			boolean exists = map.containsKey(input);

			// if input does not exist as a key continue loop
			if (!exists || input.length() != 1) {
				System.out.println("Error - invalid selection, try again");
			} 
			// if user is attempting to exit
			else if (compareString(input, "EXIT_RESUME")) {
				System.out.println("Returning to menu..");
				map = null;
				exit = true;
			} 
			// else attempt to grab the key entered and modify the value
			else {
				String key = input;
				String value = originalMap.get(key);
				input = getInput(sc, value);

				// if exit menu
				if (compareString(key, "x")) {
					// if user has entered yes, exit without saving
					if (input != null) {
						map = null;
						exit = true;
					}
				} 
				// else if confirm menu
				else if (compareString(key, "c")) {
					if (compareString(input, "SUCCESS")) {
						exit = true;
					}
				}
				// else update the value the user chose
				else {
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
		Integer counter = 0, length = 0;

		if (map != null && map.get("x") != null) {
			length = map.size() - 1;
		} else {
			length = map.size();
		}
		LinkedHashMap<String, String> copy = copyMap(map);

		// do until the user attempts to exit or the map is filled
		do {

			String key = keys.get(counter);		// get key at current index
			input = map.get(key).toString();	// get value at current index

			if (compareString(key, "x")) {
				continue;
			}
			/**
			 * if key is confirm, print current details to this point
			 */
			if (compareString(key, "c")) {
				for (int i = 0; i < keys.size() - 2; i++) {
					String l = copy.get(keys.get(i)).toString();
					String r = map.get(keys.get(i)).toString();
					if (l.length() > 15) {
						l = splitMenuString(l);
					}
					System.out.printf("%s: %s \n", capitalize(l), r);
				}
				System.out.println();
			}

			// skip previously provided input for userMenu
			if (map.size() > 1 && compareString(key, "staff") || compareString(key, "tech") && map.size() > 1) { counter++; } 
			
			// replace the value at key in the hashmap with user input
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
			
		} while (length > counter && !compareString(input, "EXIT_RESUME"));
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
				originalMap.put("x", "exit");
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
			// if user is not attempting to exit early
			if (map != null && !map.containsValue("EXIT_RESUME")) {

				// if user has confirmed or is trying to login
				if (map.get("c") != null || login) {

					// if logging in
					if (login) {

						// parse user id
						Integer userId = Integer.parseInt(map.get("employee no")) - 1;
						
						// if user exists in the database
						if (users.size() > userId && users.get(userId).login(map.get("password"))) {
							user = users.get(userId);
							System.out.printf("Welcome %s %s!\n", user.getFirstName(), user.getLastName());
							return user;
						} 
						// user has entered invalid credentials, callback to userMenu until user==null
						else {
							System.out.println("Error - invalid credentials, please try again");
							map = copyMap(originalMap);
							map = traverseMap(sc, map);
							if (map == null) {
								exit = true;
							}
						}
					} 
					// else user must be creating account
					else {
						// create staffUser
						if (compareString(type, "staff")) {
							user = new StaffUser(generateUserId(), 
										map.get("1"), map.get("2"), map.get("3"),
										map.get("4"), map.get("5"));
						} 
						// create TechUser
						else {
							Integer techLevel = Integer.parseInt(map.get("4"));
							user = new TechUser(generateUserId(), 
										map.get("1"), map.get("2"), map.get("3"),
										techLevel, 0, 0);
						}
						return user;
					}
				} 
				// else user wants to edit details
				else {
					map.remove("c");
					map.put("c", "confirm");
					map = editMap(sc, "EDIT USER MENU", originalMap, map);
				}
			} 
			// else user wants to exit
			else {
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
		originalMap.put("x", "exit");
		
		map = copyMap(originalMap);
		map = traverseMap(sc, map);

		// do until user explicitly exits or a ticket is successfully created
		do {
			// if map == null then user is trying to exit
			if (map != null) {

				// if key=c is not null then the user has confirmed their details
				if (map.get("c") != null) {
					// build system-side ticket attributes
					String ticketId = generateTicketId();
					Ticket.TicketSeverity severity = checkTicketSeverity(map.get("6"));
					String techId = "N\\A";
					TechUser technician = getAvailableTechnician(severity);

					// if technician was found
					if (technician != null) {
						techId = technician.getId();
						technician.setActiveCount(technician.getActiveCount() + 1);
					}

					// create ticket
					ticket = new Ticket(ticketId, map.get("1"), map.get("2"), user != null ? user.getId() : "N\\A", map.get("3"), map.get("4"),
							map.get("5"), severity, techId);
					exit = true;
				} 
				
				// else the user wants to edit their details
				else {
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
	 * Helper method for tech to deal with ticket
	 * Allows for tech to either close or change the severity of a ticket
	 * @param sc - Scanner to read input
	 * @param close - true=close ticket, false=change severity
	 * @return Ticket : null
	 */
	protected static Ticket ticketTechMenu(Scanner sc, Boolean close) {
		Boolean exit = false;
		String input = "\0";
		Ticket ticket = null;
		Integer ticketId;

		/**
		 * do until user explicitly exits, the ticket cannot be modified, or
		 * the ticket is successfully changed
		 */
		do {
			input = getInput(sc, "ticket number to be changed (eg 12345678-1)");
			
			// if user is not trying to exit
			if (!compareString(input, "EXIT_RESUME")) {
				
				// if there are tickets in the database
				if (tickets != null) {
					// parse ticket id
					ticketId = Integer.parseInt(input) - 1;
					
					// if ticket does not exist
					if (ticketId >= tickets.size()) {
						System.out.println("Error - invalid ticket number, please try again");
					} 
					// else process the ticket
					else {
						ticket = tickets.get(ticketId);

						// if ticket is closed exit do-while
						if (!ticket.getStatus()) {
							System.out.println("Error - ticket is closed");
							enterToContinue(sc);
							exit = true;
						} 
						// if user is the allocated technician modify the ticket
						else if (ticket.isTechnician(user.getId())) {
							System.out.printf("Attempting to modify ticket: %s\n", ticket.getId());
							
							// confirm ticket is correct with user
							exit = getConfirmInput(sc, "confirm");
							
							// if yes
							if (exit) {
								// close ticket
								if (close) {
									ticket.setStatus(false);
									((TechUser)user).setActiveCount(((TechUser)user).getActiveCount() - 1);
									((TechUser)user).setInActiveCount(((TechUser)user).getInActiveCount() + 1);
									System.out.printf("Ticket %s has been closed", ticket.getId());
								} 
								// change severity and/or assigned technician
								else {
									input = getInput(sc, "issue severity");
									TicketSeverity severity = checkTicketSeverity(input);
									if (severity != null) {
										TechUser technician = getAvailableTechnician(severity);
										ticket.setSeverity(severity);
										if (technician != null) {
											if (!compareString(technician.getId(), user.getId())) {
												((TechUser) user)
														.setActiveCount(((TechUser) user).getActiveCount() - 1);
												((TechUser) user)
														.setInActiveCount(((TechUser) user).getInActiveCount() + 1);
												ticket.setTechnicianId(technician.getId());
												technician.setActiveCount(technician.getActiveCount() + 1);
											}
										}
										System.out.printf("Ticket %s has been changed", ticket.getId());
									}
								}
							}		
						} 
						// else user is not the technician, exit do-while
						else {
							System.out.println("Error - can not change another technician's ticket");
							enterToContinue(sc);
							exit = true;
						}
					}
				} else {
					System.out.println("Error - there are no tickets in the database");
					enterToContinue(sc);
					exit = true;
				}
			} else {
				exit = true;
			}
		} while (!exit);
		return ticket; // can return null
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
	public static void closeActiveTicketsMoreThanSevenDaysOld(ArrayList<Ticket> tickets, ArrayList<User> users) {
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
