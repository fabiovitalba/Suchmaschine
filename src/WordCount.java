
public class WordCount {
	//Attributes
	private String text;
	private int count;
	
	//Constructor
	public WordCount(String text, int count)	{
		this.text = text;
		this.count = count;
	}
	
	//Methods
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		/* Wird ein illegaler Wert eingegeben (d.h. ein Negativer Wert, so wird die Anzahl einfach
		 * auf 0 gesetzt.
		 */
		if (count < 0)	{
			count = 0;
		}
		this.count = count;
	}
	
	public int incrementCount()	{
		this.count++;
		return this.count;
	}
	public int incrementCount(int n)	{
		if (n < 0)	{
			n = 0;
		}
		this.count += n;
		return this.count;
	}
}
