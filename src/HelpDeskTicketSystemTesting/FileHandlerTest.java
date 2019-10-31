package HelpDeskTicketSystemTesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.Ticket;
import HelpDeskTicketSystem.FileHandler;

class FileHandlerTest extends FileHandler {
	// Test writing to file
	@Test
	void testWriteandLoad() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>(),
				tmp_tickets = new ArrayList<Ticket>();
		tickets.add(new Ticket( "1", "TestCreatorFirstName", "TestCreatorLastName",
						"2",  "test@test.com", "0299991111",
						"test description", Ticket.TicketSeverity.HIGH));
		tickets.get(0).setStatus(false);
		FileHandler.writeTicketDatabase("test.txt", tickets);
		tmp_tickets = FileHandler.loadTicketDatabase("test.txt", "TICKET");
		assertEquals(tmp_tickets.get(0).getId(), tickets.get(0).getId());
	}
	
	@Test
	void testLoadFailure() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>(),
				tmp_tickets = null;
		tickets.add(new Ticket( "1", "TestCreatorFirstName", "TestCreatorLastName",
						"2",  "test@test.com", "0299991111",
						"test description", Ticket.TicketSeverity.HIGH));
		tickets.get(0).setStatus(false);
		tmp_tickets = FileHandler.loadTicketDatabase("p.txt", "TICKET");
		assertNull(tmp_tickets);
		tmp_tickets = FileHandler.loadTicketDatabase("test.txt", "NOTET");
		assertNull(tmp_tickets);
	}

}
