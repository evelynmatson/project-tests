import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

/**
 * This suite of JUnit tests estimate how efficiently the multithreaded versus
 * single threaded code performs. Please keep in mind these only provide an
 * estimate and the runtime output needs to be investigated for anomalies before
 * trusting the results.
 *
 * WARNING: These tests take considerable time, and should not be run unless
 * absolutely necessary.
 *
 * @author CS 212 Software Development
 * @author University of San Francisco
 * @version Spring 2019
 */
public class ThreadRuntimeTest {

	/** The number of warmup runs when benchmarking. */
	private static final int WARM_RUNS = 4;

	/** The number of timed runs when benchmarking. */
	private static final int TIME_RUNS = 6;

	/** The default number of threads to use when benchmarking. */
	private static final int THREADS = 5;

	/** The timeout per run. If code timesout, it is likely not ready for benchmarking. */
	private static final Duration TIMEOUT = Duration.ofMinutes(1);

	/**
	 * Tests that the inverted index output remains consistent when repeated.
	 */
	@RepeatedTest(5)
	public void testIndexConsistency() {
		String filename = "index-text.json";
		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve("index-text").resolve(filename);

		String[] args = {
				"-path", Path.of("text").toString(),
				"-index", actual.toString(),
				"-threads", Integer.toString(THREADS)
		};

		assertTimeout(TIMEOUT, () -> TestUtilities.checkOutput(args, actual, expected));
	}

	/**
	 * Tests that the search result output remains consistent when repeated.
	 */
	@RepeatedTest(5)
	public void testSearchConsistency() {
		String filename = "results-text.json";
		Path actual = TestUtilities.ACTUAL_PATH.resolve(filename);
		Path expected = TestUtilities.EXPECTED_PATH.resolve("results-partial").resolve(filename);

		String[] args = {
				"-path", Path.of("text").toString(),
				"-query", Path.of("query", "complex.txt").toString(),
				"-results", actual.toString(),
				"-threads", Integer.toString(THREADS)
		};

		assertTimeout(TIMEOUT, () -> TestUtilities.checkOutput(args, actual, expected));
	}

	/**
	 * Tests that code runs faster with {@value #THREADS} threads is faster versus
	 * just 1 worker thread.
	 */
	@Test
	public void testIndexMultithreaded() {
		String path = Path.of("text").toString();

		String[] args1 = { "-path", path, "-threads", String.valueOf(1) };
		String[] args2 = { "-path", path, "-threads", String.valueOf(THREADS) };

		System.out.println("Testing Build 1 vs 5 Workers...");
		double result = compare("1 Worker", args1, "5 Workers", args2);

		assertTrue(result > 0, () -> String.format("%s is %.2f seconds faster than %s.", "1 worker", -result, "5 workers"));
	}

	/**
	 * Tests that code runs faster with {@value #THREADS} threads is faster versus
	 * just 1 worker thread.
	 */
	@Test
	public void testSearchMultithreaded() {
		String path = Path.of("text").toString();
		String query = Path.of("query", "complex.txt").toString();

		String[] args1 = { "-path", path, "-query", query, "-threads", String.valueOf(1) };
		String[] args2 = { "-path", path, "-query", query, "-threads", String.valueOf(THREADS) };

		System.out.println("Testing Search 1 vs 5 Workers...");
		double result = compare("1 Worker", args1, "5 Workers", args2);

		assertTrue(result > 0, () -> String.format("%s is %.2f seconds faster than %s.", "1 worker", -result, "5 workers"));
	}

	/**
	 * Tests that the multithreading code with {@value #THREADS} is faster than
	 * the single threaded code (without the -threads parameter).
	 */
	@Test
	public void testIndexSingleMulti() {
		String path = Path.of("text").toString();

		String[] args1 = { "-path", path };
		String[] args2 = { "-path", path, "-threads", String.valueOf(THREADS) };

		System.out.println("Testing Build Single vs Multi...");
		double result = compare("Single", args1, "Multi", args2);

		assertTrue(result > 0, () -> String.format("%s is %.2f seconds faster than %s.", "Single threading", -result, "multithreading"));
	}

