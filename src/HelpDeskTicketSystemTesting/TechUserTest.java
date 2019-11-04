package HelpDeskTicketSystemTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.TechUser;

class TechUserTest {
	
	static TechUser tmp;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		tmp = new TechUser("1", "asdf", "Foo", "Bar", 0, 1);
	}

	@Test
	void testGetActiveCount() {
		assertEquals(1, tmp.getActiveCount());
	}

	@Test
	void testGetLevel() {
		assertEquals(0, tmp.getLevel());
	}

	@Test
	void testSetActiveCount() {
		tmp.setActiveCount(1);
		assertEquals(1, tmp.getActiveCount());
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

}
