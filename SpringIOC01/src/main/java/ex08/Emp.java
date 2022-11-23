package ex08;

public class Emp {
	private String name;
	private String dept;
	private int salary;
	
	//constructor
	public Emp() {
		
	}
	public Emp(String name,String dept,int s) {
		this.name=name; this.dept=dept; salary=s;
	}
	
	//setter , getter
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	//toString
	@Override
	public String toString() {
		return "Emp [name=" + name + ", dept=" + dept + ", salary=" + salary + "]";
	}
	
}
