
public class LinkedDocumentCollection extends DocumentCollection {
	//Attributes
	
	
	//Constructors
	public LinkedDocumentCollection()	{
		super();
	}
	
	//Methods
	public void addFirst(Document doc)	{
		if (doc == null) {
			return;
		}
		if (!(doc instanceof LinkedDocument))	{
			return;
		}
		if (this.containsID(((LinkedDocument)doc).getID()))	{
			return;
		}
		
		if (this.isEmpty()) {
			this.first = new DocumentCollectionCell(doc, null);
		}
		else {
			this.first = new DocumentCollectionCell(doc, first);
		}
		
		size++;
	}
	
	public void addLast(Document doc)	{
		if (doc == null) {
			return;
		}
		if (!(doc instanceof LinkedDocument))	{
			return;
		}
		if (this.containsID(((LinkedDocument)doc).getID()))	{
			return;
		}
		
		if (this.isEmpty()) {
			/* list empty, add as only element */
			this.first = new DocumentCollectionCell(doc, null);
			this.last = first;		
		}
		else {
			last.setNext(new DocumentCollectionCell(doc, null));
			last = last.getNext();
		}
		
		size++;
	}
	
	public boolean containsID(String id)	{
		DocumentCollectionCell tmp = this.first;
		
		for (int i = 0; i < this.size(); i++)	{
			while (tmp != null)	{
				if (((LinkedDocument)tmp.getDocument()).getID().equals(id))	{
					return true;
				}
				tmp = tmp.getNext();
				
			}
		}
		
		return false;
	}
	
	public void calculateIncomingLinks()	{
		if (this.size() < 2)	{
			return;
		}
		
		DocumentCollectionCell tmp = this.first;
		
		while (tmp != null)	{
			//For each Document Listed
			DocumentCollectionCell help = ((LinkedDocument)tmp.getDocument()).getOutgoingLinks().first;
			
			for (int i = 0; i < ((LinkedDocument)tmp.getDocument()).getOutgoingLinks().size(); i++)	{
				((LinkedDocument)help.getDocument()).addIncomingLink((LinkedDocument)tmp.getDocument());
				help = help.getNext();
			}
			
			tmp = tmp.getNext();
		}
	}
	
	public LinkedDocumentCollection crawl()	{
		LinkedDocumentCollection resultCollection = new LinkedDocumentCollection();
		
		this.crawl(resultCollection);
		
		return resultCollection;
	}
	
	private void crawl(LinkedDocumentCollection resultCollection)	{
		
		if (resultCollection.isEmpty())	{
			resultCollection = this;
		}
		
		DocumentCollectionCell tmp = resultCollection.first;
		
		while (tmp != null)	{
			if (resultCollection.containsID(((LinkedDocument)tmp.getDocument()).getID()))	{
				resultCollection.addLast(tmp.getDocument());
			}
			
			resultCollection.crawl(((LinkedDocument)tmp.getDocument()).getOutgoingLinks());
			
			tmp = tmp.getNext();
		}
	}
}
