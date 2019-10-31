package HelpDeskTicketSystemTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.Ticket;

class TicketTest {
	
	Ticket ticket = new Ticket( "1", "TestCreatorFirstName", "TestCreatorLastName",
			"2",  "test@test.com", "0299991111",
			"test description", Ticket.TicketSeverity.HIGH);
	
	// Test ticket setter and getter with the value of 2
	@Test
	void testSetTicketId() {
		ticket.setId("2");
		assertEquals("2", ticket.getId());
	}
	// Test ticket counter is 1
	@Test
	void testTicketCounter() {
		assertEquals(2, Ticket.ticketIDCounter);
	}
	// Test ticket setter and getter for creator first name with the value of foo
	@Test
	void testTicketCreatorName() {
		ticket.setCreatorFirstName("foo");
		assertEquals("foo", ticket.getCreatorFirstName());
	}
	// Test ticket setter and getter for creator last name with the value of bar
	@Test
	void testSetTicketCreatorLastName() {
		ticket.setCreatorLastName("bar");
		assertEquals("bar", ticket.getCreatorLastName());
	}
	// Test ticket setter and getter for creator staff number with the value of 2
	@Test
	void testTicketCreatorStaffNumber() {
		ticket.setCreatorStaffNumber("2");
		assertEquals("2", ticket.getCreatorStaffNumber());
	}
	// Test ticket setter and getter for creator email with a value of foo@bar.com
	@Test
	void testTicketCreatorEmail() {
		ticket.setCreatorEmail("foo@bar.com");
		assertEquals("foo@bar.com", ticket.getCreatorEmail());
	}
	// Test ticker setter and getter for creator contact number with a value of 61411119999
	@Test
	void testTicketCreatorContactNumber() {
		ticket.setCreatorContactNumber("61411119999");
		assertEquals("61411119999", ticket.getCreatorContactNumber());
	}
	// Test ticket setter and getter for description issue with a value of foobar
	@Test
	void testTicketDescriptionIssue() {
		ticket.setDescription("foobar");
		assertEquals("foobar", ticket.getDescription());
	}
	// Test ticker setter and getter for ticket severity with a value of LOW
	@Test
	void testTicketSeverity() {
		ticket.setSeverity(Ticket.TicketSeverity.LOW);
		assertEquals("LOW", ticket.getSeverity().name());
	}
	// Test ticket status initial value has a value of Open
	@Test
	void testGetTicketStatus() {
		assertEquals("Open", ticket.getStatus());
	}
	// Test ticket setter and getter for ticket status with a value of false
	@Test
	void testTicketSetStatus() {
		ticket.setStatus(false);
		assertEquals("Closed", ticket.getStatus());
	}
	// Test to check constructor sets Technician first  name to null
	@Test
	void testTicketTechnicianFirstName() {
		assertEquals(null, ticket.getTechnicianFirstName());
	}
	// Test setter and getter for technician first name with a value of bar
	@Test
	void testSetTicketTechnicianFirstName() {
		ticket.setTechnicianFirstName("bar");
		assertEquals("bar", ticket.getTechnicianFirstName());
	}
	// Test to check constructor sets Technician last name to null
	@Test
	void testTicketTechnicianLastName() {
		assertEquals(null, ticket.getTechnicianLastName());
	}
	// Test setter and getter for technician last name with a value of foo
	@Test
	void testSetTicketTechnicianLastName() {
		ticket.setTechnicianLastName("foo");
		assertEquals("foo", ticket.getTechnicianLastName());
	}
	// Test writing details & attributes to file
	@Test
	void testTicketWriteToFile() throws FileNotFoundException {
		String filename = "ticket.txt";
		PrintWriter pw = new PrintWriter(filename);
		ticket.writeDetails(pw);
		pw.close();
		Scanner sc = new Scanner(new FileReader(filename));
		sc.nextLine();
		Ticket tmp = new Ticket(sc);
		assertEquals("1", tmp.getId());
	}
	
}
