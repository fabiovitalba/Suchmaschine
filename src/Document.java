
public class Document {
	//Attributes
	private String title;
	private String language;
	private String description;
	private Date releaseDate;
	private Author author;
	public static final String[] SUFFICES = {
		"ab",
		"al",
		"ant",
		"artig",
		"bar",
		"chen",
		"ei",
		"eln",
		"en",
		"end",
		"ent",
		"er",
		"fach",
		"fikation",
		"fizieren",
		"fähig",
		"gemäß",
		"gerecht",
		"haft",
		"haltig",
		"heit",
		"ieren",
		"ig",
		"in",
		"ion",
		"iren",
		"isch",
		"isieren",
		"isierung",
		"ismus",
		"ist",
		"ität",
		"iv",
		"keit",
		"kunde",
		"legen",
		"lein",
		"lich",
		"ling",
		"logie",
		"los",
		"mal",
		"meter",
		"mut",
		"nis",
		"or",
		"sam",
		"schaft",
		"tum",
		"ung",		
		"voll",
		"wert",
		"würdig"
	};
	private WordCountArray wordCounts;
	
	//Constructors
	public Document(String title, String language, String description,
			Date releaseDate, Author author) {
		/* use this methods, just in case the value of the parameters is null */
		this.setTitle(title);
		this.setLanguage(language);
		this.setDescription(description);
		
		this.releaseDate = releaseDate;
		this.author = author;
	}
	
	public Document(String title, String language, String description,
			Date releaseDate, Author author, String text) {
		this(title, language, description, releaseDate, author);
		
		this.addText(text);
	}
	
	//Methods
	public String getTitle() {
		return title;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public WordCountArray getWordCounts() {
		return this.wordCounts;
	}
	
	public String toString() {
		return this.title + " by " + this.author.toString();
	}
	
	public int getAge(Date today) {
		return this.releaseDate.getAgeInDays(today);
	}
	
	public void setTitle(String title) {
		if (title == null) {
			this.title = "";
		} else {
			this.title = title;
		}
	}

	public void setLanguage(String language) {
		if (language == null) {
			this.language = "";
		} else {
			this.language = language;
		}
	}

	public void setDescription(String description) {
		if (description == null) {
			this.description = "";
		} else {
			this.description = description;
		}
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	private static String[] tokenize(String text) {
		int wordCount = 0;
		
		/* count spaces in the text */
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == ' ') {
				wordCount++;
			}
		}
		
		// there is always one word more than there are spaces
		wordCount++;
		
		// the resulting array
		String[] words = new String[wordCount];
		
		String word = "";		
		int wordIndex = 0;
		
		for (int i = 0; i <= text.length(); i++) {
			/* reached end of text or end of word
			   important: check end of text first!! */
			if (i == text.length() || text.charAt(i) == ' ') {
				if (word.length() > 0) {			
					/* put word in array */
					words[wordIndex] = word;
					wordIndex++;
					
					/* start with empty word for next loop */
					word = "";
				}
			} else {
				/* not end of word: append character */
				word = word + text.charAt(i);
			}
		}
		
		return words;
	}
	
	private void addText(String text) {
		String[] words = Document.tokenize(text);
		
		this.wordCounts = new WordCountArray(0);
		
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			
			/* find suffix and cut it */
			String suffix = Document.findSuffix(word);
			word = Document.cutSuffix(word, suffix);
			
			this.wordCounts.add(word, 1);
		}
	}
	
	private static boolean suffixEqual(String word1, String word2, int n) {		
		/* if n is too large, last n chars are not equal */
		if (n > word1.length() || n > word2.length()) {
			return false;
		}
		
		boolean isEqual = true;
		int i = 0;
		
		while (isEqual && i < n) {
			/* begin comparison at last char */
			if (word1.charAt(word1.length() - 1 - i) != word2.charAt(word2.length() - 1 - i)) {
				isEqual = false;
			}
			i++;
		}
	
		return isEqual;
	}
	
	private static String findSuffix(String word) {
		if (word == null || word.equals("")) {
			return null;
		}
		
		String suffix = "";
		String suffixHit = "";
		int i = 0;
		
		while (i < Document.SUFFICES.length) {
			suffix = Document.SUFFICES[i];
			
			/* check, if this suffix is a suffix of word */
			if (suffixEqual(word, suffix, suffix.length())) {
				if(suffixHit.length() < suffix.length()){
					suffixHit = suffix;
				}
			}
			
			i++;
		}
		return suffixHit;
	}
	
	private static String cutSuffix(String word, String suffix) {
		if (suffix == null || suffix.equals("")) {
			return word;
		}
		
		if (word == null) {
			return null;
		}
		
		/* not a suffix */
		if (!suffixEqual(word, suffix, suffix.length())) {
			return word;
		}
		
		/* create word without suffix, by copying all characters of the word stem */
		String wordWithoutSuffix = "";
		
		for (int i = 0; i < word.length() - suffix.length(); i++) {
			wordWithoutSuffix = wordWithoutSuffix + word.charAt(i);
		}
		
		return wordWithoutSuffix;
	}
	
	public boolean equals (Document document)	{
		if (document == null)	{
			return false;
		}
		if (!this.getTitle().equals(document.getTitle()))	{
			return false;
		}
		if (!this.getLanguage().equals(document.getLanguage()))	{
			return false;
		}
		if (!this.getDescription().equals(document.getDescription()))	{
			return false;
		}
		if (!this.getReleaseDate().equals(document.getReleaseDate()))	{
			return false;
		}
		if (!this.getAuthor().equals(document.getAuthor()))	{
			return false;
		}
		if (!this.getWordCounts().equals(document.getWordCounts()))	{
			return false;
		}
		return true;
	}
}
