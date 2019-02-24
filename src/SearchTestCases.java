import java.nio.file.Files;
import java.nio.file.Path;

/**
 * These are the test cases for comparing the actual and expected output for
 * searching the inverted index from query file.
 */
@SuppressWarnings("javadoc")
public enum SearchTestCases {
	// various simple test cases
	SimpleWords  ("words.txt", Path.of("simple", "words.tExT")),
	SimpleSimple ("simple.txt", Path.of("simple")),

	// the single rfc file test cases
	RFC475 ("complex.txt", Path.of("rfcs", "rfc475.txt")),
	RFC5646("complex.txt", Path.of("rfcs", "rfc5646.txt")),
	RFC6797("complex.txt", Path.of("rfcs", "rfc6797.txt")),
	RFC6805("complex.txt", Path.of("rfcs", "rfc6805.txt")),
	RFC6838("complex.txt", Path.of("rfcs", "rfc6838.txt")),
	RFC7231("complex.txt", Path.of("rfcs", "rfc7231.txt")),

	// the single gutenberg test cases
	Guten1400 ("complex.txt", Path.of("guten", "1400-0.txt")),
	Guten1228 ("complex.txt", Path.of("guten", "pg1228.txt")),
	Guten1322 ("complex.txt", Path.of("guten", "pg1322.txt")),
	Guten1661 ("complex.txt", Path.of("guten", "pg1661.txt")),
	Guten22577("complex.txt", Path.of("guten", "pg22577.txt")),
	Guten37134("complex.txt", Path.of("guten", "pg37134.txt")),

	// the directory test cases using letters.txt
	AllRFC   ("complex.txt", Path.of("rfcs")),
	AllGuten ("complex.txt", Path.of("guten")),
	AllText  ("complex.txt", Path.of("."));

	/** The input path to use for building the index. */
	public final Path input;

	/** The input path to use for the query file. */
	public final Path query;

	/** The filename to use for actual/expected output. */
	public final String label;

	/**
	 * Initializes this test case based on the input and query paths.
	 * @param query the query path to use for searching the index
	 * @param input the input path to use for building the index
	 */
	private SearchTestCases(String query, Path input) {
		this.input = Path.of("text").resolve(input).normalize();
		this.query = Path.of("query").resolve(query).normalize();

		// determine filename to use for actual/expected output
		if (Files.isDirectory(this.input)) {
			this.label = this.input.getFileName().toString();
		}
		else {
			String[] parts = this.input.getFileName().toString().split("\\.");
			String subdir = this.input.getParent().getFileName().toString();
			this.label = subdir + "-" + parts[0];
		}
	}
}
