package HelpDeskTicketSystemTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.StaffUser;

class StaffUserTest {

	StaffUser tmp = new StaffUser("1", "asdf", "Foo", "Bar", "foo@bar.com", "0410101010");
	
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
	void testGetEmail() {
		assertEquals("foo@bar.com", tmp.getEmail());
	}

	@Test
	void testGetContactNumber() {
		assertEquals("0410101010", tmp.getContactNumber());
	}

	@Test
	void testSetEmail() {
		tmp.setEmail("bar@foo.com");
		assertEquals("bar@foo.com", tmp.getEmail());
	}

	@Test
	void testSetContactNumber() {
		tmp.setContactNumber("0401010101");
		assertEquals("0401010101", tmp.getContactNumber());
	}

}
