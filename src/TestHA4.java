import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Klasse für die HA4: testet die grundlegende Funktionalitäten
 * 
 * Realisiert mit JUnit4: Das müsst ihr zuerst zum Project class path dazu tun, damits funktioniert.
 * 
 * WICHTIG: Ihr braucht auch die Textdateien (maerchen, grimm, usw.) Diese müssen in den Projektordner, also z.B. in
 * HA4, wenn das Projekt "HA4" heisst. Nicht in den src oder bin Ordner.
 * 
 * @author Maximilian Bandle
 * @author Kordian bruck <bruck@in.tum.de>
 * @since 7.12.2013
 * @version 1.2
 * @see http ://kbruck.name/2013/11/23/junit-zum-classpath-hinzufugen-eclipse-kepler/
 * 
 */
public class TestHA4 {
	private LinkedDocument doc0;
	private LinkedDocument doc1;
	private Author testAuthor;
	private Date testDate;
	private final double delta = 0.00000000000000001;// Jetzt genauere Werte ->
														// kleineres Delta

	@Before
	public void setup() {
		this.testDate = new Date(24, 12, 2013);
		this.testAuthor = new Author("Hans", "Wurst", this.testDate, "Daheim 23a, Hier und da", "hans@wurst.de");
		this.doc0 = new LinkedDocument("Hans", "de", "1s", new Date(23, 11, 2013), this.testAuthor,
				"hans hatte sieben link:dateisieben jahre bei seinem herrn gedient", "hans");
		this.doc1 = new LinkedDocument(
				"Wolf",
				"de",
				"1s",
				new Date(31, 5, 1900),
				this.testAuthor,
				"es ist einmal eine alte geiss link:dateiziege gewesen die hatte sieben link:dateisieben junge zicklein",
				"wolf");
	}

	@Test
	public void testAddIncomingLinks() {
		LinkedDocument doc2 = new LinkedDocument("la", "la", "lu", null, this.testAuthor,
				"Hier koennte dein Text stehen", "text");

		Assert.assertEquals(0, doc2.getIncomingLinks().size());
		doc2.addIncomingLink(this.doc0);
		Assert.assertEquals(1, doc2.getIncomingLinks().size());
		doc2.addIncomingLink(this.doc0);
		Assert.assertEquals(1, doc2.getIncomingLinks().size());
		doc2.addIncomingLink(this.doc1);
		Assert.assertEquals(2, doc2.getIncomingLinks().size());
		doc2.addIncomingLink(doc2);
		Assert.assertEquals(2, doc2.getIncomingLinks().size());
	}

	@Test
	public void testCrawl() {
		// Inital Dokument mit einigen Links
		LinkedDocument first = LinkedDocument.createLinkedDocumentFromFile("dateimaerchen");

		// Neue collection erstellen, doc hinzufügen und links suchen
		LinkedDocumentCollection col = new LinkedDocumentCollection();
		col.addLast(first);
		LinkedDocumentCollection crawled = col.crawl();

		// Die größe des gecrawlten arrays sollte genau Sieben sein. Acht geben
		// auf nicht hinzufügen bereits vorhandener
		// Dokumente und ein Abbruchkriterium in der rekursiven Funktion.
		Assert.assertEquals(7, crawled.size());
		// Dokumente aus der gesamtcollection holen und schauen ob auch diese
		// stimmen
		LinkedDocument grimm = (LinkedDocument) crawled.get(crawled.indexOf(new LinkedDocument("", "", "", null, null,
				"", "dateigrimm")));
		LinkedDocument ziege = (LinkedDocument) crawled.get(crawled.indexOf(new LinkedDocument("", "", "", null, null,
				"", "dateiziege")));
		Assert.assertEquals(3, first.getOutgoingLinks().size());
		// Stellt sicher, dass sich das doc nicht selbst bei outgoing drinnen
		// hat
		Assert.assertEquals(3, grimm.getOutgoingLinks().size());
		Assert.assertEquals(0, ziege.getOutgoingLinks().size());
	}

