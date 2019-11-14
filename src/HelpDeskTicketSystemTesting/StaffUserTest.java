package HelpDeskTicketSystemTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import HelpDeskTicketSystem.StaffUser;

class StaffUserTest {
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	private final PrintStream ogOut = System.out;
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(out));
	}
	
	@After
	public void restoreStreams() {
		System.setOut(ogOut);
	}
	
	StaffUser tmp = new StaffUser("1", "asdf", "Foo", "Bar", "foo@bar.com", "0410101010");
	
	@Test
	void testScannerConstructor() {
		String i = "2\n"
				+  "fdsa\n"
				+  "Bar\n"
				+  "Foo\n"
				+  "Bar@foo.com\n"
				+  "0412312312\n";
		StaffUser newUser = new StaffUser(new Scanner(i));
		assertEquals("2", newUser.getId());
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
	
	@Test
	void testWriteAttributes() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter("stafftest.txt");
		tmp.writeAttributes(pw);
		File file = new File("stafftest.txt");
		pw.close();
		assertEquals(true, file.exists());
	}

}
