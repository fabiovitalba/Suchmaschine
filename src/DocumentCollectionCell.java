
public class DocumentCollectionCell {
	//Attributes
	private DocumentCollectionCell next;
	private Document document;
	private double similarity;
	
	//Constructors
	public DocumentCollectionCell()	{
		this.setDocument(null);
		this.setNext(null);
	}
	
	public DocumentCollectionCell(Document document)	{
		this.setDocument(document);
		//If the Next DocumentCollectionCell in the List is null, we are at the last item of the list.
		this.setNext(null);	
	}
	
	//Methods
	public DocumentCollectionCell getNext() {
		return next;
	}

	public void setNext(DocumentCollectionCell next) {
		this.next = next;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		if (similarity < -1)	{
			this.similarity = 0;
		}
		this.similarity = similarity;
	}
}
