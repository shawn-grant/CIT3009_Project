package database.models;

public class Customer {
	private String id;
	private String firstName;
	private String lastName;
	private Date DOB;
	private String Address;
	private String email;
	private Date membershipDate;
	private Date membershipExipryDate;
	
	
	public Customer() {
		this.id = "N/A";
		this.firstName = "N/A";
		this.lastName = "N/A";
		this.DOB = new Date();
		this.Address = "N/A";
		this.email = "N/A";
		this.membershipDate = new Date();
		this.membershipExipryDate = new Date();
	}
	
	//Primar Constructor
	public Customer(String id, String firstName, Date dOB, String address, String email, Date membershipDate,
			Date membershipExipryDate, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.DOB = dOB;
		this.Address = address;
		this.email = email;
		this.membershipDate = membershipDate;
		this.membershipExipryDate = membershipExipryDate;
	}
	
	//Copy Constructor
	public Customer(Customer customer) {
		this.id = customer.id;
		this.firstName = customer.firstName;
		this.lastName = customer.lastName;
		this.DOB = customer.DOB;
		this.Address = customer.Address;
		this.email = customer.email;
		this.membershipDate = customer.membershipDate;
		this.membershipExipryDate = customer.membershipExipryDate;
	}

	//Getters and setters
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
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getMembershipDate() {
		return membershipDate;
	}

	public void setMembershipDate(Date membershipDate) {
		this.membershipDate = membershipDate;
	}

	public Date getMembershipExipryDate() {
		return membershipExipryDate;
	}

	public void setMembershipExipryDate(Date membershipExipryDate) {
		this.membershipExipryDate = membershipExipryDate;
	}

	@Override
	public String toString() {
		return "Customer [getId()=" + getId() + ", getFirstName()=" + getFirstName() +", getLastName()=" +getLastName() +", getDOB()=" + getDOB()
				+ ", getAddress()=" + getAddress() + ", getEmail()=" + getEmail() + ", getMembershipDate()="
				+ getMembershipDate() + ", getMembershipExipryDate()=" + getMembershipExipryDate() + "]";
	}

	public void Display() {
		System.out.println("Customer ID: "+ getId()+ "\nFirstname: "+getFirstName()+ "\nLastname: "+getLastName()+
		"\nAddress: " +getAddress()+ "\nEmail: "+getEmail()+ "\nMembership Date: "+getMembershipDate()+
		"\nMembership Expiry Date: "+getMembershipExipryDate());
	}

}
