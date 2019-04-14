import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class Project4Test {

	/*
	 * To be eligible for code review for Project 4, you must pass this test
	 * suite on the lab computers using the `project` script.
	 */

	@BeforeAll
	public static void testEnvironment() {
		assertTrue(TestUtilities.isEnvironmentSetup());
	}

	@Nested
	public class Project4OutputTest extends CrawlOutputTest {
		// includes all the tests from CrawlOutputTest
	}

//	@Nested
//	public class Project4ExceptionsTest extends CrawlExceptionsTest {
//		// includes all the tests from CrawlExceptionsTest
//	}

	@Nested
	public class PreviousTests {

		/*
		 * To reduce the time these tests takes, only the overall index and search
		 * results will be re-tested for projects 1, 2, and 3. The benchmark tests
		 * are not re-run for project 4.
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
		public class Project3ThreadTest {
			@Test
			public void testIndexOutput() {
				ThreadOutputTest.test(IndexTestCases.AllText, 5);
			}

			@Test
			public void testExactSearchOutput() {
				ThreadOutputTest.test(SearchTestCases.AllText, true, 5);
			}

			@Test
			public void testPartialSearchOutput() {
				ThreadOutputTest.test(SearchTestCases.AllText, false, 5);
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

		@Nested
		public class Project3ExceptionsTest extends ThreadExceptionsTest {
			// includes all the tests from ThreadExceptionsTest
		}

	}

}
