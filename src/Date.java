
public class Date {
	//Attributes
	private int day;
	private int month;
	private int year;
	
	//Constructor
	Date()	{
		this.day = Terminal.TODAYS_DAY;
		this.month = Terminal.TODAYS_MONTH;
		this.year = Terminal.TODAYS_YEAR;
	}
	Date(int day, int month, int year)	{
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	//Methods
	public int getDay()	{
		return this.day;
	}
	public void setDay(int day)	{
		this.day = day;
	}
	public int getMonth()	{
		return this.month;
	}
	public void setMonth(int month)	{
		this.month = month;
	}
	public int getYear()	{
		return this.year;
	}
	public void setYear(int year)	{
		this.year = year;
	}
	
	public String toString()	{
		String objDesc = "Tag:" + this.day + "." + this.month + "." + this.year;
		
		return objDesc;
	}
	
	private int daysSince1900()	{
		int daysPassed = 0;
		for (int i = 1900; i < this.year; i++)	{	//Füge pro Jahr 365 Tage hinzu
			daysPassed += 365;
		}
		for (int i = 0; i < this.month; i++)	{
			daysPassed += 30;
		}
		daysPassed += this.day;
		
		return daysPassed;
	}
	
	public int getAgeInDays(Date today)	{
		int age1 = 0, age2 = 0, ageDiff = 0;
		
		//Alter des aktuellen Dokuments
		age1 = this.daysSince1900();
		
		//Da durch den Konstruktor ohne Übergabeparameter ein Objekt mit dem aktuellen Datum erstellt wird.
		age2 = new Date().daysSince1900();
		
		ageDiff = age2 - age1;
		
		return ageDiff;
	}
}
