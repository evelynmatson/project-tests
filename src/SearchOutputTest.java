import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * JUnit tests for the inverted index search functionality.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Spring 2019
 */
public class SearchOutputTest {

	/**
	 * Generates the arguments to use for this test case. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param testCase the test case to use
	 * @param exact whether to conduct an exact search
	 */
	public static void test(SearchTestCases testCase, boolean exact) {
		String type = exact ? "exact" : "partial";
		String prefix = "results-" + type;
		String filename = prefix + "-" + testCase.label + ".json";

		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve(prefix).resolve(filename);

		List<String> args = new ArrayList<>();
		Collections.addAll(args, "-path", testCase.input.toString());
		Collections.addAll(args, "-query", testCase.query.toString());
		Collections.addAll(args, "-results", actual.normalize().toString());

		if (exact) {
			args.add("-exact");
		}

		TestUtilities.checkOutput(args.toArray(String[]::new), actual, expected);
	}

	@Nested
	@SuppressWarnings("javadoc")
	public class ExactSearchTest {

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Simple.+")
		public void testSimpleFiles(SearchTestCases testCase) {
			test(testCase, true);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^RFC.+")
		public void testRFCFiles(SearchTestCases testCase) {
			test(testCase, true);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Guten.+")
		public void testGutenFiles(SearchTestCases testCase) {
			test(testCase, true);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, names = {"AllRFC"})
		public void testDirectoryShort(SearchTestCases testCase) {
			test(testCase, true);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, names = {"AllGuten", "AllText"})
		public void testDirectoryLong(SearchTestCases testCase) {
			test(testCase, true);
		}

	}

	@Nested
	@SuppressWarnings("javadoc")
	public class PartialSearchTest {

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Simple.+")
		public void testSimpleFiles(SearchTestCases testCase) {
			test(testCase, false);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^RFC.+")
		public void testRFCFiles(SearchTestCases testCase) {
			test(testCase, false);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Guten.+")
		public void testGutenFiles(SearchTestCases testCase) {
			test(testCase, false);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, names = {"AllRFC"})
		public void testDirectoryShort(SearchTestCases testCase) {
			test(testCase, false);
		}

		@ParameterizedTest
		@EnumSource(value = SearchTestCases.class, names = {"AllGuten", "AllText"})
		public void testDirectoryLong(SearchTestCases testCase) {
			test(testCase, false);
		}

	}

}
