import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class Project3Test {

	/*
	 * To be eligible for code review for Project 3, you must pass this test
	 * suite EXCEPT the thread runtime tests on the lab computers using the
	 * `project` script.
	 */

	@BeforeAll
	public static void testEnvironment() {
		assertTrue(TestUtilities.isEnvironmentSetup());
	}

	@Nested
	public class Project3ThreadTest extends ThreadOutputTest {
		// includes all the tests from ThreadOutputTest
	}

	@Nested
	public class Project3ExceptionsTest extends ThreadExceptionsTest {
		// includes all the tests from ThreadExceptionsTest
	}

	/*
	 * The runtime tests take awhile; only run when absolutely necessary.
	 */

	@Nested
	public class Project3RuntimeTest extends ThreadRuntimeTest {
	// includes all the tests from ThreadRuntimeTest
	}

	@Nested
	public class PreviousTests {

		/*
		 * To reduce the time these tests takes, only the overall index and search
		 * results will be re-tested for projects 1 and 2.
		 */

		@Nested
		public class Project1IndexTest {
			@Test
			public void testIndexOutput() {
				IndexOutputTest.test(IndexTestCases.AllText);
			}
		}

		@Nested
		public class Project2SearchTest {
			@Test
			public void testExactSearchOutput() {
				SearchOutputTest.test(SearchTestCases.AllText, true);
			}

			@Test
			public void testPartialSearchOutput() {
				SearchOutputTest.test(SearchTestCases.AllText, false);
			}
		}

		@Nested
		public class Project1ExceptionTest extends IndexExceptionsTest {
			// includes all the tests from IndexExceptionsTest
		}

		@Nested
		public class Project2ExceptionsTest extends SearchExceptionsTest {
			// includes all the tests from SearchExceptionsTest
		}

	}

}
