
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
		if (count < 0)	{
			return;
		}
		
		/* if we have reached the end of the array,
		   increase the array size*/
		if (actualSize == maxSize) {
			this.doubleSize();
		}	
		
		if (this.getIndex(word.toLowerCase()) > -1)	{
			this.wordCounts[this.getIndex(word.toLowerCase())].incrementCount(count);
		}	else	{
			this.wordCounts[actualSize] = new WordCount(word.toLowerCase(), count);
			this.actualSize++;
		}
	}
	
	public int size() {
		return this.actualSize;
	}
	
	public String getWord(int index) {
		if (index >= this.actualSize)	{
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
	
	public boolean equals(WordCountArray wca)	{
		if (wca == null)	{
			return false;
		}
		if (this.size() != wca.size())	{
			return false;
		}
		for (int i = 0; i < this.size(); i++)	{
			if (!this.getWord(i).equals(wca.getWord(i)))	{
				return false;
			}
			if (this.getCount(i) != wca.getCount(i))	{
				return false;
			}
		}
		return true;
	}
	
	public int getIndex(String word)	{
		if (word == null || word == "")	{
			return -1;
		}
		for (int i = 0; i < this.size(); i++)	{
			if (this.getWord(i).equals(word))	{
				return i;
			}
		}
		return -1;
	}
	
	private boolean wordsEqual(WordCountArray wca)	{
		if (wca == null)	{
			return false;
		}
		if (this.size() != wca.size())	{
			return false;
		}
		for (int i = 0; i < this.size(); i++)	{
			if (!this.getWord(i).equals(wca.getWord(i)))	{
				return false;
			}
		}
		return true;
	}
	
	private double scalarProduct(WordCountArray wca)	{
		if (!this.wordsEqual(wca))	{
			return 0;
		}
		double scalarProduct = 0;
		for (int i = 0; i < this.actualSize; i++)	{
			scalarProduct += this.getCount(i) * wca.getCount(i);
		}
		
		return scalarProduct;
	}
	
	public void sort()	{
		WordCount temp;
		
		for (int j = 0; j < this.actualSize; j++)	{
			for (int i = 0; i < (this.actualSize - 1); i++)	{
				if (this.wordCounts[i].getWord().compareTo(this.wordCounts[i + 1].getWord()) > 0)	{
					temp = this.wordCounts[i];
					this.wordCounts[i] = this.wordCounts[i + 1];
					this.wordCounts[i + 1] = temp;
				}
			}
		}
	}
	
	public double similarity(WordCountArray wca)	{
		if (wca == null)	{
			return -1;
		}
		if (this.size() != wca.size())	{
			return -1;
		}
		
		double result;
		
		//Make sure both are sorted
		this.sort();
		wca.sort();
		
		result = this.scalarProduct(wca) / Math.sqrt(this.scalarProduct(this) * wca.scalarProduct(wca));
		
		return result;
	}
}
