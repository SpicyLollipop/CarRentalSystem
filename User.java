package TestRun;

public class User 
{
	private static String username = "username";
	private static String password = "password";
	
	public User(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public static String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public static String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
}
