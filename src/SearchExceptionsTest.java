import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Tests that code runs without throwing exceptions.
 */
@SuppressWarnings("javadoc")
public class SearchExceptionsTest {

	@Test
	public void testMissingQueryPath() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String[] args = { "-path", path, "-query" };
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testInvalidQueryPath() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Long.toHexString(Double.doubleToLongBits(Math.random()));
		String[] args = { "-path", path, "-query", query };
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testInvalidExactPath() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Long.toHexString(Double.doubleToLongBits(Math.random()));
		String[] args = { "-path", path, "-query", query, "-exact" };
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testNoOutput() throws IOException {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String[] args = { "-path", path, "-query", query };

		// make sure to delete old index.json and results.json if it exists
		Path index = Path.of("index.json");
		Path results = Path.of("results.json");
		Files.deleteIfExists(index);
		Files.deleteIfExists(results);

		TestUtilities.testNoExceptions(args);

		// make sure a new index.json and results.json were not created
		Assert.assertFalse(Files.exists(index) || Files.exists(results));
	}

	@Test
	public void testDefaultOutput() throws IOException {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String[] args = { "-path", path, "-query", query, "-results" };

		// make sure to delete old index.json and results.json if it exists
		Path index = Path.of("index.json");
		Path results = Path.of("results.json");
		Files.deleteIfExists(index);
		Files.deleteIfExists(results);

		TestUtilities.testNoExceptions(args);

		// make sure a new results.json was not created (but index.json was not)
		Assert.assertTrue(Files.exists(results) && !Files.exists(index));
	}

	@Test
	public void testEmptyIndex() throws IOException {
		String query = Path.of("query", "simple.txt").toString();
		String[] args = { "-query", query, "-results" };

		// make sure to delete old index.json and results.json if it exists
		Path index = Path.of("index.json");
		Path results = Path.of("results.json");
		Files.deleteIfExists(index);
		Files.deleteIfExists(results);

		TestUtilities.testNoExceptions(args);

		// make sure a new results.json was not created (but index.json was not)
		Assert.assertTrue(Files.exists(results) && !Files.exists(index));
	}

	@Test
	public void testEmptyQuery() throws IOException {
		String[] args = { "-results" };

		// make sure to delete old index.json and results.json if it exists
		Path index = Path.of("index.json");
		Path results = Path.of("results.json");
		Files.deleteIfExists(index);
		Files.deleteIfExists(results);

		TestUtilities.testNoExceptions(args);

		// make sure a new results.json was not created (but index.json was not)
		Assert.assertTrue(Files.exists(results) && !Files.exists(index));
	}

	@Test
	public void testSwitchedOrder() throws IOException {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String[] args = { "-query", query, "-results", "-path", path, "-exact" };

		// make sure to delete old index.json and results.json if it exists
		Path index = Path.of("index.json");
		Path results = Path.of("results.json");
		Files.deleteIfExists(index);
		Files.deleteIfExists(results);

		TestUtilities.testNoExceptions(args);

		// make sure a new results.json was not created (but index.json was not)
		Assert.assertTrue(Files.exists(results) && !Files.exists(index));
	}
}
