
public class LinkedDocument extends Document {
	//Attributes
	private String id;
	
	//Constructors
	public LinkedDocument(String title, String language, String description,
			Date releaseDate, Author author, String text) {
		//TODO
		super(title, language, description, releaseDate, author, text);
		this.setId(id);
	}

	//Methods
	public void setId(String id)	{
		this.id = id;
	}
	
	public boolean equals(Document doc)	{
		//TODO
	}
	
	private String[] findOutgoingIDs(String text)	{
		//TODO
	}
	
	private void setLinkCountZero()	{
		//TODO
	}
	
	public void addIncomingLink(LinkedDocument incomingLink)	{
		//TODO
	}
	
	public static LinkedDocument createLinkedDocumentFromFile(String fileName)	{
		//TODO
	}
	
	private void createOutgoingDocumentCollection()	{
		//TODO
	}
	
	public LinkedDocumentCollection getOutgoingLinks()	{
		//TODO
	}
	
	public LinkedDocumentCollection getIncomingLinks()	{
		//TODO
	}
}
