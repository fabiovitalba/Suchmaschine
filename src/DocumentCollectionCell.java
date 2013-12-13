
public class DocumentCollectionCell {
	//Attributes
	private Document document;
	private DocumentCollectionCell next;
	private double querySimilarity;
	
	//Constructors
	public DocumentCollectionCell(Document document, DocumentCollectionCell next) {
		this.document = document;
		this.next = next;
		this.querySimilarity = 0;
	}

	//Methods
	public DocumentCollectionCell getNext() {
		return next;
	}

	public void setNext(DocumentCollectionCell next) {
		this.next = next;
	}

	public double getQuerySimilarity() {
		return querySimilarity;
	}

	public void setQuerySimilarity(double querySimilarity) {
		this.querySimilarity = querySimilarity;
	}

	public Document getDocument() {
		return document;
	}

	public Document setDocument(Document document) {
		Document oldDocument = this.document;		
		this.document = document;		
		return oldDocument;
	}
}