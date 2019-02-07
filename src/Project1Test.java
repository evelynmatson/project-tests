import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

@SuppressWarnings("javadoc")
public class Project1Test {

	/*
	 * To be eligible for code review for Project 1, you must pass this test
	 * suite on the lab computers using the `project` script.
	 */

	@BeforeAll
	public static void testEnvironment() {
		assertTrue(TestUtilities.isEnvironmentSetup());
	}

	@Nested
	public class Project1IndexTest extends IndexOutputTest {
		// includes all the tests from IndexTest
	}

	@Nested
	public class Project1ExceptionTest extends IndexExceptionsTest {
		// includes all the tests from IndexExceptionsTest
	}

}
