package models;

public class Date {

	private int day;
	private int month;
	private int year;
	
	//Default Consturctor
	public Date() {
		this.day = 00;
		this.month = 00;
		this.year = 0000;
	}

	//Primary Constructor
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	//Copy Constructor
	public Date(Date date) {
		this.day = date.day;
		this.month = date.month;
		this.year = date.year;
	}

	//Getters and Setters
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void displayDate() {
		System.out.println("\nDay: " + getDay() + "\nMonth: " + getMonth() + "\nYear: " + getYear());
	}
	
	
	
}
