package database.models;

public class Department {
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
	public Department(Department depObj) {
		this.name = depObj.name;
		this.code = depObj.code;
	}

	
	//Getters and setters
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
	
	public void Display() {
		System.out.println("Department: "+getName()+ "\nCode: "+getCode());
	}

}
