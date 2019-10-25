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
    private static final Scanner sc = new Scanner(System.in);

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
    /*
     * Recursive input retrieval and validation
     */
    private static String getInput(String request)
    {
    	String input = "\0";
    	// Regex that matches email addresses
    	String emailPattern = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";
    	// Regex that matches Australian formatted phone numbers
    	String phonePattern = "^(?:\\+?(61))? ?(?:\\((?=.*\\)))?(0?[2-57-8])\\)? ?(\\d\\d(?:[- ](?=\\d{3})|(?!\\d\\d[- ]?\\d[- ]))\\d\\d[- ]?\\d[- ]?\\d{3})$";
    	
    	// request input
    	System.out.printf("Enter your %s: ", request);
    	input = sc.nextLine();
    	
    	// if empty
    	if ("".equals(input))
    	{
    		System.out.println("Error - Input can not be empty!");
    		input = getInput(request);
    	}
    	if ("X".equals(input))
    	{
    		System.out.println("Are you sure you want to cancel this ticket? Y/N");
    		input = sc.nextLine();
    		do {
    			System.out.println("Error - Invalid input, must be Y or N");
    			input = sc.nextLine();
    		} while(!input.toUpperCase().equals("Y") || !input.toUpperCase().equals("N"));
    		if (input.equals("Y"))
    		{
    			return "\0";
    		}
    		input = getInput(request);
    		
    	}
    	// if invalid email
    	if (request.equals("email") && !input.matches(emailPattern))
    	{
    		System.out.println("Error - Invalid email address!");
    		input = getInput(request);
    	}
    	// if invalid phone number
    	if (request.equals("contact number") && !input.matches(phonePattern))
		{
			System.out.println("Error - Invalid phone number, must use Australian format!");
			input = getInput(request);
		}
    	// if valid ticket severity
    	if (request.equals("issue severity"))
    	{
    		if (checkTicketSeverity(input.toUpperCase())==null)
    		{
    			System.out.println("Error - Invalid severity, must be LOW, MEDIUM, OR HIGH!");
    			input = getInput(request);
    		}
    		input = input.toUpperCase();
    	}
    	return input;
    }

    /*
     * Generate a ticket identification number
     * Ticket ID Format: yyyyMMdd-ticketIDCounter
     */
    private static String generateTicketId()
    {
    	LocalDateTime date = LocalDateTime.now();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String formattedDate = date.format(dateFormat);
    	return formattedDate + "-" + CreateTicket.ticketIDCounter;
    }
    
    /*
     * Ticket factory method
     */
    protected static CreateTicket createTicket()
    {
    	String creatorFirstName = getInput("first name");
    	String creatorLastName = getInput("last name");
    	String creatorStaffNumber = getInput("staff number");
    	String creatorEmail = getInput("email");
    	String creatorContactNumber = getInput("contact number");
    	String descriptionIssue = getInput("description of the issue");
    	String strTicketSeverity = getInput("issue severity");
    	CreateTicket.TicketSeverity ticketSeverity = checkTicketSeverity(strTicketSeverity);
    	String ticketId = generateTicketId();
    	return new CreateTicket(ticketId, creatorFirstName, creatorLastName, creatorStaffNumber, creatorEmail, creatorContactNumber, descriptionIssue, ticketSeverity);
    }
    

    public static void main(String[] args)
    {
        String userInput;
        Scanner sc = new Scanner(System.in);
        // Initialize selection variable to ASCII null to keep compiler happy
        char selection = '\0';
        
        // Check to see is persistent data for tickets and load if present
        loadTicketData();
        
        do
        {

            // print menu to screen
            System.out.println("STAFF MENU");
            System.out.println("---------------------------");
            System.out.println();

            System.out.printf("%-25s%s\n", "Create Ticket", "C");
            System.out.printf("%-25s%s\n", "Exit Program", "X");
            System.out.println();

            // prompt user to enter selection
            System.out.print("Enter selection: ");
            userInput = sc.nextLine();

            System.out.println();

            // validate selection input length
            if (userInput.length() != 1)
            {
                System.out.println("Error - invalid selection!");
            } else
            {
                // make selection "case insensitive"
                selection = Character.toUpperCase(userInput.charAt(0));

                // process user's selection
                switch (selection)
                {

                case 'C':
                    // product range search();
                	CreateTicket ticket = createTicket();
                	tickets.add(ticket);
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
                    			tmp.getTicketTechnicianFirstName(), 
                    			tmp.getTicketTechnicianLastName(),
                    			tmp.getTicketSeverity().name());
                	}
                case 'X':
                    System.out.println("Exiting the program...");
                    // Writes out any created tickets to file
                    writeTicketData();
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
    
    public static void loadTicketData()
    {
       // tag used to identify object type being read into system from file
       String ticketTag;
       CreateTicket temp;
       try
       {
          Scanner fileScanner =
                   new Scanner(new FileReader("ITHelpDeskTickets.txt"));

          while (fileScanner.hasNextLine())
          {
        	  ticketTag = fileScanner.nextLine();

             temp = null;
             // identifies tickets object type and reads in as Ticket object
             if (ticketTag.equals("CreateTicket"))
             {
                temp = new CreateTicket(fileScanner);
             }
             if (temp == null)
             {
                System.out.println("File tag " + ticketTag + " is invalid.");
             }
             else
             {
//            	 StaffMenu.tickets.size()
                tickets.add(temp);
                // ticketIDCounter++;
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
    public static void writeTicketData()
    {
       try
       {
          PrintWriter pw = new PrintWriter("ITHelpDeskTickets.txt");
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
