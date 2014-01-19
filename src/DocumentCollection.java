
public class DocumentCollection {
	private DocumentCollectionCell first;
	private DocumentCollectionCell last;
	private int size;
	
	public DocumentCollection() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	public void addFirst(Document doc) {
		if (doc == null) {
			return;
		}
		
		if (this.isEmpty()) {
			/* list empty, add as one and only element */
			this.first = new DocumentCollectionCell(doc, null);
			this.last = first;
		}
		else {
			this.first = new DocumentCollectionCell(doc, first);
		}
		
		size++;
	}
	
	public void addLast(Document doc) {
		if (doc == null) {
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
	
	public int indexOf(Document doc) {
		if (doc == null || this.isEmpty()) {
			return -1;
		}
		
		/* loop over list and find document */
		
		DocumentCollectionCell tmp = this.first;
		int index = 0;
		
		while (tmp != null) {
			if (tmp.getDocument().equals(doc)) {
				return index; 
			}
			
			tmp = tmp.getNext();
			index++;
		}
		
		return -1;
	}
	
	public boolean contains(Document doc) {
		return (this.indexOf(doc) != -1);
	}
	
	public boolean remove(int index) {
		if (index < 0 || index >= this.size()) {
			return false;
		}
		
		if (this.isEmpty()) {
			return false;
		}
		
		/* remove first */
		if (index == 0) {
			this.removeFirst();
			return true;
		}
		
		/* remove last */
		if (index == this.size() - 1) {
			this.removeLast();
			return true;
		}
		
		/* we will only get here, if index >= 1 and size >= 2 */
		
		/* loop to index, keep track of previous */
		DocumentCollectionCell actual = this.first.getNext();
		DocumentCollectionCell prev = this.first;
		int i = 1;
		
		while (i < index) {
			prev = actual;
			actual = actual.getNext();
			i++;
		}
		
		/* delete actual */
		prev.setNext(actual.getNext());
		size--;
		return true;
	}	
	
	public void removeLast() {
		if (this.isEmpty()) {
			return;
		}
		
		/* one element: clear list and return */ 
		if (this.size() == 1) {
			this.clear();
			return;
		}
		
		/* navigate to the element before last */		
		DocumentCollectionCell newLast = this.first;		
		while (newLast.getNext() != this.last) {
			newLast = newLast.getNext();
		}
		
		/* assign new last element */
		newLast.setNext(null);				
		this.last = newLast;
		size--;
	}
	
	public void removeFirst() {
		if (this.isEmpty()) {
			return;
		}
		
		/* one element: clear list and return */ 
		if (this.size() == 1) {
			this.clear();
			return;
		}
		
		/* remove first element */
		this.first = this.first.getNext();
		size--;
	}
	
	public Document getFirst() {
		if (this.isEmpty()) {
			return null;
		}
		
		return this.first.getDocument();
	}
	
	public Document getLast() {
		if (this.isEmpty()) {
			return null;
		}
		
		return this.last.getDocument();
	}
	
	private void clear() {
		this.first = null;
		this.last = null;
		this.size = 0;
	}
	
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	public int size() {
		return this.size;
	}
	
	public Document get(int index) {
		if (index < 0 || index >= this.size) {
			return null;
		}
		
		return getDocumentCollectionCell(index).getDocument();		
	}
	
	private WordCountArray allWords() {		
		/* loop over all documents to create a WordCountArray
		   containing *all* words of all documents */ 		 
		DocumentCollectionCell tmp = this.first;
		
		WordCountArray allWords = new WordCountArray(0);
		
		while (tmp != null) {
			Document doc = tmp.getDocument();
			WordCountArray wca = doc.getWordCounts();
					
			for (int i = 0; i < wca.size(); i++) {
				allWords.add(wca.getWord(i), 0);
			}
			
			tmp = tmp.getNext();
		}
		
		return allWords;
	}
	
	public void match(String query) {
		if (this.isEmpty()) {
			return;
		}
		
		if (query == null || query.equals("")) {
			return;
		}
		
		/* add query to collection as document;
		 * changed with Uebung 4: Must be a LinkedDocument, since Documents are not added to a LinkedDocumentCollection
		 */
		LinkedDocument queryDocument = new LinkedDocument("", "", "", null, null, query, "");
		this.addFirst(queryDocument);
		
		
		/* add every word to every document with count 0 */
		this.addWordsToDocumentsWithCountZero();
		
		
		/* sort all WordCountArrays of all documents */				
		DocumentCollectionCell tmp = this.first;		
		while (tmp != null) {
			tmp.getDocument().getWordCounts().sort();
			tmp = tmp.getNext();
		}
		
		
		/* calculate similarities with query document */
		tmp = this.first.getNext();
		while (tmp != null) {
			/* added parameter "this" with Uebung 4 to calculate complex similarity */ 
			tmp.setQuerySimilarity(tmp.getDocument().getWordCounts().similarity(queryDocument.getWordCounts(), this));
			tmp = tmp.getNext();
		}
		
		/* remove the query we added in the beginning */
		this.removeFirst();
		
		this.sortBySimilarity();
	}

	private void swap(DocumentCollectionCell cell1, DocumentCollectionCell cell2) {
		/* swap both contained the contained document and the corresponding similarity */
		
		Document tmpDoc = cell1.getDocument();
		double tmpSim = cell1.getQuerySimilarity();
		
		cell1.setDocument(cell2.getDocument());
		cell1.setQuerySimilarity(cell2.getQuerySimilarity());
		
		cell2.setDocument(tmpDoc);
		cell2.setQuerySimilarity(tmpSim);
	}
	
	private void sortBySimilarity() {
		for (int pass = 1; pass < this.size(); pass++) {
			
			/* variable to loop over array */
			DocumentCollectionCell actCell = this.first;
			
			for (int i = 0; i < this.size() - pass; i++) {			
				/* swap content of cells, if cells are in wrong order */
				if (actCell.getQuerySimilarity() < actCell.getNext().getQuerySimilarity()) {
					swap(actCell, actCell.getNext());
				}
				
				actCell = actCell.getNext();
			}
		}
	}
	
	private void addWordsToDocumentsWithCountZero() {
		WordCountArray allWords = this.allWords();
		
		DocumentCollectionCell tmp = this.first;
		
		while (tmp != null) {
			for (int j = 0; j < allWords.size(); j++) {
				String word = allWords.getWord(j);
				
				tmp.getDocument().getWordCounts().add(word, 0);
			}
			
			tmp = tmp.getNext();
		}	
	}
	
	public double getQuerySimilarity(int index) {
		if (index < 0 || index >= this.size()) {
			return -1;
		}
		
		return this.getDocumentCollectionCell(index).getQuerySimilarity();
	}
	
	private DocumentCollectionCell getDocumentCollectionCell(int index) {
		if (index < 0 || index >= this.size()) {
			return null;
		}
		
		/* navigate to corresponding cell at the index */
		
		DocumentCollectionCell tmp = this.first;		
		
		int i = 0;
		while (i < index) {
			tmp = tmp.getNext();
			i++;
		}
		
		return tmp;
	}
	
	@Override
	public String toString() {
		if (this.size() == 0) {
			return "[]";
		}

		if (this.size() == 1) {
			return "[" + this.get(0).getTitle() + "]";
		}

		String res = "[";
		for (int i = 0; i < this.size() - 1; i++) {
			res += this.get(i).getTitle() + ", ";
		}
		res += this.get(this.size() - 1).getTitle() + "]";
		return res;
	}
	
	public int noOfDocumentsContainingWord(String word) {
		if (word == null) {
			return 0;
		}
		
		
		int count = 0;
		
		/* loop over all documents and check if word is contained */
		for (int i = 0; i < this.size(); i++) {
			WordCountArray wca = this.get(i).getWordCounts();	
			
			if (wca != null) {			
				int index = wca.getIndex(word);
				
				/* increase count only if count of this word in the WordCountArray is greater than 0 */
				if (index != -1 && wca.getCount(index) > 0) {
					count++;
				}
			}
		}
		
		return count;
	}
}
