package HelpDeskTicketSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CreateTicketTest {
	
	CreateTicket ticket = new CreateTicket( "1", "TestCreatorFirstName", "TestCreatorLastName",
			"2",  "test@test.com", "0299991111",
			"test description");
	
	// Test ticket setter and getter with the value of 2
	@Test
	void testSetTicketId() {
		ticket.setTicketId("2");
		assertEquals("2", ticket.getTicketId());
	}
	// Test ticket counter is 1
	@Test
	void testTicketCounter() {
		assertEquals(1, CreateTicket.ticketIDCounter);
	}
	// Test ticket setter and getter for creator first name with the value of foo
	@Test
	void testTicketCreatorName() {
		ticket.setTicketCreatorFirstName("foo");
		assertEquals("foo", ticket.getTicketCreatorFirstName());
	}
	// Test ticket setter and getter for creator last name with the value of bar
	@Test
	void testSetTicketCreatorLastName() {
		ticket.setTicketCreatorLastName("bar");
		assertEquals("bar", ticket.getTicketCreatorLastName());
	}
	// Test ticket setter and getter for creator staff number with the value of 2
	@Test
	void testTicketCreatorStaffNumber() {
		ticket.setTicketCreatorStaffNumber("2");
		assertEquals("2", ticket.getTicketCreatorStaffNumber());
	}
	// Test ticket setter and getter for creator email with a value of foo@bar.com
	@Test
	void testTicketCreatorEmail() {
		ticket.setTicketCreatorEmail("foo@bar.com");
		assertEquals("foo@bar.com", ticket.getTicketCreatorEmail());
	}
	// Test ticker setter and getter for creator contact number with a value of 61411119999
	@Test
	void testTicketCreatorContactNumber() {
		ticket.setTicketCreatorContactNumber("61411119999");
		assertEquals("61411119999", ticket.getTicketCreatorContactNumber());
	}
	// Test ticket setter and getter for description issue with a value of foobar
	@Test
	void testTicketDescriptionIssue() {
		ticket.setDescriptionIssue("foobar");
		assertEquals("foobar", ticket.getDescriptionIssue());
	}
	// Test ticket status initial value has a value of Open
	@Test
	void testGetTicketStatus() {
		assertEquals("Open", ticket.getTicketStatus());
	}
	// Test ticket setter and getter for ticket status with a value of false
	@Test
	void testTicketSetStatus() {
		ticket.setTicketStatus(false);
		assertEquals("Closed", ticket.getTicketStatus());
	}
	// Test to check constructor sets Technician first  name to null
	@Test
	void testTicketTechnicianFirstName() {
		assertEquals(null, ticket.getTicketTechnicianFirstName());
	}
	// Test setter and getter for technician first name with a value of bar
	@Test
	void testSetTicketTechnicianFirstName() {
		ticket.setTicketTechnicianFirstName("bar");
		assertEquals("bar", ticket.getTicketTechnicianFirstName());
	}
	// Test to check constructor sets Technician last name to null
	@Test
	void testTicketTechnicianLastName() {
		assertEquals(null, ticket.getTicketTechnicianLastName());
	}
	// Test setter and getter for technician last name with a value of foo
	@Test
	void testSetTicketTechnicianLastName() {
		ticket.setTicketTechnicianLastName("foo");
		assertEquals("foo", ticket.getTicketTechnicianLastName());
	}

}
