import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("javadoc")
public class ThreadOutputTest {

	public static final Duration TIMEOUT = Duration.ofMinutes(3);

	public static void test(IndexTestCases testCase, int threads) {
		String actualLabel = String.format("%s-%d.json", testCase.label, threads);
		String expectLabel = String.format("%s.json", testCase.label);

		Path actual = TestUtilities.ACTUAL_PATH.resolve(actualLabel);
		Path expected = TestUtilities.EXPECTED_PATH.resolve("index-text").resolve(expectLabel);

		String[] args = {
				"-path", testCase.input.normalize().toString(),
				"-index", actual.normalize().toString(),
				"-threads", Integer.toString(threads)
		};

		assertTimeout(TIMEOUT, () -> {TestUtilities.checkOutput(args, actual, expected);});
	}

	@Nested
	public class ThreadIndexTest {

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testHello(int threads) {
			test(IndexTestCases.SimpleHello, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testSimple(int threads) {
			test(IndexTestCases.AllSimple, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testRFCs(int threads) {
			test(IndexTestCases.AllRFC, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testGuten1400(int threads) {
			test(IndexTestCases.Guten1400, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testAll(int threads) {
			test(IndexTestCases.AllText, threads);
		}
	}

	public static void test(SearchTestCases testCase, boolean exact, int threads) {
		String type = exact ? "exact" : "partial";
		String prefix = "results-" + type;
		String actualLabel = String.format("%s-%s-%d.json", prefix, testCase.label, threads);
		String expectLabel = String.format("%s-%s.json", prefix, testCase.label);

		Path actual = TestUtilities.ACTUAL_PATH.resolve(actualLabel);
		Path expected = TestUtilities.EXPECTED_PATH.resolve(prefix).resolve(expectLabel);

		List<String> args = new ArrayList<>();
		Collections.addAll(args, "-path", testCase.input.toString());
		Collections.addAll(args, "-query", testCase.query.toString());
		Collections.addAll(args, "-results", actual.normalize().toString());
		Collections.addAll(args, "-threads", Integer.toString(threads));

		if (exact) {
			args.add("-exact");
		}

		assertTimeout(TIMEOUT, () -> {TestUtilities.checkOutput(args.toArray(String[]::new), actual, expected);});
	}

	@Nested
	public class ThreadSearchExactTest {

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testWords(int threads) {
			test(SearchTestCases.SimpleWords, true, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testSimple(int threads) {
			test(SearchTestCases.SimpleSimple, true, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testRFCs(int threads) {
			test(SearchTestCases.AllRFC, true, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testGuten1400(int threads) {
			test(SearchTestCases.Guten1400, true, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testAll(int threads) {
			test(SearchTestCases.AllText, true, threads);
		}
	}

	@Nested
	public class ThreadSearchPartialTest {

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testWords(int threads) {
			test(SearchTestCases.SimpleWords, false, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testSimple(int threads) {
			test(SearchTestCases.SimpleSimple, false, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testRFCs(int threads) {
			test(SearchTestCases.AllRFC, false, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testGuten1400(int threads) {
			test(SearchTestCases.Guten1400, false, threads);
		}

		@ParameterizedTest(name = "{0} thread(s)")
		@ValueSource(ints = {1, 2, 5})
		public void testAll(int threads) {
			test(SearchTestCases.AllText, false, threads);
		}
	}
}
