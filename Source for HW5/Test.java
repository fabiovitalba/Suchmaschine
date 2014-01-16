/**
 * Test-Klasse.
 */
public class Test {
	/**
	 * Main-Methode
	 * 
	 * @param args
	 *            Kommandozeilen-Argumente
	 */
	public static void main(String[] args) {		
		
		LinkedDocumentCollection ldc = new LinkedDocumentCollection();
		String command;
		
		boolean exit = false;
		
		while (!exit) {
			command = Terminal.askString("> ");			
			
			if (command == null || command.equals("exit")) {
				/* Exit program */
				exit = true;
			}
			else if (command.startsWith("add ")) {
				/* add a new document */
				String titleAndText = command.substring(4);
				
				/* title and text separated by : */
				int separator = titleAndText.indexOf(':');				
				String title = titleAndText.substring(0, separator);
				String text = titleAndText.substring(separator + 1);
				
				ldc.addLast(new LinkedDocument(title, "", "", null, null, text, title));
			}
			else if (command.startsWith("list")) {
				/* list all document in collection */
				for (int i = 0; i < ldc.size(); i++) {
					System.out.println(ldc.get(i).getTitle());
				}
			}
			else if (command.startsWith("query ")) {
				/* query on the documents in the collection */
				String query = command.substring(6);

				ldc.match(query);				
				
				for (int i = 0; i < ldc.size(); i++) {
					System.out.println((i + 1) + ". " + ldc.get(i).getTitle() + "; Aehnlichkeit: " 
								+ ldc.getQuerySimilarity(i));
				}				
				
				System.out.println();
			}
			else if (command.startsWith("count ")) {
				/* print the count of a word in each document */
				String word = command.substring(6);
				
				for (int i = 0; i < ldc.size(); i++) {
					Document doc = ldc.get(i);
					WordCountArray docWordCounts = doc.getWordCounts();
										
					int count = docWordCounts.getCount(docWordCounts.getIndex(word));
					
					/* -1 and 0 makes a difference! */
					if (count == -1) {
						System.out.println(doc.getTitle() + ": gar nicht.");
					}
					else {
						System.out.println(doc.getTitle() + ": " + count + "x ");
					}
				}
			}
			else if (command.startsWith("crawl")) {
				ldc = ldc.crawl();
			}
		}
	}
}
