package HelpDeskTicketSystemTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.Ticket;
import HelpDeskTicketSystem.Menus;

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
		String i = "X!\n"
				+ "Foo\n"
				+ "N\n"
				+ "Foo\n"
				+ "Bar\n"
				+ "super1000\n"
				+ "foo@bar.com\n"
				+ "02 6310 1010\n"
				+ "leaking all the memory\n"
				+ "HIGH\n"
				+ "p\n" 
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
				+ "X\n"
				+ "N\n"
				+ "C\n";
		String ticketId = generateTicketId();
		Ticket ticket = createTicketMenu(new Scanner(i));
		assertEquals(ticketId + "FooBarsuper1000foo@bar.com02 6310 1010leaking all the memoryHIGH", 
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
				+ "X!\n"
				+ "p\n"
				+ "Y\n";
		Ticket ticket = createTicketMenu(new Scanner(i));
		assertNull(ticket);
	}
	
	@Test
	void testTicketMenuCompleteExit() {
		String i = "X!\n"
				+ "Foo\n"
				+ "N\n"
				+ "Foo\n"
				+ "Bar\n"
				+ "super1000\n"
				+ "foo@bar.com\n"
				+ "02 6310 1010\n"
				+ "leaking all the memory\n"
				+ "HIGH\n"
				+ "p\n"
				+ "ppppppppp\n"
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
				+ "X\n"
				+ "N\n"
				+ "X\n"
				+ "Y\n";
		Ticket ticket = createTicketMenu(new Scanner(i));
		assertNull(ticket);
	}
	
	@Test
	void testDisplayOpenTickets() {
		displayOpenTickets();
		assertEquals("", out.toString());
	}
}
