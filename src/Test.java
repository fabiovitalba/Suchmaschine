
public class Test {

	public static void main(String[] args) {
		Author aut1 = new Author("Karl", "Olsberg", new Date(2, 9, 1960), "Hamburg", "karl@olsberg.com");
		Author aut2 = new Author("Christie", "Golden", new Date(21, 11, 1963), "Atlanta", "christie.golden@gmail.com");
		Author aut3 = new Author("Fabio", "Vitalba", new Date(9, 8, 1993), "St. Peter", "fabio.vitalba@tum.de");
		
		Document doc1 = new Document("Delete: Triller", "Deutsch", "Ein Sci-Fi Thriller.", new Date(15, 10, 2013), aut1);
		Document doc2 = new Document("Wuerfelwelt: Ein Minecraft Roman", "Deutsch", "Ein Gaming Roman.", new Date(28, 6, 2013), aut1);
		Document doc3 = new Document("Die Vorgeschichte zum Cataclysm", "Deutsch", "Ein Gaming Roman zu World of Warcraft.", new Date(19, 7, 2011), aut2);
		
		Review rev1 = new Review(doc1, aut3, "English", new Date(2, 11, 2013), 7);
		Review rev2 = new Review(doc3, aut3, "English", new Date(22, 10, 2013), 9);
		Review rev3 = new Review(doc3, aut1, "Deutsch", new Date(3, 11, 2013), 5);
		
		System.out.println("Liste der Autoren mit toString()-Methode:");
		System.out.println(aut1.toString());
		System.out.println(aut2.toString());
		System.out.println();
		System.out.println("Liste der Kontaktdaten der Autoren:");
		System.out.println(aut1.getContactInformation());
		System.out.println(aut2.getContactInformation());
		System.out.println();
		System.out.println("Liste der Dokumente mit toString()-Methode:");
		System.out.println(doc1.toString());
		System.out.println(doc2.toString());
		System.out.println(doc3.toString());
		System.out.println();
		System.out.println("Liste der Rezensionen mit toString()-Methode:");
		rev1.toString();
		rev2.toString();
		rev3.toString();
		System.out.println();
		System.out.println("Alter von '" + doc1.getTitle() + "': " + doc1.getAge(new Date()) + " Tage alt.");
		System.out.println("Alter von '" + doc2.getTitle() + "': " + doc2.getAge(new Date()) + " Tage alt.");
		System.out.println("Alter von '" + doc3.getTitle() + "': " + doc3.getAge(new Date()) + " Tage alt.");
		System.out.println("Alter von '" + aut1.getFirstName() + " " + aut1.getLastName() + "': " + aut1.getAge(new Date()) + " Jahre alt.");
		System.out.println("Alter von Rezension zu '" + rev1.getReviewedDocument().getTitle() + "' von " + rev1.getAuthor().getFirstName() + ": " + rev1.getAge(new Date()) + " Tage alt.");
	}

}
