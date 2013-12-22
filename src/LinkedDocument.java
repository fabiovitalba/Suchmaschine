
public class LinkedDocument extends Document {
	//Attributes
	private String id;
	private String[] links;
	private LinkedDocumentCollection outgoingLinks;
	private LinkedDocumentCollection incomingLinks;
	
	//Constructors
	public LinkedDocument(String title, String language, String description,
			Date releaseDate, Author author, String text, String id) {
		//TODO
		super(title, language, description, releaseDate, author, text);
		this.setId(id);
		
		this.outgoingLinks = new LinkedDocumentCollection();
		this.incomingLinks = new LinkedDocumentCollection();
		
		this.links = findOutgoingIDs(text);
		this.setLinkCountZero();
	}

	//Methods
	public void setId(String id)	{
		this.id = id;
	}
	
	public String getID()	{
		return this.id;
	}
	
	public boolean equals(Document doc)	{
		if (doc instanceof LinkedDocument)	{
			return this.id.equals(((LinkedDocument)doc).getID());
		}
		return super.equals(doc);
	}
	
	private String[] findOutgoingIDs(String text)	{
		String[] texts = tokenize(text);
		int linkSize = 1;
		String[] links = new String[linkSize];
		
		for (int i = 0; i < texts.length; i++)	{
			if (texts[i].contains("link:"))	{
				links[linkSize - 1] = texts[i].substring(5, texts[i].length());
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
		if (fileName == null)	{
			return null;
		}
		
		String[] file = Terminal.readFile(fileName);
		
		if ((file == null) || (file.length < 2))	{
			return null;
		}
		
		LinkedDocument ld = new LinkedDocument(file[0], "", "", null, null, file[1], fileName);
		
		return ld;
	}
	
	private void createOutgoingDocumentCollection()	{
		if (this.links.length < 1)	{
			return;
		}
		
		for (int i = 0; i < this.links.length; i++)	{
			if (!this.getID().equals(this.links[i]))	{
				this.outgoingLinks.addLast(createLinkedDocumentFromFile(this.links[i]));
			}
		}
	}
	
	public LinkedDocumentCollection getOutgoingLinks()	{
		this.createOutgoingDocumentCollection();
		
		if (this.outgoingLinks.isEmpty())	{
			return null;
		}	else	{
			return this.outgoingLinks;
		}
	}
	
	public LinkedDocumentCollection getIncomingLinks()	{
		return this.incomingLinks;
	}
}
