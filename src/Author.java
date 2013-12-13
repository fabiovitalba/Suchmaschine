
public class Author {
	//Attributes
	private String firstName;
	private String lastName;
	private Date birthday;
	private String residence;
	private String email;

	//Constructors
	public Author(String firstName, String lastName, Date birthday,
			String residence, String email) {
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setResidence(residence);
		this.setEmail(email);

		this.birthday = birthday;
	}
	
	//Methods
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	
	public String getResidence() {
		return residence;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
	
	public String getContactInformation() {
		return this.firstName + " " + this.lastName + Terminal.NEWLINE
				+ "<" + this.email + ">" + Terminal.NEWLINE
				+ this.residence;
	}
	
	public int getAge(Date today) {
		return this.birthday.getAgeInYears(today);
	}

	public void setFirstName(String firstName) {
		if (firstName == null) {
			this.firstName = "";
		} else {
			this.firstName = firstName;
		}
	}
	
	public void setLastName(String lastName) {
		if (lastName == null) {
			this.lastName = "";
		} else {
			this.lastName = lastName;
		}
	}
	
	public void setResidence(String residence) {
		if (residence == null) {
			this.residence = "";
		} else {
			this.residence = residence;
		}
	}
	
	public void setEmail(String email) {
		if (email == null) {
			this.email = "";
		} else {
			this.email = email;
		}
		
	}
	
	public boolean equals(Author author)	{
		if (author == null)	{
			return false;
		}
		if (!this.getFirstName().equals(author.getFirstName()))	{
			return false;
		}
		if (!this.getLastName().equals(author.getLastName()))	{
			return false;
		}
		if (!this.getBirthday().equals(author.getBirthday()))	{
			return false;
		}
		if (!this.getResidence().equals(author.getResidence()))	{
			return false;
		}
		if (!this.getEmail().equals(author.getEmail()))	{
			return false;
		}
		return true;
	}
}
