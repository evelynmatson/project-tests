import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/**
 * JUnit tests for the inverted index functionality.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Spring 2019
 */
public class IndexOutputTest {

	/**
	 * Generates the arguments to use for this test case. Designed to be used
	 * inside a JUnit test.
	 *
	 * @param testCase the test case to use
	 */
	public void test(IndexTestCases testCase) {
		String prefix = "index-text";
		String filename = testCase.label + ".json";

		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve(prefix).resolve(filename);

		String[] args = {
				"-path", testCase.input.normalize().toString(),
				"-index", actual.normalize().toString()
		};

		TestUtilities.checkOutput(args, actual, expected);
	}

	/**
	 * Tests the locations functionality of the inverted index on the entire
	 * input directory.
	 */
	@Test
	public void testLocation() {
		String filename = "locations.json";

		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve(filename);

		String[] args = {
				"-path", TestUtilities.TEXT_INPUT.normalize().toString(),
				"-locations", actual.normalize().toString()
		};

		TestUtilities.checkOutput(args, actual, expected);
	}

	/**
	 * These unit tests match all of the test cases that start with "Simple"
	 * followed by at least one character. For example, this will include the
	 * test case "SimpleHello" but not the test case "Simple".
	 *
	 * @param testCase the individual test case
	 */
	@ParameterizedTest
	@EnumSource(value = IndexTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Simple.+")
	public void testSimpleFiles(IndexTestCases testCase) {
		test(testCase);
	}

	/**
	 * These unit tests match all of the test cases that start with "RFC" followed
	 * by at least one character. For example, this will include the test case
	 * "RFC475" but not the test case "RFC".
	 *
	 * @param testCase the individual test case
	 */
	@ParameterizedTest
	@EnumSource(value = IndexTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^RFC.+")
	public void testRFCFiles(IndexTestCases testCase) {
		test(testCase);
	}

	/**
	 * These unit tests match all of the test cases that start with "Guten" followed
	 * by at least one character. For example, this will include the test case
	 * "Guten1440" but not the test case "AllGuten".
	 *
	 * @param testCase the individual test case
	 */
	@ParameterizedTest
	@EnumSource(value = IndexTestCases.class, mode = EnumSource.Mode.MATCH_ALL, names = "^Guten.+")
	public void testGutenFiles(IndexTestCases testCase) {
		test(testCase);
	}

	/**
	 * This unit test will only match the "AllSimple" and "AllRFC" test cases.
	 *
	 * @param testCase the individual test case
	 */
	@ParameterizedTest
	@EnumSource(value = IndexTestCases.class, names = {"AllSimple", "AllRFC"})
	public void testDirectoryShort(IndexTestCases testCase) {
		test(testCase);
	}

	/**
	 * This unit test will only match the "AllGuten" and "AllText" test case. These
	 * test cases may have longer runtime than the others.
	 *
	 * @param testCase the individual test case
	 */
	@ParameterizedTest
	@EnumSource(value = IndexTestCases.class, names = {"AllGuten", "AllText"})
	public void testDirectoryLong(IndexTestCases testCase) {
		test(testCase);
	}

}
