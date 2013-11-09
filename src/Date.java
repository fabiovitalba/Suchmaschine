
public class Date {
	//Attributes
	private int day;
	private int month;
	private int year;
	
	//Constructor
	Date(int day, int month, int year)	{
		if (day > 30)	{
			this.day = 30;
		}	else if (day < 1)	{
			this.day = 1;
		}	else	{
			this.day = day;
		}
		if (month > 12)	{
			this.month = 12;
		}	else if (month < 1)	{
			this.month = 1;
		}	else	{
			this.month = month;
		}
		this.year = year;
	}
	Date()	{
		this.day = Terminal.TODAYS_DAY;
		this.month = Terminal.TODAYS_MONTH;
		this.year = Terminal.TODAYS_YEAR;
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
		String objDesc = "Tag: " + this.day + "." + this.month + "." + this.year;
		
		return objDesc;
	}
	
	private int daysSince1900()	{
		int daysPassed = 0;
		for (int i = 1900; i < this.year; i++)	{	//Fuege pro Jahr 365 Tage hinzu
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
		
		//Da durch den Konstruktor ohne Uebergabeparameter ein Objekt mit dem aktuellen Datum erstellt wird.
		age2 = (new Date()).daysSince1900();
		
		ageDiff = age2 - age1;
		
		return ageDiff;
	}
	
	public int getAgeInYears(Date today)	{
		int age, ageDiff = 0;
		
		//Speichere die Zeit in Tagen
		age = this.getAgeInDays(today);
		/* Fuer alle 365 Tage wird die Variable die die Zeit in Jahre speichert um 1 erhoeht.
		 * Die Zeit wird natuerlich auch pro Zyklus um 365 reduziert, damit man nicht in einer Endlosschleife endet.
		 */
		for (; age >= 365; ageDiff++)	{
			age -= 365;
		}
		
		return ageDiff;
	}
	
	/* Erlaeuterung zu f) vi)
	 * Wie in den Teilaufgaben iv) und v) ersichtlich, ist es wichtig, dass Methoden 
	 * entsprechend kommentiert und dokumentiert werden. Ohne diese Dokumentation kann
	 * man erst durch genaues duchlesen und nachvollziehen der Methode verstehen in welcher
	 * Masseinheit das Alter zurueckgegeben wird. Es besteht ja auch ein gewaltiger 
	 * unterschied zwischen dem Alter in Jahren und in Tagen.
	 * 
	 * Ausserdem kann nur durch vollstaendige Dokumentation sichergestellt werden, dass man
	 * zu einem spaeteren Zeitpunkt gleich das Projekt versteht und somit gleich weiterarbeiten
	 * kann. Wenn z.B. eine aufwendige Methode nicht kommentiert ist, kann es durchaus zu 
	 * einigen Stunden an Aufwand kommen, damit diese nachvollzogen werden kann und eventuell 
	 * optimiert werden kann.
	 */
}
