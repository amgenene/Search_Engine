/**
 * Alazar Genene 
 * 
 * This is our examples class which holds all of our test cases for both HW5 and HW6. 
 */
import java.util.LinkedList;
import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;
import java.io.*;

public class Examples {
	// declare variables for all of your search engines here
	SearchEngine s;
	SimpleMarkdownReader smr;

	// local method to add a page to a search engine. Created a
	// helper so that we can isolate the exception handling here,
	// rather than clutter up each test case with the exceptions
	private Webpage addPage(SearchEngine s, String p) {
		try {
			return s.visitPage(p);
		} catch (FileNotFoundException e) {
			fail("Aborting Example setup -- file not found: " + e);
		} catch (UnsupportedFileExn e) {
			fail("Aborting Examples setup -- unsupported file extension: " + e);
		}
		return null;
	}

	public Examples() {
		try {
			// set up all of your search engines with pages here
			// rather than in the individual tests (or you will have
			// to copy the exceptions code into the test classes)
			smr = new SimpleMarkdownReader();
			s = new SearchEngine(new LinkedList<String>());
			addPage(s, "src/PageFiles/goatsZoo.md");
			addPage(s, "src/PageFiles/aboutWPI.md");
			addPage(s, "src/PageFiles/worcesterZoo.md");
			addPage(s, "src/PageFiles/KoalasZoo.md");
		} catch (FileNotFoundException e) {
			System.out.println("Aborting Example setup -- file not found: " + e);
		} catch (UnsupportedFileExn e) {
			System.out.println("Aborting Examples setup -- unsupported file extension: " + e);
		}
	}

	@Test
	public void testGoatsQuery() {
		assertEquals(s.runQuery("goat").size(), 3);
	}

	@Test
	public void testWPIQuery() {
		assertEquals(s.runQuery("WPI").size(), 2);
		addPage(s, "src/PageFiles/worcesterZoo.md");
		assertEquals(s.runQuery("WPI").size(), 3);
	}

	@Test
	public void testKoalaQuery() {
		addPage(s, "src/PageFiles/KoalasZoo.md");
		assertEquals(s.runQuery("what is a koala").size(), 2);
		// assertTrue(s.runQuery("what is a koala").contains("koala"));
	}

	@Test
	public void testTwoQuery() {
		addPage(s, "src/PageFiles/KoalasZoo.md");
		assertEquals(s.runQuery("KoALa is what").size(), 2);
		assertEquals(s.knownPageCount(), 5);
		assertEquals(s.runQuery("koala a is").size(), 2);
		assertEquals(s.knownPageCount(), 5);
	}

	@Test
	public void testWorcesterPages() throws FileNotFoundException {
		addPage(s, "src/PageFiles/worcesterZoo.md");
		assertEquals(s.runQuery("universities").size(), 3);
	}

	@Test
	public void testGoatVisitPage() throws UnsupportedFileExn, FileNotFoundException {
		s.runQuery("a goat");
		s.runQuery("pizza");
		assertEquals(s.visitPage("src/PageFiles/goatsZoo.md").url, smr.readPage("src/PageFiles/goatsZoo.md").url);
	}

	@Test
	public void testWorcesterVisitPage2() throws UnsupportedFileExn, FileNotFoundException {
		s.runQuery("worcester");
		s.runQuery("pizza");
		assertEquals(s.visitPage("src/PageFiles/worcesterZoo.md").url, smr.readPage("src/PageFiles/worcesterZoo.md").url);
	}

	@Test
	public void testSorting() {
		try {
			s = new SearchEngine(new LinkedList<String>());
		} catch (FileNotFoundException | UnsupportedFileExn e) {
			e.printStackTrace();
		}
			Webpage p1 = new Webpage("d", "d", "d", (new LinkedList<String>()));
			Webpage p2 = new Webpage("d", "d", "d", (new LinkedList<String>()));
			Webpage p3 = new Webpage("d", "d", "d", (new LinkedList<String>()));
//			Webpage p2 = addPage(s, "src/PageFiles/aboutWPI.md");
//			Webpage p3 = addPage(s, "src/PageFiles/worcesterZoo.md");
			p1.rank = 7.0;
			p2.rank = 9.0;
			p3.rank = 4.0;
			LinkedList<Webpage> pages = new LinkedList<>();
			pages.add(p1);
			pages.add(p2);
			pages.add(p3);
			s.sortRank(pages);
			assertEquals(pages.get(0).rank, 9.0, 0.0);
			assertEquals(pages.get(1).rank, 7.0, 0.0);
			assertEquals(pages.get(2).rank, 4.0, 0.0);
	}	
	
	@Test
	public void testSponsorRanking() throws LowerRateException, InvalidRateException {
		s.updateSponsor("WPI", .06);
		s.updateSponsor("Zoo", .05);
		LinkedList<Webpage> pages = s.runQuery("wpi");
		assertEquals(pages.get(0).rank, 1.55, 0.0);
		assertEquals(pages.get(1).rank, 1.55, 0.0);
		assertEquals(pages.get(2).rank, 1.55, 0.0);
		assertEquals(pages.get(3).rank, 1.55, 0.0);
	}
}
