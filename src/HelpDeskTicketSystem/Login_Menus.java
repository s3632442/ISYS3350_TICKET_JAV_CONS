package HelpDeskTicketSystem;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Login_Menus {
	// array of created ticket objects
	private static ArrayList<CreateTicket> tickets = new ArrayList<CreateTicket>();

	public static void main(String[] args) {
		String userInput;
		Scanner sc = new Scanner(System.in);
		// Initialize selection variable to ASCII null to keep compiler happy
		char selection = '\0';
		List<String> menu = Arrays.asList("", "");
		List<String> menuSelections = Arrays.asList("", "");

		// variables for login dialogue
		BufferedReader getIt = new BufferedReader(new InputStreamReader(System.in));
		String userNO = "";
		String userPWD = "";
		String credNO = "";
		String credPWD = "";
		boolean exit = false;

		// Check to see is persistent data for tickets and load if present
		loadTicketData("ITHelpDeskTickets.txt");

		{
			try {
				// open credentials file
				FileInputStream fstream = new FileInputStream("userpass.txt");

				// create data input stream and buffered reader
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));

				// Get user credentials
				System.out.println("Enter Employee No");
				userNO = getIt.readLine();
				System.out.println("Enter Password");
				userPWD = getIt.readLine();

				// iterate through stored credentials to the end or a match is found
				while (userNO != (credNO) && userPWD != (credPWD) && credPWD != null) {

					// store credentials as variables
					credNO = br.readLine();
					credPWD = br.readLine();

					// evaluate user credentials against stored credentials
					if (userNO.equals(credNO) && userPWD.equals(credPWD)) {
						System.out.println("Logged In!");
						
						System.out.println();

						// make user prefix case insensitive
						selection = Character.toUpperCase(userNO.charAt(0));

						// Staff or Tech menu switch statement
						switch (Character.toUpperCase(userNO.charAt(0))) {

						// staff menu case
						case 'S':
							do {
								// set menu selections
								menu = Arrays.asList("Create Ticket", "Exit Program");
								menuSelections = Arrays.asList("C", "X");

								// menu title
								printMenu("STAFF MENU", menu, menuSelections);
								userInput = sc.nextLine();
								System.out.println();

								// validate menu selection input length
								if (userInput.length() != 1) {
									System.out.println("Error - invalid selection!");
								} else {

									// make selection case insensitive
									selection = Character.toUpperCase(userInput.charAt(0));

									// process user menu selection
									switch (Character.toUpperCase(userInput.charAt(0))) {

									// create ticket case
									case 'C':
										CreateTicket ticket = TicketMenu(sc);
										if (ticket != null) {
											tickets.add(ticket);
										}
										break;

									case 'P':
										for (int i = 0; i < tickets.size(); i++) {
											CreateTicket tmp = tickets.get(i);
											System.out.printf("Ticket ID: %s\n" + "Name: %s %s\n" + "Staff Number: %s\n"
													+ "Email: %s\n" + "Contact Number: %s\n" + "Issue description: %s\n"
													+ "Ticket Status: %s\n" + "Assigned Technician: %s %s\n"
													+ "Ticket Severity: %s\n\n", tmp.getTicketId(),
													tmp.getTicketCreatorFirstName(), tmp.getTicketCreatorLastName(),
													tmp.getTicketCreatorStaffNumber(), tmp.getTicketCreatorEmail(),
													tmp.getTicketCreatorContactNumber(), tmp.getDescriptionIssue(),
													tmp.getTicketStatus(),
													tmp.getTicketTechnicianFirstName() != null
															? tmp.getTicketTechnicianFirstName()
															: "N/A",
													tmp.getTicketTechnicianLastName() != null
															? tmp.getTicketTechnicianLastName()
															: "",
													tmp.getTicketSeverity().name());
										}
										break;

									// exit case
									case 'X':
										System.out.println("Exiting the program...");
										// Writes out any created tickets to file
										writeTicketData("ITHelpDeskTickets.txt", tickets);
										exit = true;
										break;

									// invalid selection case
									default:
										System.out.println("Error - invalid selection!");
									}
								}

								System.out.println();

							} while (selection != 'X');
							sc.close();
							break;

						// tech menu case
						case 'T':
							do {
								// set menu selections
								menu = Arrays.asList("Close Ticket", "Change Ticket Status" ,"Exit Program");
								menuSelections = Arrays.asList("C", "S" , "X");
								
								displayAllocatedTickets();
								
								// menu title
								printMenu("Tech Menu", menu, menuSelections);
								userInput = sc.nextLine();

								System.out.println();

								// validate selection input length
								if (userInput.length() != 1) {
									System.out.println("Error - invalid selection!");
								} else {

									// make selection case insensitive
									selection = Character.toUpperCase(userInput.charAt(0));

									// process user menu selection
									switch (Character.toUpperCase(userInput.charAt(0))) {

									// view open ticket case
									case 'C':
										System.out.println("Enter the ticket # you wish to close");
										break;

										// view open ticket case
									case 'S':
										System.out.println("Enter the ticket # you wish to change");
										break;
										
									// exit case
									case 'X':
										System.out.println("Exiting the program...");
										exit = true;
										break;
									// invalid selection case
									default:
										System.out.println("Error - invalid selection!");
									}
								}

								System.out.println();

							} while (selection != 'X');
							sc.close();
							break;
						}

					}

					else if (userNO != (credNO) && userPWD != (credPWD) && credPWD == null && exit == false) {
						System.out.println("Invalid Credentials");

					}
				}

				// Close the input stream
				in.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}
	}
	
    protected static void displayAllocatedTickets() {
    	

		
    	
        try {
            // resets reference in the array
            int ticketArrayCount = 0;
            int arrSize = tickets.size();
            // iterates through array
            while (arrSize > ticketArrayCount)
            {
                // displays details for each stored object
            	System.out.println("Allocated tickets");
                System.out.print("ID: " + tickets.get(ticketArrayCount).getTicketId() + "| Status:" + tickets.get(ticketArrayCount).getTicketStatus() + "| Severity:" + tickets.get(ticketArrayCount).getTicketSeverity());
                System.out.println();

                ticketArrayCount++;
            }
        } catch (Exception e) {// Catch exception if any
            
        
            System.err.println("Error: " + e.getMessage());
        }

        
}

	// print menu method
	protected static void printMenu(String title, List<String> menu, List<String> menuSelections) {
		System.out.printf("%s\n---------------------------\n\n", title);
		for (int i = 0; i < menu.size(); i++) {
			System.out.printf("%-25s%s\n", menu.get(i), menuSelections != null ? menuSelections.get(i) : i + 1);
		}
		System.out.println();
		System.out.println("Enter selection: ");
	}

	protected static CreateTicket.TicketSeverity checkTicketSeverity(String input) {
		if (input.equals(CreateTicket.TicketSeverity.HIGH.name())) {
			return CreateTicket.TicketSeverity.HIGH;
		}
		if (input.equals(CreateTicket.TicketSeverity.MEDIUM.name())) {
			return CreateTicket.TicketSeverity.MEDIUM;
		}
		if (input.equals(CreateTicket.TicketSeverity.LOW.name())) {
			return CreateTicket.TicketSeverity.LOW;
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
			System.out.println("Error - invalid phone number, must use Australian format!");
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

	/*
	 * Generate a ticket identification number Ticket ID Format:
	 * yyyyMMdd-ticketIDCounter
	 */
	protected static String generateTicketId() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedDate = date.format(dateFormat);
		return formattedDate + "-" + CreateTicket.ticketIDCounter;
	}

	protected static CreateTicket TicketMenu(Scanner scanner) {
		String input = null, exit = "\0";
		Boolean cancel = false;
		String[] ticket = new String[8];
		CreateTicket.TicketSeverity severity = null;

		List<String> menu = Arrays.asList("Surname", "Given name", "Staff number", "Email", "Contact number",
				"Description", "Severity", "Confirm", "Exit");
		List<String> menuSelections = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "C", "X");

		do {
			printMenu("CREATE TICKET MENU", menu, menuSelections);
			input = scanner.nextLine();
			if (input.length() >= 1) {
				switch (Character.toUpperCase(input.charAt(0))) {
				case '1':
					ticket[1] = getInput(scanner, menu.get(0));
					break;
				case '2':
					ticket[2] = getInput(scanner, menu.get(1));
					break;
				case '3':
					ticket[3] = getInput(scanner, menu.get(2));
					break;
				case '4':
					ticket[4] = getInput(scanner, menu.get(3));
					break;
				case '5':
					ticket[5] = getInput(scanner, menu.get(4));
					break;
				case '6':
					ticket[6] = getInput(scanner, menu.get(5));
					break;
				case '7':
					ticket[7] = getInput(scanner, menu.get(6));
					severity = checkTicketSeverity(ticket[7]);
					break;

				case 'C':
					ticket[0] = generateTicketId();
					boolean missingField = false;
					for (int i = 1; i < ticket.length; i++) {
						if (ticket[i] == null) {
							System.out.printf("Error - missing field: %s\n", menu.get(i - 1));
							missingField = true;
						}
					}
					if (!missingField) {
						return new CreateTicket(ticket[0], ticket[1], ticket[2], ticket[3], ticket[4], ticket[5],
								ticket[6], severity);
					}
					System.out.println("Error - all fields are required!");
					break;

				case 'X':
					System.out.println("Exiting will cause any progress to be lost");
					System.out.println("Are you sure you want to exit? Y/N");
					exit = scanner.nextLine();
					do {
						if (exit.length() == 1) {
							switch (Character.toUpperCase(exit.charAt(0))) {
							case 'N':
								System.out.println("Returning to create ticket menu...");
								cancel = true;
								break;
							case 'Y':
								System.out.println("Returning to staff menu...");
								cancel = true;
								break;
							default:
								System.out.println("Error - invalid selection, must be Y or N");
								exit = scanner.nextLine();
							}
						} else {
							System.out.println("Error - invalid selection, must be Y or N");
							exit = scanner.nextLine();
						}

					} while (!cancel);
					cancel = false;
					break;

				default:
					System.out.println("Error - invalid selection!");
				}
			} else {
				System.out.println("Error - invalid selection!");
			}
		} while (Character.toUpperCase(exit.charAt(0)) != 'Y');
		return null;
	}

	// Method or loading saved tickets

	public static void loadTicketData(String filename) {
		// tag used to identify object type being read into system from file
		String ticketTag;
		CreateTicket temp;
		try {
			Scanner fileScanner = new Scanner(new FileReader(filename));

			while (fileScanner.hasNextLine()) {
				ticketTag = fileScanner.nextLine();

				temp = null;
				// identifies tickets object type and reads in as Ticket object
				if (ticketTag.equals("CreateTicket")) {
					temp = new CreateTicket(fileScanner);
				}
				if (temp == null) {
					System.out.println("File tag " + ticketTag + " is invalid.");
				} else {
					tickets.add(temp);
					// Set ticket count to number of tickets in arraylist
					CreateTicket.ticketIDCounter = Login_Menus.tickets.size() + 1;
				}
			}
			fileScanner.close();
		} catch (FileNotFoundException FNFe) {
			System.out.println("*** File error - \"ITHelpDeskTickets.txt\" file not found. ***" + "\n"
					+ "*** Starting System without IT Ticket data.*** \n");
		}
	}

	// Method for saving created tickets for persistence
	// helper method for writing all Ticket data out to file
	public static void writeTicketData(String filename, ArrayList<CreateTicket> tickets) {
		try {
			PrintWriter pw = new PrintWriter(filename);
			for (int ticketCount = 0; ticketCount < tickets.size(); ticketCount++) {
				tickets.get(ticketCount).writeDetails(pw);
			}
			pw.close();
		} catch (IOException IOEx) {
			System.out.println("File error file could not be opened.");
		}
	}
}
