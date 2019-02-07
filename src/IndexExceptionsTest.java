import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * Tests that code runs without throwing exceptions.
 */
@SuppressWarnings("javadoc")
public class IndexExceptionsTest {

	@Test
	public void testNoArguments() {
		String[] args = {};
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testBadArguments() {
		String[] args = { "hello", "world" };
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testMissingPath() {
		String[] args = { "-path" };
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testInvalidPath() {
		// generates a random path name
		String path = Long.toHexString(Double.doubleToLongBits(Math.random()));
		String[] args = { "-path", path };
		TestUtilities.testNoExceptions(args);
	}

	@Test
	public void testNoOutput() throws IOException {
		String path = Paths.get("text", "simple", "hello.txt").toString();
		String[] args = { "-path", path };

		// make sure to delete old index.json if it exists
		Path output = Paths.get("index.json");
		Files.deleteIfExists(output);

		TestUtilities.testNoExceptions(args);

		// make sure a new index.json was not created
		assertFalse(Files.exists(output));
	}

	@Test
	public void testDefaultOutput() throws IOException {
		String path = Paths.get("text", "simple", "hello.txt").toString();
		String[] args = { "-path", path, "-index" };

		// make sure to delete old index.json if it exists
		Path output = Paths.get("index.json");
		Files.deleteIfExists(output);

		TestUtilities.testNoExceptions(args);

		// make sure a new index.json was created
		assertTrue(Files.exists(output));
	}

	@Test
	public void testEmptyOutput() throws IOException {
		String[] args = { "-index" };

		// make sure to delete old index.json if it exists
		Path output = Paths.get("index.json");
		Files.deleteIfExists(output);

		TestUtilities.testNoExceptions(args);

		// make sure a new index.json was created
		assertTrue(Files.exists(output));
	}

	@Test
	public void testSwitchedOrder() throws IOException {
		String path = Paths.get("text", "simple", "hello.txt").toString();
		String[] args = { "-index", "-path", path };

		// make sure to delete old index.json if it exists
		Path output = Paths.get("index.json");
		Files.deleteIfExists(output);

		TestUtilities.testNoExceptions(args);

		// make sure a new index.json was created
		assertTrue(Files.exists(output));
	}
}
