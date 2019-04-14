import java.nio.file.Path;
import java.time.Duration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * JUnit tests for the web crawler functionality.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Spring 2019
 */
@SuppressWarnings("javadoc")
public class CrawlOutputTest {

	/** How long each individual test should wait before timing out. */
	public static final Duration TIMEOUT = Duration.ofSeconds(45);

	public static void testLocations(CrawlTestCases testCase) {
		String filename = String.format("location-%s.json", testCase.label);
		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve("locations").resolve(filename);

		String[] args = {
			"-url", testCase.url,
			"-limit", Integer.toString(testCase.limit),
			"-locations", actual.toString(),
			"-threads", Integer.toString(5)
		};

		Assertions.assertTimeoutPreemptively(TIMEOUT, () -> {
			TestUtilities.checkOutput(args, actual, expected);
		});
	}

	@Nested
	public class LocationTest {

		@Test
		public void testRecurse() {
			testLocations(CrawlTestCases.Recurse);
		}

		@Test
		public void testRedirect() {
			testLocations(CrawlTestCases.Redirect);
		}

		@Test
		public void testBirds() {
			testLocations(CrawlTestCases.Birds);
		}

		@Test
		public void testSimple() {
			testLocations(CrawlTestCases.AllSimple);
		}

		@Test
		public void testGutenberg() {
			testLocations(CrawlTestCases.AllGuten);
		}

		@Test
		public void testRFCs() {
			testLocations(CrawlTestCases.AllRFCs);
		}

		@Test
		public void testJava() {
			testLocations(CrawlTestCases.AllJava);
		}
	}

	public static void testIndex(CrawlTestCases testCase) {
		String prefix = "index-urls";
		String filename = String.format("index-%s.json", testCase.label);

		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve(prefix).resolve(filename);

		String[] args = {
				"-url", testCase.url,
				"-limit", Integer.toString(testCase.limit),
				"-index", actual.normalize().toString(),
				"-threads", Integer.toString(5)
		};

		Assertions.assertTimeoutPreemptively(TIMEOUT, () -> {
			TestUtilities.checkOutput(args, actual, expected);
		});
	}

	@Nested
	public class IndexTest {

		@ParameterizedTest
		@EnumSource(value = CrawlTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Simple.+")
		public void testSimple(CrawlTestCases testCase) {
			testIndex(testCase);
		}

		@ParameterizedTest
		@EnumSource(value = CrawlTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^RFC.+")
		public void testRFC(CrawlTestCases testCase) {
			testIndex(testCase);
		}

		@ParameterizedTest
		@EnumSource(value = CrawlTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Guten.+")
		public void testGuten(CrawlTestCases testCase) {
			testIndex(testCase);
		}

		@Test
		public void testAllSimple() {
			testIndex(CrawlTestCases.AllSimple);
		}

		@Test
		public void testAllRFCs() {
			testIndex(CrawlTestCases.AllRFCs);
		}

		@Test
		public void testAllGuten() {
			testIndex(CrawlTestCases.AllGuten);
		}

		@Test
		public void testJava() {
			testIndex(CrawlTestCases.Java);
		}

		@Test
		public void testAllJava() {
			testIndex(CrawlTestCases.AllJava);
		}
	}

	public static void testSearch(CrawlTestCases testCase, boolean exact) {
		String type = exact ? "exact" : "partial";
		String prefix = "results-urls";
		String filename = String.format("results-%s-%s.json", testCase.label, type);

		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve(prefix).resolve(filename);

		String[] args = {
				"-url", testCase.url,
				"-limit", Integer.toString(testCase.limit),
				"-query", testCase.query.toString(),
				"-results", actual.normalize().toString(),
				"-threads", Integer.toString(5)
		};

		Assertions.assertTimeoutPreemptively(TIMEOUT, () -> {
			TestUtilities.checkOutput(args, actual, expected);
		});
	}

	@Nested
	public class SearchTest {

		@ParameterizedTest(name = "exact = {0}")
		@ValueSource(strings = { "true", "false" })
		public void testSimple(boolean exact) {
			testSearch(CrawlTestCases.AllSimple, exact);
		}

		@ParameterizedTest(name = "exact = {0}")
		@ValueSource(strings = { "true", "false" })
		public void testRFCs(boolean exact) {
			testSearch(CrawlTestCases.AllRFCs, exact);
		}

		@ParameterizedTest(name = "exact = {0}")
		@ValueSource(strings = { "true", "false" })
		public void testGuten(boolean exact) {
			testSearch(CrawlTestCases.AllGuten, exact);
		}

		@ParameterizedTest(name = "exact = {0}")
		@ValueSource(strings = { "true", "false" })
		public void testJava(boolean exact) {
			testSearch(CrawlTestCases.AllJava, exact);
		}
	}
}
