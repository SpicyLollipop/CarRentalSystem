package asg;

public class Customer
{
	private String customerName;
	private String customerIC;
	private String customerContact;
	private String customerLicense;
	
	Customer(String customerName,String customerIC, String customerContact, String customerLicense)
	{
		this.customerName = customerName;
		this.customerIC = customerIC;
		this.customerContact = customerContact;
		this.customerLicense = customerLicense;
	}
	
	public String getCustomerName()
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getCustomerIC()
	{
		return customerIC;
	}

	public void setCustomerIC(String customerIC)
	{
		this.customerIC = customerIC;
	}
	
	public String getCustomerContact()
	{
		return customerContact;
	}
	
	public void setCustomerContact(int customerContact)
	{
		this.customerContact = customerContact;
	}

	public String getCustomerLicense()
	{
		return customerLicense;
	}

	public void setCutomerLicense(String customerLicense)
	{
		this.customerLicense = customerLicense;
	}
}
