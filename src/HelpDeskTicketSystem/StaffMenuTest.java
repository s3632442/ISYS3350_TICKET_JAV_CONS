package HelpDeskTicketSystem;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 

class StaffMenuTest extends Login_Menus {
	
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private final PrintStream ogOut = System.out;
	
	// Setup output
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void restoreStreams() {
		System.setOut(ogOut);
	}
	
//	@Test
//	public void shouldProcessUserInput() {
//		String i = "11\n"
//				+ "10\n";
//		assertEquals(10, processUserInput(new Scanner(i)));
//	}
	/*
	 * getInput Testing
	 * - empty input
	 * - name
	 * - email
	 * - wrong phone number format
	 * - correct phone number
	 * - wrong ticket severity
	 * - correct ticket severity
	 */
	@Test
	public void testGetInputBadString()
	{
		String i = "\n"
				+ "foobar\n";
		String input = getInput(new Scanner(i), "Given name");
		assertEquals("foobar", input);
	}
	@Test
	public void testGetInputBadEmail()
	{
		String i = "foobar\n"
				+ "foo@bar.com\n";
		String input = getInput(new Scanner(i), "Email");
		assertEquals("foo@bar.com", input);
	}
	@Test
	public void testGetInputBadPhone()
	{
		String i = "101010101010101\n"
				+ "04 1111 1111\n";
		String input = getInput(new Scanner(i), "Contact number");
		assertEquals("04 1111 1111", input);
	}
	@Test
	public void testGetInputBadSeverity()
	{
		String i = "lo\n"
				+ "high";
		String input = getInput(new Scanner(i), "Severity");
		assertEquals("HIGH", input);
	}
	
	// Test printing menu with menu selectors
	@Test
	void testPrintMenuNoSelection() {
		Login_Menus.printMenu("UTAPAU", Arrays.asList("HELLO THERE!", "GENERAL KENOBI."), null);
		assertEquals("", out.toString());
	}
	// Test printing menu with menu selectors
	@Test
	void testPrintMenuWithSelection() {
		Login_Menus.printMenu("HAVE YOU HEARD THE STORY OF DARTH PLAGUEIS THE WISE?", Arrays.asList("Yes", "No"), Arrays.asList("Y", "N"));
		assertEquals("", out.toString());
	}
	// Test all TicketSeverity paths
	@Test
	void testCheckTicketSeverity() {
		String low = CreateTicket.TicketSeverity.LOW.name();
		String medium = CreateTicket.TicketSeverity.MEDIUM.name();
		String high = CreateTicket.TicketSeverity.HIGH.name();
		
		CreateTicket.TicketSeverity severity = checkTicketSeverity("");
		assertNull(severity);
		
		severity = checkTicketSeverity(low);
		assertEquals("LOW", severity.name());
		
		severity = checkTicketSeverity(medium);
		assertEquals("MEDIUM", severity.name());
		
		severity = checkTicketSeverity(high);
		assertEquals("HIGH", severity.name());
		
	}
	// Test to check Ticket Id generation
	@Test
	void testGenerateTicketId() {
		LocalDateTime date = LocalDateTime.now();
    	DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    	String formattedDate = date.format(dateFormat);
    	assertEquals(formattedDate +"-1", generateTicketId());
	}

	// Test all ticket paths
	@Test
	void testTicketMenu() {
		String i = "p\n" 
				+ "C\n"
				+ "1\n"
				+ "Bar\n"
				+ "2\n"
				+ "Foo\n"
				+ "3\n"
				+ "super1000\n"
				+ "4\n"
				+ "foo@bar.com\n"
				+ "5\n"
				+ "02 6310 1010\n"
				+ "6\n"
				+ "leaking all the memory\n"
				+ "7\n"
				+ "HIGH\n"
				+ "C\n";
		String ticketId = generateTicketId();
		CreateTicket ticket = TicketMenu(new Scanner(i));
		assertEquals(ticketId + "FooBarsuper1000foo@bar.com02 6310 1010leaking all the memoryHIGH", 
				ticket.getTicketId()
				+ ticket.getTicketCreatorLastName()
				+ ticket.getTicketCreatorFirstName()
				+ ticket.getTicketCreatorStaffNumber()
				+ ticket.getTicketCreatorEmail()
				+ ticket.getTicketCreatorContactNumber()
				+ ticket.getDescriptionIssue()
				+ ticket.getTicketSeverity().name());
	}
	// Test ticket menu exit paths
	@Test
	void testTicketMenuExit() {
		String i = "\n"
				+ "X\n"
				+ "x\n"
				+ "xxxxxx\n"
				+ "N\n"
				+ "X\n"
				+ "Y\n";
		CreateTicket ticket = TicketMenu(new Scanner(i));
		assertNull(ticket);
	}
	
	// Test reading from non-existant file
	@Test
	void testLoadTicketData() {
		String filename = "test.txt";
		loadTicketData(filename);
		assertEquals("", out.toString());
	}
	
	// Test writing to file
	@Test
	void testWriteTicketData() {
		String filename = "testfile.txt";
		ArrayList<CreateTicket> tickets = new ArrayList<CreateTicket>();
		tickets.add(new CreateTicket( "1", "TestCreatorFirstName", "TestCreatorLastName",
						"2",  "test@test.com", "0299991111",
						"test description", CreateTicket.TicketSeverity.HIGH));
		tickets.add(new CreateTicket( "1", "TestCreatorFirstName", "TestCreatorLastName",
				"2",  "test@test.com", "0299991111",
				"test description", CreateTicket.TicketSeverity.HIGH));
		
		writeTicketData(filename, tickets);
		loadTicketData(filename);
		assertEquals("", out.toString());
	}
}
