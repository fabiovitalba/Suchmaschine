/**
 * The class {@code Date} represents a date.
 * 
 * This class ensures, that the represented date is always a valid date.
 * For example, the method {@link Date#toString()} will never return 31/2/2010.
 *
 */
public class Date {
	/**
	 * the day of the date
	 */
	private int day;
	
	/**
	 * the month of the date
	 */
	private int month;
	
	/**
	 * the year of the date
	 */
	private int year;
	
	
	/**
	 * Constructs a date that represents the current date
	 */
	public Date() {
		this.day = Terminal.TODAYS_DAY;
		this.month = Terminal.TODAYS_MONTH;
		this.year = Terminal.TODAYS_YEAR;
	}


	/**
	 * Constructs a date with the given values.
	 * 
	 * If the specified values (or at least one of them) are invalid,
	 * the constructor behaves as described for {@link Date#setDay(int)},
	 * {@link Date#setMonth(int)} and {@link Date#setYear(int)}.
	 * 
	 * @param day the day
	 * @param month the month
	 * @param year the year
	 */
	public Date(int day, int month, int year) {
		/* use setter-methods, since they check for invalid values */
		this.setYear(year);
		this.setMonth(month);
		this.setDay(day);
	}
	
	
	/**
	 * Returns the day of the date
	 * 
	 * @return the day of the date
	 */
	public int getDay() {
		return day;
	}


	/**
	 * Returns the month of the date
	 * 
	 * @return the month of the date
	 */
	public int getMonth() {
		return month;
	}


	/**
	 * Returns the year of the date
	 * 
	 * @return the year of the date
	 */
	public int getYear() {
		return year;
	}
	
	
	/**
	 * Returns the days of this date since 01/01/1900.
	 * 
	 * @return the days since 01/01/1900.
	 */
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
	
	
	/**
	 * Returns the number of the days in the specified year.
	 * 
	 * @param year the year
	 * @return the number of the days in the specified year.
	 */
	private int daysInYear(int year) {
		int days = 0;
		
		for (int month = 1; month <= 12; month++) {
			days += this.daysInMonth(month, year);
		}
		
		return days;
	}	
	
		
	/**
	 * Returns the days between the specified date and the date represented by this instance.
	 * 
	 * @param today the specified date
	 * @return the days
	 */
	public int getAgeInDays(Date today) {
		return today.daysSince1900() - this.daysSince1900();
	}
	
	
	/**
	 * Returns the full years between the specified date and the date represented by this instance.
	 * 
	 * @param today the specified date
	 * @return the full years
	 */
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
	
	
	/**
	 * Returns the number of the days in the specified month in the specified year.
	 * For a <code>month</code> value of either 1, 3, 5, 7, 8, 10 or 12 this method returns 31.
	 * For a <code>month</code> value of either 4, 6, 9 or 11 this method returns 30.
	 * For a <code>month</code> value of 2 (February) the returned value is either 28 or 29, 
	 * depending on the specified year.
	 * 
	 * @param month the month
	 * @param year the year
	 * @return the number of the days in the specified month in the specified year.
	 */
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
	
	
	/**
	 * Returns the number of days in the February of the specified year. This method considers leap years.
	 * 
	 * @param year the year
	 * @return the number of days in the February of the specified year.
	 */
	private int daysinFebruary(int year) {		
		if (year % 4 != 0) {
			return 28;
		}
		
		if ((year % 100 == 0) && (year % 400 != 0)) {
			return 28;
		}
		
		return 29;
	}
	
	
	
	/**
	 * Returns a string representation of this date: day/month/year.
	 * @return a string representation of this date: day/month/year
	 */
	public String toString() {
		return this.day + "/" + this.month + "/" + this.year;
	}


	/**
	 * Sets the day of this date. 
	 * 
	 * If the specified day is < 1, then the day is set to 1.
	 * If the specified day is greater than the number of days 
	 * in the month of this date, then it is set to the maximum
	 * value of days in the month of this date.   
	 * 
	 * @see Date#daysInMonth(int, int)
	 * @param day the new day
	 */
	public void setDay(int day) {
		if (day < 1) {
			this.day = 1;
		} else if (day > daysInMonth(this.month, this.year)) {
			this.day = daysInMonth(this.month, this.year);
		} else {	
			this.day = day;
		}
	}


	/**
	 * Sets the month of this date.
	 * 
	 * If the specified month is < 1, the month is set to 1.
	 * If the specified month is > 12, the month is set to 12.
	 * If the new month has less days than the current month,
	 * it may happen that the day of this date gets invalid.
	 * In this case, the day of this date is set to the
	 * maximum value of the specified month.
	 * 
	 * @param month the new month
	 */
	public void setMonth(int month) {
		if (month < 1) {
			this.month = 1;
		} else if (month > 12) {
			this.month = 12;
		} else {
			this.month = month;
		}
		
		/* To avoid that the day of this date gets invalid,
		 * execute the setDay() method with the current day */
		this.setDay(this.day);
	}


	
	/**
	 * Sets the year of this date.
	 * 
	 * If the specified year is < 1900, then the year is set to 1900.
	 * If the specified year is > 2100, then the year is set to 2100.
	 * In case the current month of this date is February it may happen that
	 * the day of this date gets invalid (a value of 29 in a leap year).
	 * In this case the day is set to 28.
	 * 
	 * @param year the new year
	 */
	public void setYear(int year) {
		if (year < 1900) {
			this.year = 1900;
		} else if (year > 2100) {
			this.year = 2100;
		} else {
			this.year = year;
		}
		
		/* To avoid that the day of this date gets invalid,
		 * execute the setDay() method with the current day */
		this.setDay(this.day);
	}	
	
	
	/**
	 * Returns true, if this instance and the specified {@link Date} equal.
	 * 
	 * @param date the other date 
	 * @return true, if this instance and the specified {@link Date} equal
	 */
	public boolean equals(Date date) {
		if (this == date) {
			return true;
		}
		
		if (date == null) {
			return false;
		}
		
		return this.day == date.day 
			&& this.month == date.month 
			&& this.year == date.year;
	}
}
