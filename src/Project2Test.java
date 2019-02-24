import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;

@SuppressWarnings("javadoc")
public class Project2Test {

	/*
	 * To be eligible for code review for Project 2, you must pass this test
	 * suite on the lab computers using the `project` script.
	 */

	@BeforeAll
	public static void testEnvironment() {
		assertTrue(TestUtilities.isEnvironmentSetup());
	}

	@Nested
	public class Project2SearchTest extends SearchOutputTest {
		// includes all the tests from SearchOutputTest
	}

	@Nested
	public class Project2ExceptionsTest extends SearchExceptionsTest {
		// includes all the tests from SearchExceptionsTest
	}

	@Nested
	public class PreviousTests {

		@Nested
		public class Project1IndexTest extends IndexOutputTest {
			// includes all the tests from IndexTest
		}

		@Nested
		public class Project1ExceptionTest extends IndexExceptionsTest {
			// includes all the tests from IndexExceptionsTest
		}

	}

}
