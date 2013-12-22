
public class LinkedDocumentCollection extends DocumentCollection {
	//Attributes
	
	
	//Constructors
	
	
	//Methods
	public void addFirst(Document doc)	{
		//TODO
		if (doc == null) {
			return;
		}
		if (!(doc instanceof LinkedDocument))	{
			return;
		}
		if (this.containsID(((LinkedDocument)doc).getId()))	{
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
		//TODO
		if (doc == null) {
			return;
		}
		if (!(doc instanceof LinkedDocument))	{
			return;
		}
		if (this.containsID(((LinkedDocument)doc).getId()))	{
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
				if (((LinkedDocument)tmp.getDocument()).getId().equals(id))	{
					return true;
				}
				tmp = tmp.getNext();
				
			}
		}
		
		return false;
	}
	
	public void calculateIncomingLinks()	{
		//TODO
	}
	
	public LinkedDocumentCollection crawl()	{
		return null;
	}
	
	private void crawl(LinkedDocumentCollection resultCollection)	{
		//TODO
	}
}
