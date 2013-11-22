
public class Date {
	//Attributes
	private int day;
	private int month;
	private int year;
	
	//Constructor
	public Date(int day, int month, int year)	{
		this.setDay(day);
		this.setMonth(month);
		this.year = year;
	}
	public Date()	{
		this.day = Terminal.TODAYS_DAY;
		this.month = Terminal.TODAYS_MONTH;
		this.year = Terminal.TODAYS_YEAR;
	}
	
	//Methods
	public int getDay()	{
		return this.day;
	}
	public void setDay(int day)	{
		if (day > 30)	{
			this.day = 30;
		}	else if (day < 1)	{
			this.day = 1;
		}	else	{
			this.day = day;
		}
	}
	public int getMonth()	{
		return this.month;
	}
	public void setMonth(int month)	{
		if (month > 12)	{
			this.month = 12;
		}	else if (month < 1)	{
			this.month = 1;
		}	else	{
			this.month = month;
		}
	}
	public int getYear()	{
		return this.year;
	}
	public void setYear(int year)	{
		this.year = year;
	}
	
	@Override
	public String toString()	{
		return "Tag: " + this.day + "." + this.month + "." + this.year;
	}
	
	private int daysSince1900()	{
		return (this.day - 1) + ((this.month - 1) * 30) + ((this.year - 1900) * 360);
	}
	
	public int getAgeInDays(Date today)	{
		return today.daysSince1900() - this.daysSince1900();
	}
	
	public int getAgeInYears(Date today)	{
		return getAgeInDays(today)/(360);
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
