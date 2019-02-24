import java.nio.file.Path;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public class ThreadExceptionsTest {

	@Test
	public void testNegativeThreads() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String threads = "-1";
		String[] args = { "-path", path, "-query", query, "-threads", threads };
		TestUtilities.checkExceptions(args);
	}

	@Test
	public void testZeroThreads() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String threads = "0";
		String[] args = { "-path", path, "-query", query, "-threads", threads };
		TestUtilities.checkExceptions(args);
	}

	@Test
	public void testFractionThreads() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String threads = "3.14";
		String[] args = { "-path", path, "-query", query, "-threads", threads };
		TestUtilities.checkExceptions(args);
	}

	@Test
	public void testWordThreads() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String threads = "fox";
		String[] args = { "-path", path, "-query", query, "-threads", threads };
		TestUtilities.checkExceptions(args);
	}

	@Test
	public void testDefaultThreads() {
		String path = Path.of("text", "simple", "hello.txt").toString();
		String query = Path.of("query", "simple.txt").toString();
		String[] args = { "-path", path, "-query", query, "-threads" };
		TestUtilities.checkExceptions(args);
	}
}
