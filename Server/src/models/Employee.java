package models;
//============================================================================
//Name        : Employee.java
//Author      : Tyrien Gilpin
//Version     : 1
//Copyright   : Your copyright notice
//Description : Employee Model Class
//============================================================================

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "employee")
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @Column(name = "ID")
    private String id;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Temporal(TemporalType.DATE)
    @Column(name = "DOB")
    private Date DOB;
    @Column(name = "address")
    private String address;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "email")
    private String email;
    @Column(name = "employeeType")
    private String type;
    @Column(name = "dept_code")
    private String department;

    //Default Constructor
    public Employee() {
        id = "N/A";
        firstName = "N/A";
        lastName = "N/A";
        DOB = new Date();
        address = "N/A";
        telephone = "N/A";
        email = "N/A";
        type = "N/A";
        department = "N/A";
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

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", DOB=" + DOB +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
