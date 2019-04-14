import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * These are the test cases for comparing the actual and expected output for
 * crawling the web.
 */
@SuppressWarnings("javadoc")
public enum CrawlTestCases {
	// the single simple test cases (start here)
	SimpleHello   ("simple-hello"   , "https://www.cs.usfca.edu/~cs212/simple/hello.html", "simple.txt", 1),
	SimpleElephant("simple-elephant", "https://www.cs.usfca.edu/~cs212/simple/mixed_case.htm", "simple.txt", 1),
	SimplePosition("simple-position", "https://www.cs.usfca.edu/~cs212/simple/position.html", "simple.txt", 1),
	SimpleSymbols ("simple-symbols" , "https://www.cs.usfca.edu/~cs212/simple/symbols.html", "simple.txt", 1),

	// special cases to test url handling (only used for location output)
	Recurse ("other-recurse",  "https://www.cs.usfca.edu/~cs212/recurse/link01.html", null, 100),
	Redirect("other-redirect", "https://www.cs.usfca.edu/~cs212/redirect/index.html", null, 10),
	Birds   ("other-birds",    "https://www.cs.usfca.edu/~cs212/birds/birds.html",    null, 50),

	// the single rfc file test cases
	RFC475 ("rfc475",  "https://www.cs.usfca.edu/~cs212/rfcs/rfc475.html", "complex.txt", 1),
	RFC5646("rfc5646", "https://www.cs.usfca.edu/~cs212/rfcs/rfc5646.html", "complex.txt", 1),
	RFC6797("rfc6797", "https://www.cs.usfca.edu/~cs212/rfcs/rfc6797.html", "complex.txt", 1),
	RFC6805("rfc6805", "https://www.cs.usfca.edu/~cs212/rfcs/rfc6805.html", "complex.txt", 1),
	RFC6838("rfc6838", "https://www.cs.usfca.edu/~cs212/rfcs/rfc6838.html", "complex.txt", 1),
	RFC7231("rfc7231", "https://www.cs.usfca.edu/~cs212/rfcs/rfc7231.html", "complex.txt", 1),

	// the single gutenberg test cases
	Guten1400 ("guten1400", "https://www.cs.usfca.edu/~cs212/guten/1400-h/1400-h.htm", "complex.txt", 1),
	Guten1228 ("guten1228", "https://www.cs.usfca.edu/~cs212/guten/1228-h/1228-h.htm", "complex.txt", 1),
	Guten1322 ("guten1322", "https://www.cs.usfca.edu/~cs212/guten/1322-h/1322-h.htm", "complex.txt", 1),
	Guten1661 ("guten1661", "https://www.cs.usfca.edu/~cs212/guten/1661-h/1661-h.htm", "complex.txt", 1),
	Guten22577("guten22577", "https://www.cs.usfca.edu/~cs212/guten/22577-h/22577-h.htm", "complex.txt", 1),
	Guten37134("guten37134", "https://www.cs.usfca.edu/~cs212/guten/37134-h/37134-h.htm", "complex.txt", 1),

	// the single java test case
	Java("java-index",   "https://www.cs.usfca.edu/~cs212/docs/jdk-11.0.2_doc-all/api/allclasses-index.html", "complex.txt", 1),

	// multiple file crawls
	AllSimple("simple", "https://www.cs.usfca.edu/~cs212/simple/index.html", "simple.txt", 10),
	AllRFCs  ("rfcs",   "https://www.cs.usfca.edu/~cs212/rfcs/index.html", "complex.txt", 7),
	AllGuten ("guten",  "https://www.cs.usfca.edu/~cs212/guten/", "complex.txt", 7),
	AllJava  ("java",   "https://www.cs.usfca.edu/~cs212/docs/jdk-11.0.2_doc-all/api/allclasses-index.html", "complex.txt", 50);

	/** The label to use in the actual/expected output filenames. */
	public final String label;

	/** The URL to use as input to the inverted index. */
	public final String url;

	/** The path to the query file to use for this test case. */
	public final Path query;

	/** The number of pages to crawl from this url seed. */
	public final int limit;

	/** Initializes this test case. */
	private CrawlTestCases(String label, String url, String query, int limit) {
		this.label = label;
		this.url = url;
		this.query = query == null ? null : Paths.get("query").resolve(query);
		this.limit = limit;
	}
}
