
public class LinkedDocument extends Document {
	public static final String LINK_PREFIX = "link:";
	
	private final String id;
	private LinkedDocumentCollection outgoingLinks;
	private LinkedDocumentCollection incomingLinks;
	private String[] outgoingIDs;
	
	public LinkedDocument(String title, String language, String description,
			Date releaseDate, Author author, String text, String id) {
		super(title, language, description, releaseDate, author, text);
		
		this.id = id;
		this.incomingLinks = new LinkedDocumentCollection();

		this.outgoingIDs = this.findOutgoingIDs(text);
		this.outgoingLinks = null;
		this.setLinkCountZero();
	}
	
	public static LinkedDocument createLinkedDocumentFromFile(String fileName) {
		String[] fileContent = Terminal.readFile(fileName);
		
		if (fileContent != null && fileContent.length >= 2) {		
			String title = fileContent[0]; 
			String text = fileContent[1];

			return new LinkedDocument(title, "", "", null, null, text, fileName);
		}
		else {
			return null;
		}
	}

	private void setLinkCountZero() {
		WordCountArray wca = this.getWordCounts();
		
		for (int i = 0; i < wca.size(); i++) {
			if (wca.getWord(i).startsWith(LinkedDocument.LINK_PREFIX)) {
				wca.setCount(i, 0);
			}
		}
	}
	
	@Override
	public boolean equals(Document doc) {
		if (doc instanceof LinkedDocument) { 
			return this.id.equals(((LinkedDocument) doc).id);
		}
		else {
			return super.equals(doc);
		}
	}
	
	public String getID() {
		return this.id;
	}
	
	private void createOutgoingDocumentCollection() {
		this.outgoingLinks = new LinkedDocumentCollection();
		
		for (int i = 0; i < this.outgoingIDs.length; i++) {
			LinkedDocument newDoc = LinkedDocument.createLinkedDocumentFromFile(this.outgoingIDs[i]);
			
			/* do not add links to this page (page pointing to itself) */
			if (!this.equals(newDoc)) {
				this.outgoingLinks.addLast(newDoc);
			}
		}
	}
	
	public LinkedDocumentCollection getOutgoingLinks() {
		if (this.outgoingLinks == null) {
			this.createOutgoingDocumentCollection();
		}
		
		return this.outgoingLinks;
	}
	
	private String[] findOutgoingIDs(String text) {
		if (text == null) {
			return null;
		}
		
		String textCopy = new String(text);
		
		/* the number of words in the WordCountArray is sufficient */
		String[] tmpIDs = new String[this.getWordCounts().size()];
		
		/* get the next index of the LINK_PREFIX */
		int index = textCopy.indexOf(LinkedDocument.LINK_PREFIX);
		
		int noOfIDs = 0;
		
		/* loop ends, when index is not found */
		while (index != -1) {
			/* divide the text at the found index */
			String strBeforeLink = textCopy.substring(0, index);
			String strWithLink = textCopy.substring(index);

			int endIndex = strWithLink.indexOf(' ');
			
			String link;
			
			/* if endIndex is not found, we are looking at the last word */
			if (endIndex == -1) {
				link = strWithLink.substring(0);
				textCopy = strBeforeLink;
			}
			else {
				/* otherwise, there are more words */
				link = strWithLink.substring(0, endIndex);				
				textCopy = strBeforeLink + strWithLink.substring(endIndex + 1);
			}			
			
			/* add the ID to array */
			tmpIDs[noOfIDs] = link.substring(LinkedDocument.LINK_PREFIX.length());
			noOfIDs++;
			
			/* get next index for next loop */
			index = textCopy.indexOf(LinkedDocument.LINK_PREFIX);
		}
		
		
		/* create new array with the size according to the actual number of found IDs */
		String[] ids = new String[noOfIDs];
		
		for (int i = 0; i < noOfIDs; i++) {
			ids[i] = tmpIDs[i];
		}
		
		return ids;
	}

	public void addIncomingLink(LinkedDocument incomingLink) {
		/* do not allow link on itself */
		if (!this.equals(incomingLink)) {
			/* addLast() will do the check, if document is already contained */
			this.incomingLinks.addLast(incomingLink);
		}
	}
	
	public LinkedDocumentCollection getIncomingLinks() {
		return this.incomingLinks;
	}
}
