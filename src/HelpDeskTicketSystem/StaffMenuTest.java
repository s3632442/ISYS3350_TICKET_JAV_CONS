package HelpDeskTicketSystem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class StaffMenuTest extends StaffMenu {

	@Test
	void testMain() {
		
		String userInput = new Scanner(System.in).nextLine();
		assertEquals( "", userInput.length() != 1);
	}

}
