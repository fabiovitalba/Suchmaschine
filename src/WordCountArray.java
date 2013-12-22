
public class WordCountArray {
	//Attributes
	private WordCount[] wordCounts;
	private int actualSize;
	private int maxSize;
	
	//Constructors
	public WordCountArray(int maxSize) {		
		if (maxSize < 0) {
			this.maxSize = 0;
		} else {
			this.maxSize = maxSize;
		}
		
		this.actualSize = 0;
		this.wordCounts = new WordCount[this.maxSize];
	}
	
	//Methods
	public void add(String word, int count) {
		if (word == null || word.equals("")) {
			return;
		}
		
		if (count < 0) {
			return;
		}
		
		/* get the index, if the word is already administered */
		int index = getIndex(word.toLowerCase());
		
		/* word found? */
		if (index == -1) {
			/* the word has not been found, so create a new
			   WordCount instance and add it */
			
			/* if we have reached the end of the array,
			   increase the array size */
			if (actualSize == maxSize) {
				this.doubleSize();
			}		
			
			this.wordCounts[actualSize] = new WordCount(word.toLowerCase(), count);
			this.actualSize++;
		}
		else {
			/* the word has been found and therefore it
			   is already administered, so add the count */
			this.wordCounts[index].incrementCount(count);
		}
	}
	
	public int size() {
		return this.actualSize;
	}

	public String getWord(int index) {
		if (index < 0 || index >= this.actualSize) {
			return null;
		}
		
		return this.wordCounts[index].getWord();		
	}

	public int getCount(int index) {
		if (index < 0 || index >= this.actualSize) {
			return -1;
		}
		
		return this.wordCounts[index].getCount();
	}

	public void setCount(int index, int count) {
		if (index < 0 || index >= this.actualSize) {
			return;
		}
		
		if (count < 0) {
			this.wordCounts[index].setCount(0);
		} else { 
			this.wordCounts[index].setCount(count);
		}
	}

	private void doubleSize() {
		this.maxSize = this.maxSize * 2;
		
		/* would be stupid, if former size was 0, so take action... */
		if (this.maxSize <= 0) {
			this.maxSize = 1;
		}
		
		WordCount[] newWordCounts = new WordCount[this.maxSize];		
		
		/* copy old array */
		for (int i = 0; i < this.wordCounts.length; i++) {
			newWordCounts[i] = this.wordCounts[i];
		}
		
		this.wordCounts = newWordCounts;
	}
	
	public int getIndex(String word) {
		/* make it short */
		if (word == null || word.equals("")) {
			return -1;
		}
		
		/* search for the word in the array of WordCounts */
		for (int i = 0; i < this.actualSize; i++) {
			if (this.wordCounts[i].getWord().equals(word)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private WordCount get(int index) {
		if (index < 0 || index >= this.actualSize) {
			return null;
		}
		
		return this.wordCounts[index];
	}

	public void sort() {
		this.bucketSort();
	}

	private void bucketSort() {
		/* one bucket for every character */
		WordCountArray[] buckets = new WordCountArray[26];
		
		/* initialize the buckets */		   
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new WordCountArray(this.actualSize / 26);
		}
		
		/* sort words into buckets */
		for (int i = 0; i < this.actualSize; i++) {	
			if ((this.getWord(i).charAt(0) - 'a') >= 0)
				buckets[this.getWord(i).charAt(0) - 'a'].add(this.getWord(i), this.getCount(i));
		}
		
		/* sort every bucket with bubblesort */
		for (int i = 0; i < buckets.length; i++) {
			buckets[i].bubbleSort();
		}
		
		/* new WordCount-Array that will contain the sorted WordCount-objects */
		WordCount[] newWordCounts = new WordCount[this.actualSize];
		
		/* concatenate the sorted buckets into the new array */
		int j = 0;
		for (int bucket = 0; bucket < buckets.length; bucket++) {
			for (int i = 0; i < buckets[bucket].size(); i++) {
				newWordCounts[j] = buckets[bucket].get(i);
				j++;
			}
		}
		
		/* set the WordCount-Array of this instance to the new sorted WordCount-Array */
		this.wordCounts = newWordCounts;
		this.maxSize = this.actualSize;
	}

	private void bubbleSort() {
		for (int pass = 1; pass < this.actualSize; pass++) {
			for (int i = 0; i < this.actualSize - pass; i++) {
				if (this.getWord(i).compareTo(this.getWord(i + 1)) > 0) {
					WordCount tmp = this.wordCounts[i];
					this.wordCounts[i] = this.wordCounts[i + 1];
					this.wordCounts[i + 1] = tmp;
				}
			}
		}
	}

	private boolean wordsEqual(WordCountArray wca) {
		/* make it short: the same */
		if (this == wca) {
			return true;
		}
		
		/* cannot be the same */
		if ((wca == null) || (this.size() != wca.size())) {
			return false;
		}
		
		/* compare every single word at every position */
		for (int i = 0; i < this.size(); i++) {
			if (!this.getWord(i).equals(wca.getWord(i))) {
				return false;
			}
		}
		
		return true;
	}

	public double similarity(WordCountArray wca) {	
		if (wca == null) {
			return 0;
		}

		double scalarProductThis = this.scalarProduct(this);
		double scalarProductWca = wca.scalarProduct(wca);
		
		double scalarProduct = 0;
		
		if (scalarProductThis != 0 && scalarProductWca != 0) {
			scalarProduct = this.scalarProduct(wca) / (Math.sqrt(scalarProductThis * scalarProductWca));
		}
		
		return scalarProduct;
	}

	private double scalarProduct(WordCountArray wca) {
		if (wca == null) {
			return 0;
		}
		
		/* scalar product is 0 by definition, if size is different */
		if (this.size() != wca.size()) {
			return 0;
		}
		
		/* also, the scalar product is 0 by definition, 
		   if the contained words are not exactly the same.
		   Though, if this == wca we do not have to do the wordsEqual()-check. */
		if ((this != wca) && !this.wordsEqual(wca)) {
			return 0;
		}
		
		double scalarProduct = 0;
		
		for (int i = 0; i < this.size(); i++) {
			scalarProduct += this.getCount(i) * wca.getCount(i);			
		}
		
		return scalarProduct;
	}

	public boolean equals(WordCountArray wca) {
		/* make it short: the same */
		if (this == wca) {
			return true;
		}
		
		/* cannot be the same */
		if ((wca == null) || (this.size() != wca.size())) {
			return false;
		}
		
		/* compare every single word and their counts at every position */
		for (int i = 0; i < this.size(); i++) {
			if (!this.getWord(i).equals(wca.getWord(i)) || this.getCount(i) != wca.getCount(i)) {
				return false;
			}
		}
		
		return true;
	}
}