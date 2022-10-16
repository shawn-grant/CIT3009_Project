package models;

import java.io.Serializable;

public class Employee implements Serializable {
    private String id;
    private String firstName;
    private String lastName;
    private Date DOB;
    private String address;
    private String telephone;
    private String email;
    private String type;
    private String department;

    //Default Constructor
    public Employee() {
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.DOB = new Date();
        this.address = "";
        this.telephone = "";
        this.email = "";
        this.type = "";
        this.department = "";
    }

    //Primary Constructor
    public Employee(String id, String firstName, String lastName, Date DOB, String address,
                    String telephone, String email, String type, String department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.type = type;
        this.department = department;
    }

    //Copy Constructor
    public Employee(Employee employee) {
        this.id = employee.id;
        this.firstName = employee.firstName;
        this.lastName = employee.lastName;
        this.DOB = employee.DOB;
        this.address = employee.address;
        this.telephone = employee.telephone;
        this.email = employee.email;
        this.type = employee.type;
        this.department = employee.department;
    }

    //Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date dOB) {
        DOB = dOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    //Displaying employee record
    public void displayEmployee() {
        System.out.println("Employee ID: " + getId() + "\nFirstname: " + getFirstName() + "\nLastname: "
                + getLastName() + "\nDOB: " + getDOB() + "\nAddress: " + getAddress() + "\nTelephone: "
                + getTelephone() + "\nEmail: " + getEmail() + "\nType: " + getType() + "\nDepartment: "
                + getDepartment());
    }


}
