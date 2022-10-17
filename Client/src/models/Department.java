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

    //Default Constructor
    public Department() {
        this.name = "N/A";
        this.code = "N/A";
    }

    //Primary Constructor
    public Department(String name, String code) {
        this.name = name;
        this.code = code;
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
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Department: " + getName() + "\nDepartment Code: " + getCode();
    }
}