	@Test
	public void testCrawlIncomingLinks() {
		LinkedDocument first = LinkedDocument.createLinkedDocumentFromFile("dateimaerchen");
		LinkedDocumentCollection col = new LinkedDocumentCollection();
		col.addLast(first);
		LinkedDocumentCollection crawled = col.crawl();
		crawled.calculateIncomingLinks();

		LinkedDocument sieben = (LinkedDocument) crawled.get(crawled.indexOf(new LinkedDocument("", "", "", null, null,
				"", "dateisieben")));
		LinkedDocument grimm = (LinkedDocument) crawled.get(crawled.indexOf(new LinkedDocument("", "", "", null, null,
				"", "dateigrimm")));
		Assert.assertEquals(1, first.getIncomingLinks().size());
		Assert.assertEquals(1, grimm.getIncomingLinks().size());
		Assert.assertEquals(3, sieben.getIncomingLinks().size());
	}

	@Test
	public void testCrawlNoSelfReference() {
		LinkedDocument first = LinkedDocument.createLinkedDocumentFromFile("dateimaerchen");
		LinkedDocumentCollection col = new LinkedDocumentCollection();
		col.addLast(first);
		LinkedDocumentCollection crawled = col.crawl();
		crawled.calculateIncomingLinks();
		for (int i = 0; i < crawled.size(); i++) {
			LinkedDocumentCollection out = ((LinkedDocument) crawled.get(i)).getOutgoingLinks();
			for (int j = 0; j < out.size(); j++) {
				Assert.assertNotEquals(crawled.get(i), out.get(i));
			}
			LinkedDocumentCollection in = ((LinkedDocument) crawled.get(i)).getOutgoingLinks();
			for (int j = 0; j < out.size(); j++) {
				Assert.assertNotEquals(crawled.get(i), in.get(i));
			}
		}
	}

	@Test
	public void testCreateLinkedDocumentFromFile() {
		LinkedDocument doc2 = LinkedDocument.createLinkedDocumentFromFile("dateiwolf");

		doc2.equals(this.doc1);
		LinkedDocumentCollection out = doc2.getOutgoingLinks();
		Assert.assertEquals(2, out.size());

		Assert.assertEquals(0, doc2.getWordCounts().getCount(doc2.getWordCounts().getIndex("link:dateisieb")));
		Assert.assertEquals(1, doc2.getWordCounts().getCount(doc2.getWordCounts().getIndex("zick")));
		Assert.assertEquals(0, doc2.getWordCounts().getCount(doc2.getWordCounts().getIndex("link:dateiziege")));
	}

	@Test
	public void testGetID() {
		Assert.assertEquals("hans", this.doc0.getID());
		Assert.assertEquals("wolf", this.doc1.getID());
		Assert.assertNotEquals(this.doc0.getID(), this.doc1.getID());
	}

	@Test
	public void testGetOutgoingLinks() {
		LinkedDocument doc2 = new LinkedDocument("la", "la", "lu", null, this.testAuthor,
				"Hier link:dateisieben koennte dein Text link:dateitext stehen link:dateisieben ;)", "dateitext");
		// Schauen obs fehler gibt mit nicht existierenden Dateien
		LinkedDocumentCollection out0 = this.doc0.getOutgoingLinks();
		LinkedDocumentCollection out1 = this.doc1.getOutgoingLinks();
		LinkedDocumentCollection out2 = doc2.getOutgoingLinks();

		Assert.assertEquals(1, out0.size());
		Assert.assertEquals(2, out1.size());
		Assert.assertEquals(1, out2.size());
		Assert.assertNotEquals(out0, out1);
	}

