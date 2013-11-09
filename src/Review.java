
public class Review {
	//Attributes
	private Document reviewedDocument;
	private Author author;
	private String language;
	private Date releaseDate;
	private int rating;
	
	//Constructor
	Review(Author author, Document reviewedDocument, String language, Date releaseDate, int rating)	{
		this.author = author;
		this.reviewedDocument = reviewedDocument;
		this.language = language;
		this.releaseDate = releaseDate;
		if (rating > 10)	{
			this.rating = 10;
		}	else if (rating < 0)	{
			this.rating = 0;
		}	else	{
			this.rating = rating;
		}
		
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
		String objDesc = "Rezension zu: " + this.reviewedDocument.toString() + "(Von: " 
	+ this.author.toString() + ")";
		
		return objDesc;
	}
	
	//Gibt das Alter der Rezension am Zeitpunkt today in Tagen zurueck.
	public int getAge(Date today)	{
		int age = 0;
		
		age = this.releaseDate.getAgeInDays(today);
		
		return age;
	}
}
