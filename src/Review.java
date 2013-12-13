
public class Review {
	//Attributes
	private Author author;
	private Document reviewedDocument;
	private String language;
	private Date releaseDate;
	private int rating;
	public static final int MAX_RATING = 10;
	public static final int MIN_RATING = 0;
	
	//Constructors
	public Review(Author author, Document reviewedDocument, String language,
			Date releaseDate, int rating) {
		this.setLanguage(language);
		this.setRating(rating);
		
		this.author = author;
		this.reviewedDocument = reviewedDocument;
		this.releaseDate = releaseDate;
	}
	
	//Methods
	public Author getAuthor() {
		return author;
	}

	public Document getReviewedDocument() {
		return reviewedDocument;
	}

	public String getLanguage() {
		return language;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public int getRating() {
		return rating;
	}

	public String toString() {
		return this.reviewedDocument.toString() + " is rated " + this.rating + " by " + this.author.toString();
	}
	
	public int getAge(Date today) {
		return this.releaseDate.getAgeInDays(today);
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}

	public void setLanguage(String language) {
		if (language == null) {
			this.language = "";
		} else {
			this.language = language;
		}
	}

	public void setRating(int rating) {
		if (rating < Review.MIN_RATING) {
			this.rating = Review.MIN_RATING;
		} else if (rating > Review.MAX_RATING) {
			this.rating = Review.MAX_RATING;
		} else {
			this.rating = rating;
		}
	}
	
	public boolean equals(Review review)	{
		if (review == null)	{
			return false;
		}
		if (!this.getAuthor().equals(review.getAuthor()))	{
			return false;
		}
		if (!this.getReviewedDocument().equals(review.getReviewedDocument()))	{
			return false;
		}
		if (!this.getLanguage().equals(review.getLanguage()))	{
			return false;
		}
		if (!this.getReleaseDate().equals(review.getReleaseDate()))	{
			return false;
		}
		if (this.getRating() != review.getRating())	{
			return false;
		}
		return true;
	}
}
