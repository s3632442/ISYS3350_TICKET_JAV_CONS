package HelpDeskTicketSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 

class StaffMenuTest extends StaffMenu {

//	Scanner sc = new Scanner(System.in);
//	String input = sc.nextLine();
	HelpDeskTicketSystem.CreateTicket ticket = createTicket();
	
	//TODO Test to check Get Input functionality via reading in text file
//	@Test
//	void testGetInput() {
//		HelpDeskTicketSystem.CreateTicket ticket = createTicket();
//		System.out.println("\n");
//		System.out.println("b");
//	}
////		ByteArrayOutputStream stream = new ByteArrayOutputStream();
////		PrintStream ps = new PrintStream(stream);
//		PrintStream consoleOutput = System.out;
////		System.out.println(consoleOutput);
//		assertEquals(consoleOutput.toString(), getInput(input));
//	}
	// Test to check Ticket Id generation
	@Test
	void testGenerateTicketId() {
		LocalDateTime date = LocalDateTime.now();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String formattedDate = date.format(dateFormat);
    	assertEquals(formattedDate +"-0", ticket.getTicketId());
	}

}