	@Test
	public void testLinkedDocumentCollectionIntegrity() {
		LinkedDocumentCollection docs = new LinkedDocumentCollection();
		Document doc2 = new Document("Wolf", "de", "1s", new Date(31, 5, 1900), this.testAuthor,
				"es ist einmal eine alte geiss link:dateiziege gewesen die hatte sieben link:dateisieben junge zicklein");
		Assert.assertTrue(docs.isEmpty());

		docs.addFirst(this.doc0);
		Assert.assertFalse(docs.isEmpty());
		docs.addLast(this.doc0);
		Assert.assertEquals(1, docs.size());
		Assert.assertFalse(docs.isEmpty());
		docs.addLast(this.doc1);
		Assert.assertEquals(2, docs.size());
		docs.addFirst(this.doc1);
		Assert.assertEquals(2, docs.size());
		Assert.assertEquals(this.doc1, docs.getLast());

		docs.removeLast();
		docs.removeFirst();
		Assert.assertTrue(docs.isEmpty());

		docs.addFirst(doc2);
		docs.addLast(doc2);
		Assert.assertTrue(docs.isEmpty());
	}

	@Test
	public void testLinkedDocumentCollectionNullDoc() {
		LinkedDocumentCollection d = new LinkedDocumentCollection();
		d.addFirst(null);
		d.addFirst(null);
		d.addLast(null);
		d.addLast(null);

		// Soll nicht möglich sein
		Assert.assertEquals(0, d.size());
	}

	@Test
	public void testLinkedDocumentEquals() {
		Assert.assertTrue(this.doc0.equals(this.doc0));
		Assert.assertTrue(this.doc1.equals(this.doc1));
		Assert.assertFalse(this.doc0.equals(this.doc1));
		LinkedDocument doc3 = new LinkedDocument("la", "la", "lu", new Date(31, 5, 1900), this.testAuthor,
				"Hier koennte dein Text stehen", "wolf");
		Assert.assertTrue(this.doc1.equals(doc3));
	}

	@Test
	public void testMatch() {
		LinkedDocument doc2 = new LinkedDocument("1", "", "", new Date(), null, "abc def ghi jkl mno pqr", "id2");
		LinkedDocument doc3 = new LinkedDocument("2", "", "", new Date(), null, "abc jkl mno", "id3");
		LinkedDocument doc4 = new LinkedDocument("3", "", "", new Date(), null, "stu", "id4");
		LinkedDocument doc5 = new LinkedDocument("4", "", "", new Date(), null, "abc def ghi", "id5");
		LinkedDocumentCollection docs = new LinkedDocumentCollection();
		docs.addFirst(doc2);
		docs.addFirst(doc3);
		docs.addFirst(doc4);
		docs.addFirst(doc5);

		docs.match("stu");
		Assert.assertEquals(1.0, docs.getQuerySimilarity(0), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(1), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(2), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(3), this.delta);

		docs.match("vwx");
		Assert.assertEquals(0.0, docs.getQuerySimilarity(0), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(1), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(2), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(3), this.delta);

		docs.match("abc");

		// Counts müssen stimmen
		Assert.assertEquals(3, docs.noOfDocumentsContainingWord("abc"));
		Assert.assertEquals(4, docs.size());

		// Die Verhältnisse zueinander stimmen
		Assert.assertEquals(docs.getQuerySimilarity(0), docs.getQuerySimilarity(1), this.delta);
		Assert.assertNotEquals(docs.getQuerySimilarity(0), docs.getQuerySimilarity(2), this.delta);
		Assert.assertTrue(docs.getQuerySimilarity(0) > docs.getQuerySimilarity(2));

		// Der Submission-Server ist jetzt bis auf die letzte Nachkommastelle mit den Testwerten zufrieden ;)
		Assert.assertEquals(0.2525147628886298, docs.getQuerySimilarity(0), this.delta);
		Assert.assertEquals(0.2525147628886298, docs.getQuerySimilarity(1), this.delta);
		Assert.assertEquals(0.1415721168980825, docs.getQuerySimilarity(2), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(3), this.delta);

		docs.match("ghi");
		int index1 = docs.indexOf(doc5);
		int index2 = docs.indexOf(doc2);
		Assert.assertEquals(0, index1);
		Assert.assertEquals(1, index2);
		Assert.assertTrue(docs.getQuerySimilarity(index1) > docs.getQuerySimilarity(index2));
		Assert.assertEquals(docs.getQuerySimilarity(2), docs.getQuerySimilarity(3), this.delta);

		System.out.println(docs);
		// Der Submission-Server ist jetzt bis auf die letzte Nachkommastelle
		// mit den Testwerten zufrieden ;)
		Assert.assertEquals(0.47077169914246125, docs.getQuerySimilarity(0), this.delta);
		Assert.assertEquals(0.2483114078489643, docs.getQuerySimilarity(1), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(2), this.delta);
		Assert.assertEquals(0.0, docs.getQuerySimilarity(3), this.delta);
	}

