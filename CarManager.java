package TestRun;

import java.util.*;
import java.io.*;

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
	
	public CarManager (String model, int seat, String plateno, String power, String engine, String category, double rate)
	{
		carModel = model;
		carSeats = seat;
		carPlateno = plateno;
		carPower = power;
		carEngine = engine;
		carCategory = category;
		carRate = rate;
	}

	public static void addCar(Scanner input, ArrayList<CarManager> cars, Files1 carFileManager) {
        System.out.println("Enter Car Details:");
        System.out.print("Model: ");
        String carModel = input.nextLine();
        System.out.print("Seats: ");
        int carSeats = input.nextInt();
        input.nextLine();
        System.out.print("Plateno: ");
        String carPlateno = input.nextLine();
        System.out.print("Power: ");
        String carPower = input.nextLine();
        System.out.print("Engine: ");
        String carEngine = input.nextLine();
        System.out.print("Category: ");
        String carCategory = input.nextLine();
        System.out.print("Rate/Day: ");
        double carRate = input.nextDouble();
        input.nextLine();

        cars.add(new CarManager(carModel, carSeats, carPlateno, carPower, carEngine, carCategory, carRate));
        System.out.println("Car added successfully.");

        
        carFileManager.setListOfCars(cars);
        try {
            carFileManager.saveToFile();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
	
    public static void removeCar(Scanner input, ArrayList<CarManager> cars, Files1 carFileManager) {
        System.out.print("Enter the model of the car to remove: ");
        String modelToRemove = input.nextLine();

        boolean removed = false;

        Iterator<CarManager> iterator = cars.iterator();//check each carmodel
        while (iterator.hasNext()) {
            CarManager car = iterator.next();
            if (car.getModel().equalsIgnoreCase(modelToRemove)) {
                iterator.remove();
                System.out.println("Car removed successfully.");
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("Car not found in the list.");
        }

        
        carFileManager.setListOfCars(cars);
        try {
            carFileManager.saveToFile();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
    
    
}



