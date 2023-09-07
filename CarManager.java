//package xxx;

public class CarManager {
	private String carModel;
	private int carSeats;
	private String carPlateno;
	private String carPower;
	private String carEngine;
	private String carCategory;
	private double carRate;
	
	public String getModel()
	{
		return carModel;
	}
	public int getSeats()
	{
		return carSeats;
	}
	public String getPlateno()
	{
		return carPlateno;
	}
	public String getPower()
	{
		return carPower;
	}
	public String getEngine()
	{
		return carEngine;
	}
	public String getCategory()
	{
		return carCategory;
	}
	public double getRate()
	{
		return carRate;
	}
	
	public CarMenu (String model, int seat, String plateno, String power, String engine, String category, double rate)
	{
		carModel = model;
		carSeats = seat;
		carPlateno = plateno;
		carPower = power;
		carEngine = engine;
		carCategory = category;
		carRate = rate;
	}

	
}

