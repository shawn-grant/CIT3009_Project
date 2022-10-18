package models;
//============================================================================
//Name        : Date.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
//Description : Date Model Class
//============================================================================
import java.io.Serializable;

public class Date implements Serializable {

    private int day;
    private int month;
    private int year;

    //Default Constructor
    public Date() {
        day = 0;
        month = 0;
        year = 0;
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

    //String Constructor
    public Date(String dateString) {
        String[] segments = dateString.split("/");

        this.day = Integer.parseInt(segments[0]);
        this.month = Integer.parseInt(segments[1]);
        this.year = Integer.parseInt(segments[2]);
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

    @Override
    public String toString() {
        // database friendly format
        return getDay() + "/" + getMonth() + "/" + getYear();
    }
}
