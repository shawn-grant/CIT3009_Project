package models;
//============================================================================
//Name        : Department.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
//Description : Department Model Class
//============================================================================
import java.io.Serializable;

public class Department implements Serializable {
    private String name;
    private String code;
    private final String[] departments = {"Management", "Inventory", "Accounting & sales"};
    private final String[] codes = {"MAN", "INV", "ACS"};

    //Default Constructor
    public Department() {
        name = "N/A";
        code = "N/A";
    }

    //Primary Constructor
    public Department(String name) {
        this.name = name;
    }

    //Copy Constructor
    public Department(Department department) {
        this.name = department.name;
        this.code = department.code;
    }

    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        int index = 0;
        while(index < departments.length) {
            if (departments[index].equals(name)) {
                return codes[index];
            }
            index++;
        }
        return null;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Department: " + getName() + "\nDepartment Code: " + getCode();
    }
}
