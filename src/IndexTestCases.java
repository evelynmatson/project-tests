import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * These are the test cases for comparing the actual and expected output for
 * the inverted index.
 */
@SuppressWarnings("javadoc")
public enum IndexTestCases {
	// the single simple test cases (start here)
	SimpleHello   (Paths.get("text", "simple", "hello.txt")),
	SimpleAnimals (Paths.get("text", "simple", "animals.text")),
	SimpleCapitals(Paths.get("text", "simple", "capitals.txt")),
	SimpleDigits  (Paths.get("text", "simple", "digits.txt")),
	SimplePosition(Paths.get("text", "simple", "position.teXt")),
	SimpleWords   (Paths.get("text", "simple", "words.tExT")),

	// the single rfc file test cases
	RFC475 (Paths.get("text", "rfcs", "rfc475.txt")),
	RFC5646(Paths.get("text", "rfcs", "rfc5646.txt")),
	RFC6797(Paths.get("text", "rfcs", "rfc6797.txt")),
	RFC6805(Paths.get("text", "rfcs", "rfc6805.txt")),
	RFC6838(Paths.get("text", "rfcs", "rfc6838.txt")),
	RFC7231(Paths.get("text", "rfcs", "rfc7231.txt")),

	// the single gutenberg test cases
	Guten1400 (Paths.get("text", "guten", "1400-0.txt")),
	Guten1228 (Paths.get("text", "guten", "pg1228.txt")),
	Guten1322 (Paths.get("text", "guten", "pg1322.txt")),
	Guten1661 (Paths.get("text", "guten", "pg1661.txt")),
	Guten22577(Paths.get("text", "guten", "pg22577.txt")),
	Guten37134(Paths.get("text", "guten", "pg37134.txt")),

	// the directory test cases
	AllSimple(Paths.get("text", "simple")),
	AllRFC   (Paths.get("text", "rfcs")),
	AllGuten (Paths.get("text", "guten")),
	AllText  (Paths.get("text"));

	/** The input path to use for building the index. */
	public final Path input;

	/** The filename to use for actual/expected output. */
	public final String label;

	/**
	 * Initializes this test case based on the input path.
	 *
	 * @param input the input path to use for building the index
	 */
	private IndexTestCases(Path input) {
		this.input = input;

		// determine filename to use for actual/expected output
		if (Files.isDirectory(input)) {
			// e.g. index-simple.json
			this.label = "index-" + input.getFileName();
		}
		else {
			// e.g. index-simple-hello.json
			String[] parts = input.getFileName().toString().split("\\.");
			String subdir = input.getParent().getFileName().toString();
			this.label = "index-" + subdir + "-" + parts[0];
		}
	}
}