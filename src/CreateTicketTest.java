import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CreateTicketTest {
	
	CreateTicket ticket = new CreateTicket( "1", "TestCreatorFirstName", "TestCreatorLastName",
			"2",  "test@test.com", "0299991111",
			"test description", false, "TestTechFirstName",
			 "TestTechLastName");
	
	// Test to check ticket is initially set to open
	@Test
	void testGetTicketStatus() {
		assertEquals("Open", ticket.getTicketStatus());
	}
	// Test to check constructor sets Technician first  name to null
	@Test
	void testTicketTechnicianFirstName() {
		assertEquals(null, ticket.getTicketTechnicianFirstName());
	}
	// Test to check constructor sets Technician last name to null
	@Test
	void testTicketTechnicianLastName() {
		assertEquals(null, ticket.getTicketTechnicianLastName());
	}

}
