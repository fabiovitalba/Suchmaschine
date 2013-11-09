
public class Author {
	//Attributes
	private String firstName;
	private String lastName;
	private Date birthday;
	private String residence;
	private String email;
	
	//Constructor
	Author(String firstName, String lastName, Date birthday, String residence, String email)	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.residence = residence;
		this.email = email;
	}
	
	//Methods
	public String getFirstName()	{
		return this.firstName;
	}
	public void setFirstName(String firstName)	{
		this.firstName = firstName;
	}
	public String getLastName()	{
		return this.lastName;
	}
	public void setLastName(String lastName)	{
		this.lastName = lastName;
	}
	public Date getBirthday()	{
		return this.birthday;
	}
	public void setBirthday(Date birthday)	{
		this.birthday = birthday;
	}
	public String getResidence()	{
		return this.residence;
	}
	public void setResidence(String residence)	{
		this.residence = residence;
	}
	public String getEmail()	{
		return this.email;
	}
	public void setEmail(String email)	{
		this.email = email;
	}
	
	public String toString()	{
		String objDesc = "Autor: " + this.firstName + " " + this.lastName;
		
		return objDesc;
	}
	
	public String getContactInformation()	{
		String contact = this.firstName + " " + this.lastName + Terminal.NEWLINE
				+ "E-Mail: " + this.email + Terminal.NEWLINE
				+ "Wohnhaft in: " + this.residence;
		
		return contact;
	}
	
	//Gibt das Alter des Autors am Zeitpunkt today in Jahren zurück.
	public int getAge(Date today)	{
		int age = 0;
		
		/* Es wird einfach eine neue Instanz der Variable Date erstellt, die ja ohne 
		 * Übergabeparameter das aktuelle Datum wählt.
		 */
		age = this.birthday.getAgeInYears(today);
		
		return age;
	}
}
