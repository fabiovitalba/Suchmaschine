
public class WordCountArray {
	//Attributes
	private WordCount[] words;
	int lastPosition;
	
	//Constructor
	public WordCountArray(int maxSize)	{
		this.words = new WordCount[maxSize];
		this.lastPosition = -1;
	}
	
	//Methods
	
	
	
	public void add(String word, int count)	{
		this.words[++this.lastPosition] = new WordCount(word, count);
	}
	
}