	@Test
	public void testNoOfDocumentsContainingWord() {
		LinkedDocument doc2 = new LinkedDocument("1", "", "", new Date(), null, "abc def ghi jkl mno pqr", "id2");
		LinkedDocument doc3 = new LinkedDocument("2", "", "", new Date(), null, "abc jkl mno", "id3");
		Document doc4 = new Document("3", "", "", new Date(), null, "abc def ghi jkl mno pqr");
		LinkedDocument doc5 = new LinkedDocument("4", "", "", new Date(), null, "abc def ghi", "id4");
		LinkedDocumentCollection docs = new LinkedDocumentCollection();
		docs.addFirst(doc2);
		docs.addFirst(doc3);
		docs.addFirst(doc4); // Sollte nicht eingefügt werden
		docs.addFirst(doc5);
		Assert.assertEquals(3, docs.size());

		Assert.assertEquals(3, docs.noOfDocumentsContainingWord("abc"));
		Assert.assertEquals(1, docs.noOfDocumentsContainingWord("pqr"));
		Assert.assertEquals(2, docs.noOfDocumentsContainingWord("mno"));
		Assert.assertEquals(1, docs.noOfDocumentsContainingWord("pqr"));
		Assert.assertEquals(0, docs.noOfDocumentsContainingWord("stv"));

		docs.match("stv"); // Sollte nichts ändern
		// Wenn du hier einen Fehler erhälts, dann hat sich etwas an der
		// Collection geändert! Du musst die match methode
		// in der Unterklasse anpassen, sonst funktioniert das mit dem
		// Temporären Dokument nicht!
		Assert.assertEquals(3, docs.noOfDocumentsContainingWord("abc"));
		Assert.assertEquals(1, docs.noOfDocumentsContainingWord("pqr"));
		Assert.assertEquals(2, docs.noOfDocumentsContainingWord("mno"));
		Assert.assertEquals(1, docs.noOfDocumentsContainingWord("pqr"));
		Assert.assertEquals(0, docs.noOfDocumentsContainingWord("stv"));

	}

	@Test
	public void testSetLinkCountZero() {
		Assert.assertEquals(1, this.doc0.getWordCounts().getCount(this.doc0.getWordCounts().getIndex("hans")));
		Assert.assertEquals(0, this.doc0.getWordCounts().getCount(this.doc0.getWordCounts().getIndex("link:dateisieb")));

		Assert.assertEquals(0, this.doc1.getWordCounts().getCount(this.doc1.getWordCounts().getIndex("link:dateisieb")));
		Assert.assertEquals(1, this.doc1.getWordCounts().getCount(this.doc1.getWordCounts().getIndex("zick")));
		Assert.assertEquals(0, this.doc1.getWordCounts()
				.getCount(this.doc1.getWordCounts().getIndex("link:dateiziege")));
	}

	@Test
	public void testWordCount() {
		// Um Grundlagenfehler zu finden, an sowas denkt man nämlich nie
		WordCount w = new WordCount("test");
		Assert.assertEquals("test", w.getWord());
		w.setWeight(42.14);
		Assert.assertEquals(42.14, w.getWeight(), this.delta);
		w.setNormalizedWeight(21.24124);
		Assert.assertEquals(21.24124, w.getNormalizedWeight(), this.delta);
	}
}