package HelpDeskTicketSystem;

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StaffMenu {
	 // you can refer to the array and Scanner object here anywhere
    // within this class, even within helper methods if you choose to
    // implement them
    
	private static ArrayList<CreateTicket> tickets = new ArrayList<CreateTicket>();
	
	protected static void printMenu(String title, List<String> menu, List<String> menuSelections)
	{
		System.out.printf("%s\n---------------------------\n\n", title);
		for (int i = 0; i < menu.size(); i++)
		{
			System.out.printf("%-25s%s\n", menu.get(i), menuSelections!=null ? menuSelections.get(i) : i+1);
		}
		System.out.println();
		System.out.println("Enter selection: ");
	}

    protected static CreateTicket.TicketSeverity checkTicketSeverity(String input)
    {
    	if (input.equals(CreateTicket.TicketSeverity.HIGH.name()))
    	{
    		return CreateTicket.TicketSeverity.HIGH;
    	}
    	if (input.equals(CreateTicket.TicketSeverity.MEDIUM.name()))
    	{
    		return CreateTicket.TicketSeverity.MEDIUM;
    	}
    	if (input.equals(CreateTicket.TicketSeverity.LOW.name()))
    	{
    		return CreateTicket.TicketSeverity.LOW;
    	}
    	return null;
    }

    protected static String getInput(Scanner scanner, String request)
    {
    	String input = "\0";
    	// Regex that matches email addresses
    	String emailPattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
    	// Regex that matches Australian formatted phone numbers
    	String phonePattern = "^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$";
    	
    	// request input
    	System.out.printf("Enter your %s: ", request);
    	input = scanner.nextLine();
    	
    	// if empty
    	if ("".equals(input))
    	{
    		System.out.println("Error - input can not be empty!");
    		input = getInput(scanner, request);
    	}
    	// if invalid email
    	if (request.equalsIgnoreCase("email") && !input.matches(emailPattern))
    	{
    		System.out.println("Error - invalid email address!");
    		input = getInput(scanner, request);
    	}
    	// if invalid phone number
    	if (request.equalsIgnoreCase("contact number") && !input.matches(phonePattern))
		{
			System.out.println("Error - invalid phone number, must use Australian format!");
			input = getInput(scanner, request);
		}
    	// if valid ticket severity
    	if (request.equalsIgnoreCase("severity"))
    	{
    		if (checkTicketSeverity(input.toUpperCase())==null)
    		{
    			System.out.println("Error - invalid severity, must be LOW, MEDIUM, OR HIGH!");
    			input = getInput(scanner, request);
    		}
    		input = input.toUpperCase();
    	}
    	return input;
    }

    /*
     * Generate a ticket identification number
     * Ticket ID Format: yyyyMMdd-ticketIDCounter
     */
    protected static String generateTicketId()
    {
    	LocalDateTime date = LocalDateTime.now();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String formattedDate = date.format(dateFormat);
    	return formattedDate + "-" + CreateTicket.ticketIDCounter;
    }


    protected static CreateTicket TicketMenu(Scanner scanner)
    {
		String input = null, exit = "\0";
		Boolean cancel = false;
		String[] ticket = new String[8];
    	CreateTicket.TicketSeverity severity = null;
		
		List<String> menu = Arrays.asList("Surname", "Given name", "Staff number", "Email", "Contact number", "Description", "Severity", "Confirm", "Exit");
    	List<String> menuSelections = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "C", "X");
		
		do {
    		printMenu("CREATE TICKET MENU", menu, menuSelections);
			input = scanner.nextLine();
			if (input.length() >= 1)
			{
				switch(Character.toUpperCase(input.charAt(0)))
				{
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
					for (int i = 1; i < ticket.length; i++)
					{
						if (ticket[i] == null)
						{
							System.out.printf("Error - missing field: %s\n", menu.get(i-1));
							missingField = true;
						}
					}
					if (!missingField)
					{
						return new CreateTicket(ticket[0], ticket[1], ticket[2], ticket[3], ticket[4], ticket[5], ticket[6], severity);
					}
					System.out.println("Error - all fields are required!");
					break;
				
				case 'X':
					System.out.println("Exiting will cause any progress to be lost");
					System.out.println("Are you sure you want to exit? Y/N");
					exit = scanner.nextLine();
					System.out.println(exit.length());
					do {
						if (exit.length() == 1)
						{
							switch(Character.toUpperCase(exit.charAt(0)))
							{
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
		} while(Character.toUpperCase(exit.charAt(0)) != 'Y');
		return null;
    }
    
    public static void main(String[] args)
    {
        String userInput;
        Scanner sc = new Scanner(System.in);
        // Initialize selection variable to ASCII null to keep compiler happy
        char selection = '\0';
        List<String> menu = Arrays.asList("Create Ticket", "Exit Program");
        List <String> menuSelections = Arrays.asList("C", "X");
        
        // Check to see is persistent data for tickets and load if present
        loadTicketData("ITHelpDeskTickets.txt");
        
        do
        {
        	printMenu("STAFF MENU", menu, menuSelections);
            userInput = sc.nextLine();

            System.out.println();

            // validate selection input length
            if (userInput.length() != 1)
            {
                System.out.println("Error - invalid selection!");
            } else
            {
                // make selection "case insensitive"
                //
            	selection = Character.toUpperCase(userInput.charAt(0));

                // process user's selection
                switch (Character.toUpperCase(userInput.charAt(0)))
                {

                case 'C':
                    // product range search();
					CreateTicket ticket = TicketMenu(sc);
					if (ticket != null)
					{
						tickets.add(ticket);
					}       	
                    break;

                case 'P':
                	for (int i = 0; i<tickets.size(); i++)
                	{
                		CreateTicket tmp = tickets.get(i);
                    	System.out.printf("Ticket ID: %s\n"
                    			+ "Name: %s %s\n"
                    			+ "Staff Number: %s\n"
                    			+ "Email: %s\n"
                    			+ "Contact Number: %s\n"
                    			+ "Issue description: %s\n"
                    			+ "Ticket Status: %s\n"
                    			+ "Assigned Technician: %s %s\n"
                    			+ "Ticket Severity: %s\n\n", 
                    			tmp.getTicketId(), 
                    			tmp.getTicketCreatorFirstName(), 
                    			tmp.getTicketCreatorLastName(), 
                    			tmp.getTicketCreatorStaffNumber(), 
                    			tmp.getTicketCreatorEmail(), 
                    			tmp.getTicketCreatorContactNumber(), 
                    			tmp.getDescriptionIssue(), 
                    			tmp.getTicketStatus(), 
                    			tmp.getTicketTechnicianFirstName() != null ? tmp.getTicketTechnicianFirstName() : "N/A", 
                    			tmp.getTicketTechnicianLastName() != null ? tmp.getTicketTechnicianLastName() : "",
                    			tmp.getTicketSeverity().name());
                	}
                	break;
                	
                case 'X':
                    System.out.println("Exiting the program...");
                    // Writes out any created tickets to file
                    writeTicketData("ITHelpDeskTickets.txt", tickets);
                    break;

                default:
                    System.out.println("Error - invalid selection!");
                }
            }

            System.out.println();

        } while (selection != 'X');
        sc.close();
    }
    
    // Method or loading saved tickets
    
    public static void loadTicketData(String filename)
    {
       // tag used to identify object type being read into system from file
       String ticketTag;
       CreateTicket temp;
       try
       {
          Scanner fileScanner =
                   new Scanner(new FileReader(filename));

          while (fileScanner.hasNextLine())
          {
        	  ticketTag = fileScanner.nextLine();

             temp = null;
             // identifies tickets object type and reads in as Ticket object
             if (ticketTag.equals("CreateTicket")){
                temp = new CreateTicket(fileScanner);
             }
             if (temp == null){
                System.out.println("File tag " + ticketTag + " is invalid.");
             }
             else{
                tickets.add(temp);
		// Set ticket count to number of tickets in arraylist
                CreateTicket.ticketIDCounter = StaffMenu.tickets.size() + 1;
             }
          }
          fileScanner.close();
       }
       catch (FileNotFoundException FNFe)
       {
          System.out.println("*** File error - \"ITHelpDeskTickets.txt\" file not found. ***" +
                             "\n" + "*** Starting System without IT Ticket data.*** \n");
       }
    }
    // Method for saving created tickets for persistence
 // helper method for writing all Ticket data out to file
    public static void writeTicketData(String filename, ArrayList<CreateTicket> tickets)
    {
       try
       {
          PrintWriter pw = new PrintWriter(filename);
          for (int ticketCount = 0; ticketCount < tickets.size(); ticketCount++)
          {
             tickets.get(ticketCount).writeDetails(pw);
          }
          pw.close();
       }
       catch (IOException IOEx)
       {
          System.out.println("File error file could not be opened.");
       }
    }
}
