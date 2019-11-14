package HelpDeskTicketSystemTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.TechUser;
import HelpDeskTicketSystem.Ticket;

class TechUserTest {
	
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private final PrintStream ogOut = System.out;
	static TechUser tmp;

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void restoreStreams() {
		System.setOut(ogOut);
	}
		
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tmp = new TechUser("1", "asdf", "Foo", "Bar", 1, 0, 0);
	}

	@Test
	void testScannerConstructor() {
		String i = "2\n"
				+  "fdsa\n"
				+  "Bar\n"
				+  "Foo\n"
				+  "1\n"
				+  "0\n"
				+  "0\n";
		TechUser newUser = new TechUser(new Scanner(i));
		assertEquals("2", newUser.getId());
	}
	
	@Test
	void testGetActiveCount() {
		assertEquals(1, tmp.getActiveCount());
	}
	
	@Test
	void testGetInActiveCount() {
		assertEquals(0, tmp.getInActiveCount());
	}

	@Test
	void testGetLevel() {
		assertEquals(1, tmp.getLevel());
	}

	@Test
	void testSetActiveCount() {
		tmp.setActiveCount(1);
		assertEquals(1, tmp.getActiveCount());
	}

	@Test
	void testSetInactiveCount() {
		tmp.setInActiveCount(1);
		assertEquals(1, tmp.getInActiveCount());
	}
	@Test
	void testSetLevel() {
		tmp.setLevel(2);
		assertEquals(2, tmp.getLevel());
	}

	@Test
	void testGetId() {
		assertEquals("1", tmp.getId());
	}

	@Test
	void testGetPwd() {
		assertEquals("asdf", tmp.getPwd());
	}

	@Test
	void testGetFirstName() {
		assertEquals("Foo", tmp.getFirstName());
	}

	@Test
	void testGetLastName() {
		assertEquals("Bar", tmp.getLastName());
	}

	@Test
	void testSetPwd() {
		tmp.setPwd("fdsa");
		assertEquals("fdsa", tmp.getPwd());
	}

	@Test
	void testPrintEmptyTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tmp.printActiveTickets(tickets);
		tmp.printInActiveTickets(tickets);
		assertEquals(0, tickets.size());
	}
	
	@Test
	void testPrintTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		tickets.add(new Ticket(
				"1", "Obi-wan", "Kenobi", 
				"3", "general.kenobi@gmail.com", "0412312312",
				"Clones attacking jedi", Ticket.TicketSeverity.HIGH, "1"));
		tmp.printActiveTickets(tickets);
		tmp.printInActiveTickets(tickets);
		assertEquals(1, tickets.size());
	}
	
	@Test
	void testWriteAttributes() {
		PrintWriter pw = new PrintWriter(out);
		tmp.writeAttributes(pw);
		pw.close();
		assertFalse(pw.checkError());
	}
}
