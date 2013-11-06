
public class Document {
	//Attributes
	private String title;
	private String language;
	private String description;
	private Date releaseDate;
	private Author author;
	
	//Constructor
	Document(String title, String language, String description, Date releaseDate, Author author)	{
		this.title = title;
		this.language = language;
		this.description = description;
		this.releaseDate = releaseDate;
		this.author = author;
	}
	
	//Methods
	public String getTitle()	{
		return this.title;
	}
	public void setTitle(String title)	{
		this.title = title;
	}
	public String getLanguage()	{
		return this.language;
	}
	public void setLanguage(String language)	{
		this.language = language;
	}
	public String getDescription()	{
		return this.description;
	}
	public void setDesciption(String description)	{
		this.description = description;
	}
	public Date getReleaseDate()	 {
		return this.releaseDate;
	}
	public void setReleaseDate(Date releaseDate)	{
		this.releaseDate = releaseDate;
	}
	public Author getAuthor()	{
		return this.author;
	}
	public void setAuthor(Author author)	{
		this.author = author;
	}
	
	public String toString()	{
		String objDesc = "Dokument: " + this.title + " (Autor: " + this.author.toString() + ", " + this.releaseDate.toString() + ")";
		
		return objDesc;
	}
}
