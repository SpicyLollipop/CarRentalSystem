public class Customer
{
	private static String customerName;
	private static String customerIC;
	private static String customerContact;
	private String customerLicense;
	
	Customer(String customerName,String customerIC, String customerContact, String customerLicense)
	{
		this.customerName = customerName;
		this.customerIC = customerIC;
		this.customerContact = customerContact;
		this.customerLicense = customerLicense;
	}
	
	public static String getCustomerName()
	{
		return customerName;
	}
	
	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public static String getCustomerIC()
	{
		return customerIC;
	}

	public void setCustomerIC(String customerIC)
	{
		this.customerIC = customerIC;
	}
	
	public static String getCustomerContact()
	{
		return customerContact;
	}
	
	public void setCustomerContact(String customerContact)
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