	/**
	 * Tests that the multithreading code with {@value #THREADS} is faster than
	 * the single threaded code (without the -threads parameter).
	 */
	@Test
	public void testSearchSingleMulti() {
		String path = Path.of("text").toString();
		String query = Path.of("query", "complex.txt").toString();

		String[] args1 = { "-path", path, "-query", query };
		String[] args2 = { "-path", path, "-query", query, "-threads", String.valueOf(THREADS) };

		System.out.println("Testing Search Single vs Multi...");
		double result = compare("Single", args1, "Multi", args2);

		assertTrue(result > 0, () -> String.format("%s is %.2f seconds faster than %s.", "Single threading", -result, "multithreading"));
	}

	/**
	 * Compares the runtime using two different sets of arguments. Outputs the
	 * runtimes to the console just in case there are any anomalies.
	 *
	 * @param label1 the label of the first argument set
	 * @param args1 the first argument set
	 * @param label2 the label of the second argument set
	 * @param args2 the second argument set
	 * @return the runtime difference between the first and second set of arguments
	 */
	public static double compare(String label1, String[] args1, String label2, String[] args2) {

		long[] runs1 = benchmark(args1);
		long[] runs2 = benchmark(args2);

		long total1 = 0;
		long total2 = 0;

		String labelFormat = "%n%-6s    %10s    %10s%n";
		String valueFormat = "%-6d    %10.6f    %10.6f%n";

		System.out.printf(labelFormat, "Warmup", label1, label2);
		for (int i = 0; i < WARM_RUNS; i++) {
			System.out.printf(valueFormat, i + 1,
					(double) runs1[i] / Duration.ofSeconds(1).toMillis(),
					(double) runs2[i] / Duration.ofSeconds(1).toMillis());
		}

		System.out.printf(labelFormat, "Timed", label1, label2);
		for (int i = WARM_RUNS; i < WARM_RUNS + TIME_RUNS; i++) {
			total1 += runs1[i];
			total2 += runs2[i];
			System.out.printf(valueFormat, i + 1,
					(double) runs1[i] / Duration.ofSeconds(1).toMillis(),
					(double) runs2[i] / Duration.ofSeconds(1).toMillis());
		}

		double average1 = (double) total1 / TIME_RUNS;
		double average2 = (double) total2 / TIME_RUNS;

		System.out.println();
		System.out.printf("%10s:  %10.6f seconds%n", label1, average1 / Duration.ofSeconds(1).toMillis());
		System.out.printf("%10s:  %10.6f seconds%n", label2, average2 / Duration.ofSeconds(1).toMillis());
		System.out.printf("%10s: x%10.6f %n%n", "Speedup", average1 / average2);

		return average1 - average2;
	}

	/**
	 * Benchmarks the {@link Driver#main(String[])} method with the provided
	 * arguments. Tracks the timing of every run to allow of visual inspection.
	 *
	 * @param args the arguments to run
	 * @return an array of all the runtimes, including warmup runs and timed runs
	 */
	public static long[] benchmark(String[] args) {
		long[] runs = new long[WARM_RUNS + TIME_RUNS];

		Instant start;
		Duration elapsed;

		try {
			for (int i = 0; i < WARM_RUNS; i++) {
				start = Instant.now();

				Driver.main(args);

				elapsed = Duration.between(start, Instant.now());
				runs[i] = elapsed.toMillis();
			}

			for (int i = 0; i < TIME_RUNS; i++) {
				start = Instant.now();

				Driver.main(args);

				elapsed = Duration.between(start, Instant.now());
				runs[i + WARM_RUNS] = elapsed.toMillis();
			}
		}
		catch (Exception e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));

			String debug = String.format("%nArguments:%n    [%s]%nException:%n    %s%n", String.join(" ", args),
					writer.toString());
			fail(debug);
		}

		return runs;
	}

}
