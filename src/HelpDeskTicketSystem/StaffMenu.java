package HelpDeskTicketSystem;

import java.util.Scanner;

public class StaffMenu {
	 // you can refer to the array and Scanner object here anywhere
    // within this class, even within helper methods if you choose to
    // implement them
    
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args)
    {
        String userInput;
        Scanner sc = new Scanner(System.in);
        // Initialize selection variable to ASCII null to keep compiler happy
        char selection = '\0';

       
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
                	System.out.println("Create Ticket - function to be implemented");
                                        break;

                case 'X':
                    System.out.println("Exiting the program...");
                    break;

                default:
                    System.out.println("Error - invalid selection!");
                }
            }

            System.out.println();

        } while (selection != 'X');

    }
}
   