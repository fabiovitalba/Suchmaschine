
public class DocumentCollection {
	//Attributes
	private DocumentCollectionCell first;
	
	//Constructors
	public DocumentCollection()	{
		this.setFirst(null);
	}
	
	public DocumentCollection(DocumentCollectionCell dcc)	{
		this.setFirst(dcc);
	}
	
	//Methods
	public Document get(int index)	{
		if (index >= this.size())	{
			return null;
		}
		if (index < 0)	{
			return null;
		}
		if (this.isEmpty())	{
			return null;
		}
		
		DocumentCollectionCell temp = this.first;
		for (int i = 0; i < index; i++)	{
			temp = temp.getNext();
		}
		return temp.getDocument();
	}
	
	public Document getFirst()	{
		return get(0);
	}
	
	public Document getLast()	{
		return get(size() - 1);
	}
	
	public void setFirst(DocumentCollectionCell first) {
		this.first = first;
	}
	
	public void addFirst(Document doc)	{
		if (doc == null)	{
			return;
		}
		//Shortest method: first = new DocumentCollectionCell(doc, first);
		if (!this.isEmpty())	{
			DocumentCollectionCell temp = this.first;
			this.setFirst(new DocumentCollectionCell(doc));
			this.first.setNext(temp);
		}	else	{
			this.setFirst(new DocumentCollectionCell(doc));
		}
	}

	public void addLast(Document doc)	{
		if (doc == null)	{
			return;
		}
		
		if (!this.isEmpty())	{
			DocumentCollectionCell last = this.first;
			while (last.getNext() != null)	{
				last = last.getNext();
			}
			last.setNext(new DocumentCollectionCell(doc));
		}	else	{
			this.setFirst(new DocumentCollectionCell(doc));
		}
	}
	
	public int size()	{
		int length = 0;
		
		if (isEmpty())	{
			return 0;
		}
		
		DocumentCollectionCell last = this.first;
		
		while (last.getNext() != null)	{
			if (last.getDocument() != null)	{
				length++;
			}
			last = last.getNext();
		}
		
		return length + 1;
	}
	
	public boolean isEmpty()	{
		if (this.first == null)	{
			return true;
		}	
		if (this.first.getDocument() == null)	{
			return true;
		}
		return false;
		
	}
	
	public boolean remove(int index)	{
		if (index >= size())	{
			return false;
		}
		if (index < 0)	{
			return false;
		}
		if (isEmpty())	{
			return false;
		}
		
		DocumentCollectionCell prev = this.first;
		DocumentCollectionCell temp = this.first;
		
		for (int i = 0; i < index; i++)	{
			if (temp != this.first)	{
				prev = prev.getNext();
			}
			temp = temp.getNext();
		}
		
		if (temp.getNext() == null)	{
			if (index == 0)	{
				this.first = null;
			}	else	{
				prev.setNext(null);
			}
		}	else if (temp.getNext().getNext() == null)	{
			temp.setNext(null);
		}	else	{
			temp.setNext(temp.getNext().getNext());
		}
		
		return true;
	}
	
	public void removeFirst()	{
		this.remove(0);
	}
	
	public void removeLast()	{
		this.remove(size() - 1);
	}
	
	public boolean contains(Document doc)	{
		if (doc == null)	{
			return false;
		}
		
		DocumentCollectionCell temp = this.first;
		
		while (temp != null)	{
			if (temp.getDocument().equals(doc))	{
				return true;
			}
			temp = temp.getNext();
		}
		
		return false;
	}
	
	public int indexOf(Document doc)	{
		if (doc == null)	{
			return -1;
		}
		if (!this.contains(doc))	{
			return -1;
		}
		
		DocumentCollectionCell temp = this.first;
		int index = 0;
		
		do	{
			if (temp.getDocument().equals(doc))	{
				return index;
			}
			index++;
			temp = temp.getNext();
			
		}	while(temp.getNext() != null);
		
		return -1;
	}
	
	private WordCountArray allWords()	{
		WordCountArray wca = new WordCountArray(2);
		
		if (isEmpty())	{
			return null;
		}
		
		DocumentCollectionCell temp = this.first;
		while (temp != null)	{
			for (int i = 0; i < temp.getDocument().getWordCounts().size(); i++)	{
				if (wca.getIndex(temp.getDocument().getWordCounts().getWord(i)) < 0)	{
					wca.add(temp.getDocument().getWordCounts().getWord(i), 0);
				}
			}
			
			temp = temp.getNext();
		}
		
		return wca;
	}
	
	private void addWordsToDocumentsWithCountZero()	{
		WordCountArray allWords = this.allWords();
		
		if (allWords == null)	{
			return;
		}
		
		DocumentCollectionCell temp = this.first;
		while (temp != null)	{
			for (int i = 0; i < allWords.size(); i++)	{
				if (temp.getDocument().getWordCounts().getIndex(allWords.getWord(i)) < 0)	{
					temp.getDocument().getWordCounts().add(allWords.getWord(i), 0);
				}
			}
			
			temp = temp.getNext();
		}
	}
	
	public void match(String query)	{
		Document docQuery = new Document(null, null, null, null, null, query);
		
		this.addLast(docQuery);
		
		this.addWordsToDocumentsWithCountZero();
		
		DocumentCollectionCell temp = this.first;
		while (temp != null)	{
			temp.setSimilarity(docQuery.getWordCounts().similarity(temp.getDocument().getWordCounts()));
			temp = temp.getNext();
		}
		
		this.removeLast();
		this.sortBySimilarity();
	}
	
	public double getQuerySimilarity(int index)	{
		if (index >= this.size())	{
			return -1;
		}
		if (index < 0)	{
			return -1;
		}
		
		DocumentCollectionCell temp = this.first;
		
		for (int i = 0; i < (index - 1); i++)	{
			temp = temp.getNext();
		}
		
		return temp.getSimilarity();
	}
	
	private void sortBySimilarity()	{
		if (isEmpty() || this.size() < 2)	{
			return;
		}
		
		DocumentCollectionCell prev = this.first;
		DocumentCollectionCell center = this.first;
		DocumentCollectionCell post = this.first.getNext();
		
		DocumentCollectionCell help1 = null;
		DocumentCollectionCell help2 = null;
		
		for (int i = 0; i < this.size(); i++)	{
			do	{
				if (center.getSimilarity() < post.getSimilarity())	{
					help1 = center;
					help2 = post.getNext();
					
					prev.setNext(center.getNext());
					post.setNext(help1);
					center.setNext(help2);
				}
				if (center != this.first)	{
					prev = prev.getNext();
				}
				center = center.getNext();
				post = post.getNext();
			}	while(post != null);
			
			center = this.first;
			prev = this.first;
			post = this.first.getNext();
		}
	}
}
