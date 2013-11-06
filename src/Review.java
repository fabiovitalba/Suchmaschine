
public class Review {
	//Attributes
	private Document reviewedDocument;
	private Author author;
	private String language;
	private Date releaseDate;
	private int rating;
	
	//Constructor
	Review(Document reviewedDocument, Author author, String language, Date releaseDate, int rating)	{
		this.reviewedDocument = reviewedDocument;
		this.author = author;
		this.language = language;
		this.releaseDate = releaseDate;
		this.rating = rating;
	}
	
	//Methods
	public Document getReviewedDocument()	{
		return this.reviewedDocument;
	}
	public void setReviewedDocument(Document reviewedDocument)	{
		this.reviewedDocument = reviewedDocument;
	}
	public Author getAuthor()	{
		return this.author;
	}
	public void setAuthor(Author author)	{
		this.author = author;
	}
	public String getLanguage()	{
		return this.language;
	}
	public void setLanguage(String language)	{
		this.language = language;
	}
	public Date getReleaseDate()	{
		return this.releaseDate;
	}
	public void setReleaseDate(Date releaseDate)	{
		this.releaseDate = releaseDate;
	}
	public int getRating()	{
		return this.rating;
	}
	public void setRating(int rating)	{
		this.rating = rating;
	}
	
	public String toString()	{
		String objDesc = "Rezension zu: " + this.reviewedDocument.toString() + "(Von: " + this.author.toString() + ")";
		
		return objDesc;
	}
}
