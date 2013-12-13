
public class Date {
	//Attributes
	private int day;
	private int month;
	private int year;
	
	//Constructors
	public Date() {
		this.day = Terminal.TODAYS_DAY;
		this.month = Terminal.TODAYS_MONTH;
		this.year = Terminal.TODAYS_YEAR;
	}

	public Date(int day, int month, int year) {
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
	}
	
	//Methods
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	private int daysSince1900() {
		int days = 0;

		// all former years
		for (int i = 1900; i < this.year; i++) {
			days += this.daysInYear(i);
		}
		
		// all former months in this year
		for (int i = 1; i < this.month; i++) {
			days += this.daysInMonth(i, this.year);
		}
		
		// all former days in this month
		days += this.day - 1;
		
		return days;
	}
	
	private int daysInYear(int year) {
		int days = 0;
		
		for (int month = 1; month <= 12; month++) {
			days += this.daysInMonth(month, year);
		}
		
		return days;
	}
	
	public int getAgeInDays(Date today) {
		return today.daysSince1900() - this.daysSince1900();
	}
	
	public int getAgeInYears(Date today) {
		int age = today.year - this.year;
		
		if (today.month > this.month) {
			// birthday was earlier this year
			return age;
		}
		else if (today.month < this.month) {
			// birthday is in a later month of this year
			return age - 1;
		}
		else {
			// birthday is in this month
			if (today.day >= this.day) {
				// earlier this month or today
				return age;				
			}
			else {
				// later this month
				return age - 1;
			}
		}
	}
	
	private int daysInMonth(int month, int year) {
		switch(month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			case 2:
				return daysinFebruary(year);
		}
		
		return -1;
	}
	
	private int daysinFebruary(int year) {		
		if (year % 4 != 0) {
			return 28;
		}
		
		if ((year % 100 == 0) && (year % 400 != 0)) {
			return 28;
		}
		
		return 29;
	}
	
	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;
	}
	
	public void setDay(int day) {
		if (day < 1) {
			this.day = 1;
		} else if (day > daysInMonth(this.month, this.year)) {
			this.day = daysInMonth(this.month, this.year);
		} else {	
			this.day = day;
		}
	}
	
	public void setMonth(int month) {
		if (month < 1) {
			this.month = 1;
		} else if (month > 12) {
			this.month = 12;
		} else {
			this.month = month;
		}
		this.setDay(this.day);
	}
	
	public void setYear(int year) {
		if (year < 1900) {
			this.year = 1900;
		} else if (year > 2100) {
			this.year = 2100;
		} else {
			this.year = year;
		}
		this.setDay(this.day);
	}
	
	public boolean equals(Date date)	{
		if (date == null)	{
			return false;
		}
		if (this.getDay() != date.getDay())	{
			return false;
		}
		if (this.getMonth() != date.getMonth())	{
			return false;
		}
		if (this.getYear() != date.getYear())	{
			return false;
		}
		return true;
	}
}
