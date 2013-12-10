/**
 * This class represents a set words and its counts.
 * 
 * This class ensures, that no empty words are added and that the word count
 * is always greater than or equal to <code>0</code>.
 * 
 */
public class WordCountArray {
	/**
	 * the administered WordCount-objects
	 */
	private WordCount[] wordCounts;
	
	/**
	 * the actual number of administered WordCount-objects 
	 */
	private int actualSize;
	
	
	/**
	 * the maximum number of administrable WordCount-objects;
	 */
	private int maxSize;
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * The created instance is able to administer at most <code>maxSize</code> words.
	 *  
	 * @param maxSize the maximum number of words that can be administered by this instance
	 */
	public WordCountArray(int maxSize) {		
		if (maxSize < 0) {
			this.maxSize = 0;
		} else {
			this.maxSize = maxSize;
		}
		
		this.actualSize = 0;
		this.wordCounts = new WordCount[this.maxSize];
	}
	
	
	/**
	 * Adds the specified word with the specified count to this instance.
	 * 
	 * If the specified word is already administered by this instance, then the
	 * count of the specified word is increased by the given count.
	 * 
	 * If the specified word is not already administered by this instance, 
	 * this method creates a new {@link WordCount} instance and administers this newly created
	 * with count <code>count</code>.
	 * If the specified word is <code>null</code> or an empty {@link String}, nothing will happen.
	 * If the specified count is lower than <code>0</code>, nothing will happen.
	 * 
	 * 
	 * @param word the word to be added
	 * @param count the count of the word to be added
	 */
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
	
	
	/**
	 * Returns the number of words currently administered by this instance.
	 * 
	 * @return the number of words currently administered by this instance
	 */
	public int size() {
		return this.actualSize;
	}
	
	
	/**
	 * Returns the word at the position <code>index</code> of the {@link WordCount}-Array.
	 * 
	 * @param index the index
	 * @return the word at the specified <code>index</code> or <code>null</code>,
	 * if the specified <code>index</code> is illegal.
	 */
	public String getWord(int index) {
		if (index < 0 || index >= this.actualSize) {
			return null;
		}
		
		return this.wordCounts[index].getWord();		
	}
	
	
	/**
	 * Returns the count of the word at position <code>index</code> of the {@link WordCount}-Array.
	 * 
	 * @param index the index
	 * @return the count of the word at the specified <code>index</code> or <code>-1</code>,
	 * if the specified <code>index</code> is illegal
	 */
	public int getCount(int index) {
		if (index < 0 || index >= this.actualSize) {
			return -1;
		}
		
		return this.wordCounts[index].getCount();
	}
	
	
	/**
	 * Sets the count of the word at position <code>index</code> of the
	 * {@link WordCount}-Array to the specified <code>count</code>.
	 * 
	 * If the specified <code>index</code> is illegal, nothing will happen. 
	 * If the specified <code>count</code> is lower than <code>0</code>,
	 * the count is set to <code>0</code>.
	 * 
	 * @param index the index of the word whose frequency will be changed
	 * @param count the new frequency of the word at position <code>index</code>
	 */
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
	
	
	/**
	 * Doubles the number of administerable WordCount-objects,
	 */
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
	
	

	/**
	 * Returns the index of the internal
	 * {@link WordCount}-Array where the specified word is administered.
	 * 
	 * @param word the word for which we want to know the index
	 * @return the index of the specified word in the internal array,
	 * 		or <code>-1</code> if this word is not administered
	 */
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
	
	
	
	
	
	/**
	 * Private helper method that returns the <code>WordCount</code>-object at the specified index.
	 * 
	 * @param index the index
	 * @return the <code>WordCount</code>-object at the specified index or <code>null</code>,
	 * 			if the specified index is illegal
	 */
	private WordCount get(int index) {
		if (index < 0 || index >= this.actualSize) {
			return null;
		}
		
		return this.wordCounts[index];
	}
	

	
	
	/**
	 * Sorts the <code>WordCount</code> objects administered by this instance.
	 * 
	 * After calling this method the administered <code>WordCount</code> objects 
	 * are ordered lexicographically according to the words represented by the
	 * <code>WordCount</code> objects.
	 */
	public void sort() {
		this.bucketSort();
	}


	/**
	 * Sorts the <code>WordCount</code> objects administered by this instance
	 * with the bucket sort algorithm.
	 * 
	 * This method assumes, that all words begin with a lower case letter.
	 */
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
	
	
	/**
	 * Sorts the <code>WordCount</code> objects administered by this instance
	 * with the bubble sort algorithm. 
	 */
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
	
	
	/**
	 * Determines, whether the words administered by this instance and the words
	 * in the specified {@link WordCountArray} are equal.
	 * 
	 * This method returns <code>true</code>, if
	 * <ul>
	 *  <li>the words administered by this instance
	 *     and the words administered by the specified {@link WordCountArray} instance
	 *     are the same <b>and</b></li>
	 *  <li>the words are in the same order</li>
	 * </ul>
	 * Otherwise, this method will return <code>false</code>.
	 * 
	 * @param wca the {@link WordCountArray} that will be compared
	 * @return <code>true</code>, if the administered words equal as described in detail above;
	 * 			<code>false</code> otherwise.
	 */
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
	
	
	
	/**
	 * Calculate the similarity of this instance and the specified {@link WordCountArray}.
	 * 
	 * This method will return a value between <code>0</code> and <code>1</code>.
	 * If <code>wca</code> is <code>null</code>, <code>0</code> is returned.
	 * 
	 * @param wca the 2nd {@link WordCountArray}
	 * @return the similarity between this instance and the specified {@link WordCountArray}
	 */
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
	
	
	
	/**
	 * Calculate the scalar product of the word counts of this instance and the
	 * word counts of the specified {@link WordCountArray}.
	 * 
	 * If the two {@link WordCountArray} have a different size, <code>0</code> is returned.
	 * Also, if the words contained in the two {@link WordCountArray}s are not
	 * exactly the same (cf. {@link WordCountArray#wordsEqual(WordCountArray)}),
	 * the result is <code>0</code>. If <code>wca</code> is <code>null</code>, <code>0</code>
	 * is returned.
	 * 
	 * @param wca the 2nd {@link WordCountArray}
	 * @return the scalar product of this instance and the specified {@link WordCountArray}
	 */
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
	
	
	
	/**
	 * Returns true, if this instance and the specified {@link WordCountArray} equal.
	 * 
	 * @param wca the other WordCountArray 
	 * @return true, if this instance and the specified {@link WordCountArray} equal
	 */
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