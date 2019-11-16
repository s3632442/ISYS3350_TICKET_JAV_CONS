package HelpDeskTicketSystemTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.Ticket;
import HelpDeskTicketSystem.User;
import HelpDeskTicketSystem.Menus;
import HelpDeskTicketSystem.TechUser;

class MenusTest extends Menus {

	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private final PrintStream ogOut = System.out;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Ticket.ticketIDCounter = 0;
	}
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void restoreStreams() {
		System.setOut(ogOut);
	}
	
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
		String input = getInput(new Scanner(i), "given name");
		assertEquals("foobar", input);
	}
	@Test
	public void testGetInputBadEmail()
	{
		String i = "foobar\n"
				+ "foo@bar.com\n";
		String input = getInput(new Scanner(i), "email address");
		assertEquals("foo@bar.com", input);
	}
	@Test
	public void testGetInputBadPhone()
	{
		String i = "101010101010101\n"
				+ "04 1111 1111\n";
		String input = getInput(new Scanner(i), "contact number");
		assertEquals("04 1111 1111", input);
	}
	@Test
	public void testGetInputBadSeverity()
	{
		String i = "lo\n"
				+ "high";
		String input = getInput(new Scanner(i), "issue severity");
		assertEquals("HIGH", input);
	}
	

	// Test printing menu with menu selectors
	@Test
	void testPrintMenuNoSelection() {
		Menus.printMenu("UTAPAU", Arrays.asList("HELLO THERE!", "GENERAL KENOBI."), null);
		assertEquals("", out.toString());
	}
	// Test printing menu with menu selectors
	@Test
	void testPrintMenuWithSelection() {
		Menus.printMenu("HAVE YOU HEARD THE STORY OF DARTH PLAGUEIS THE WISE?", Arrays.asList("Yes", "No"), Arrays.asList("Y", "N"));
		assertEquals("", out.toString());
	}
	// Test all TicketSeverity paths
	@Test
	void testCheckTicketSeverity() {
		String low = Ticket.TicketSeverity.LOW.name();
		String medium = Ticket.TicketSeverity.MEDIUM.name();
		String high = Ticket.TicketSeverity.HIGH.name();
		
		Ticket.TicketSeverity severity = checkTicketSeverity("");
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
    	assertEquals(formattedDate +"-0", generateTicketId());
	}

	// Test all ticket paths
	@Test
	void testTicketMenu() {
		String i = "!X\n"
				+ "Foo\n"
				+ "N\n"
				+ "Foo\n"
				+ "Bar\n"
				+ "foo@bar.com\n"
				+ "02 6310 1010\n"
				+ "leaking all the memory\n"
				+ "LOW\n"
				+ "n\n"
				+ "ppppppppp\n"
				+ "1\n"
				+ "Bar\n"
				+ "2\n"
				+ "Foo\n"
				+ "3\n"
				+ "bar@foo.com\n"
				+ "4\n"
				+ "02 6399 9999\n"
				+ "5\n"
				+ "stepped on lego brick\n"
				+ "6\n"
				+ "HIGH\n"
				+ "c\n"
				+ "y\n";
		String ticketId = generateTicketId();
		Ticket ticket = createTicketMenu(new Scanner(i));
		assertEquals(ticketId + "FooBarN\\Abar@foo.com02 6399 9999stepped on lego brickHIGH", 
				ticket.getId()
				+ ticket.getCreatorLastName()
				+ ticket.getCreatorFirstName()
				+ ticket.getCreatorStaffNumber()
				+ ticket.getCreatorEmail()
				+ ticket.getCreatorContactNumber()
				+ ticket.getDescription()
				+ ticket.getSeverity());
	}
	// Test ticket menu exit paths
	@Test
	void testTicketMenuCleanExit() {
		/*String i = "\n"
				+ "X!\n"
				+ "x\n"
				+ "xxxxxx\n"
				+ "N\n"
				+ "X\n"
				+ "Y\n";*/
		String i = "\n"
				+ "X\n"
				+ "!X\n"
				+ "p\n"
				+ "Y\n";
		Ticket ticket = createTicketMenu(new Scanner(i));
		assertNull(ticket);
	}
	
	@Test
	void testTicketMenuCompleteExit() {
		String i = "!X\n"
				+ "Foo\n"
				+ "N\n"
				+ "Foo\n"
				+ "Bar\n"
				+ "foo@bar.com\n"
				+ "02 6310 1010\n"
				+ "leaking all the memory\n"
				+ "LOW\n"
				+ "n\n"
				+ "ppppppppp\n"
				+ "1\n"
				+ "Bar\n"
				+ "2\n"
				+ "Foo\n"
				+ "3\n"
				+ "bar@foo.com\n"
				+ "4\n"
				+ "02 6399 9999\n"
				+ "5\n"
				+ "stepped on lego brick\n"
				+ "6\n"
				+ "HIGH\n"
				+ "c\n"
				+ "n\n";
		Ticket ticket = createTicketMenu(new Scanner(i));
		assertNull(ticket);
	}
	
	@Test
	void testDisplayOpenTickets() {
		assertEquals("", out.toString());
	}
	
	// Test closer of ticket greater than seven days old
	@Test
	void testAutoCloseTicketMoreThanSevenDaysOld() {
		// Create open ticket
		TechUser tech = new TechUser("1", "asdf", "Bar", "Foo", 2, 0, 0);
		ArrayList<User> users = new ArrayList<User>();
		users.add(tech);
		Ticket oldTicket = new Ticket("20191101-1","Foo", "Bar", "2", "Bar@foo.com",
				"0412341234", "Lab vm CPU died.", Ticket.TicketSeverity.HIGH, "1");
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(oldTicket);
		// Check if ticket is open
		assertEquals("Open", oldTicket.getStringStatus());
		closeActiveTicketsMoreThanSevenDaysOld(tickets, users);
		// Check if ticket has been closed due to older than seven days
		assertEquals("Closed", oldTicket.getStringStatus());
	}
	
	@Test
	void testEnterToContinue() {
		String i = "\n";
		enterToContinue(new Scanner(i));
		assertEquals("", out.toString());
	}
	
	@Test
	void testGetInputExitResumeExit() {
		String i = "y\n";
		String input = getInput(new Scanner(i), "exit");
		assertEquals("EXIT_RESUME", input);
	}
	
	@Test
	void testGetInputEarlyExitSelection() {
		String i = "!X\n";
		String input = getInput(new Scanner(i));
		assertEquals("!X", input);
	}
	
}
