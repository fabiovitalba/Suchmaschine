
public class LinkedDocument extends Document {
	//Attributes
	private String id;
	private String[] links;
	private LinkedDocumentCollection outgoingLinks;
	private LinkedDocumentCollection incomingLinks;
	
	
	
	//Constructors
	public LinkedDocument(String title, String language, String description,
			Date releaseDate, Author author, String text) {
		//TODO
		super(title, language, description, releaseDate, author, text);
		this.setId(id);
		this.links = findOutgoingIDs(text);
		this.setLinkCountZero();
	}

	//Methods
	public void setId(String id)	{
		this.id = id;
	}
	
	public String getId()	{
		return this.id;
	}
	
	public boolean equals(Document doc)	{
		if (doc instanceof LinkedDocument)	{
			return this.id.equals(((LinkedDocument)doc).getId());
		}
		return super.equals(doc);
	}
	
	private String[] findOutgoingIDs(String text)	{
		Document texts = new Document("", "", "", null, null, text);
		int linkSize = 1;
		String[] links = new String[linkSize];
		String tmpWord;
		
		for (int i = 0; i < texts.getWordCounts().size(); i++)	{
			tmpWord = texts.getWordCounts().getWord(i);
			if (tmpWord.contains("link:"))	{
				links[linkSize - 1] = tmpWord.substring(5, (tmpWord.length() - 1));
				linkSize++;
				String[] tmp = links;
				links = new String[linkSize];
				for (int j = 0; j < (linkSize - 1); j++)	{
					links[j] = tmp[j];
				}
			}
		}
		
		return links;
	}
	
	private void setLinkCountZero()	{
		for (int i = 0; i < this.getWordCounts().size(); i++)	{
			if (this.getWordCounts().getWord(i).contains("link:"))	{
				this.getWordCounts().setCount(i, 0);
			}
		}
	}
	
	public void addIncomingLink(LinkedDocument incomingLink)	{
		if (incomingLink == null)	{
			return;
		}
		if (incomingLink.equals(this))	{
			return;
		}
		this.incomingLinks.addLast(incomingLink);
	}
	
	public static LinkedDocument createLinkedDocumentFromFile(String fileName)	{
		return null;
	}
	
	private void createOutgoingDocumentCollection()	{
		//TODO
	}
	
	public LinkedDocumentCollection getOutgoingLinks()	{
		return null;
	}
	
	public LinkedDocumentCollection getIncomingLinks()	{
		return null;
	}
}
