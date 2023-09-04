package asg;

public class Customer extends User 
{
	private String customerName;
	private int customerContact;
	
	Customer(String username, String password, String customerName, int customerContact)
	{
		super(username, password);
		this.customerName = customerName;
		this.customerContact = customerContact;
	}
	
	public String getCustomerName()
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}
	
	public int getCustomerContact()
	{
		return customerContact;
	}
	
	public void setCustomerContact(int customerContact)
	{
		this.customerContact = customerContact;
	}
}
