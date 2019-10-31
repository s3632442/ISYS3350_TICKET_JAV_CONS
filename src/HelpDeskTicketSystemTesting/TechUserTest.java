package HelpDeskTicketSystemTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.TechUser;

class TechUserTest {
	
	static TechUser tmp;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tmp = new TechUser("1", "asdf", "Foo", "Bar", 0, new ArrayList<String>());
	}

	@Test
	void testGetActiveCount() {
		assertEquals(1, tmp.getActiveCount());
	}

	@Test
	void testGetTicketIds() {
		assertEquals(2, tmp.getTicketIds().size());
	}

	@Test
	void testSetActiveCount() {
		tmp.setActiveCount(1);
		assertEquals(1, tmp.getActiveCount());
	}

	@Test
	void testSetTicketIds() {
		ArrayList<String> ids = new ArrayList<String>();
		ids.add("testid");
		tmp.setTicketIds(ids);
		assertEquals("testid", tmp.getTicketIds().get(0));
	}

	@Test
	void testAddTicketId() {
		String id = "idtest";
		tmp.addTicketId(id);
		assertEquals("idtest", tmp.getTicketIds().get(1));
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

}
