package HelpDeskTicketSystemTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.Ticket;
import HelpDeskTicketSystem.User;
import HelpDeskTicketSystem.FileHandler;
import HelpDeskTicketSystem.StaffUser;
import HelpDeskTicketSystem.TechUser;

class FileHandlerTest extends FileHandler {
	
	// Test writing user to file
	@Test
	void testUserWriteandLoad() {
		ArrayList<User> users = new ArrayList<User>(),
				tmp_users = new ArrayList<User>();
		users.add(new StaffUser("1", "asdf", "Foo", "Bar", "Foo@Bar.com", "6360000000"));
		users.add(new TechUser("2", "fdsa", "Bar", "Foo", 0, 0, 0));
		FileHandler.writeUserDatabase("testusers.txt", users);
		tmp_users = FileHandler.loadUserDatabase("testusers.txt");
		assertEquals(tmp_users.get(0).getId(), users.get(0).getId());
	}

	// Test writing ticket to file
	@Test
	void testTicketWriteandLoad() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>(),
				tmp_tickets = new ArrayList<Ticket>();
		tickets.add(new Ticket( "1", "TestCreatorFirstName", "TestCreatorLastName",
						"2",  "test@test.com", "0299991111",
						"test description", Ticket.TicketSeverity.HIGH, "1"));
		tickets.get(0).setStatus(false);
		FileHandler.writeTicketDatabase("testtickets.txt", tickets);
		tmp_tickets = FileHandler.loadTicketDatabase("testtickets.txt");
		assertEquals(tmp_tickets.get(0).getId(), tickets.get(0).getId());
	}
	
	// Test load failure behavior
	@Test
	void testTicketLoadFailure() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>(),
				tmp_tickets = null;
		tickets.add(new Ticket( "1", "TestCreatorFirstName", "TestCreatorLastName",
						"2",  "test@test.com", "0299991111",
						"test description", Ticket.TicketSeverity.HIGH, "1"));
		tickets.get(0).setStatus(false);
		tmp_tickets = FileHandler.loadTicketDatabase("p.txt");
		assertTrue(tmp_tickets.isEmpty());
		tmp_tickets = FileHandler.loadTicketDatabase("test.txt");
		assertTrue(tmp_tickets.isEmpty());
	}

}
