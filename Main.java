package TestRun;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        ArrayList<CarManager> cars = new ArrayList<CarManager>();
        Scanner input = new Scanner(System.in);
        String relativeFilePath = "car.txt";
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = workingDirectory + File.separator + relativeFilePath;

        File1 carFileManager = new File1(absoluteFilePath);

        try {
            carFileManager.loadFromFile();
            cars = carFileManager.getListOfCars(); 
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }

        boolean continueManagingCars = true;

        while (continueManagingCars) {
            System.out.println("Menu:");
            System.out.println("1. Add a car");
            System.out.println("2. Display cars");
            System.out.println("3. Remove a car");
            System.out.println("4. Quit");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    CarManager.addCar(input, cars, carFileManager);
                    break;
                case 2:
                	File1.displayCars(absoluteFilePath);
                    break;
                case 3:
                	CarManager.removeCar(input, cars, carFileManager);
                    break;
                case 4:
                    continueManagingCars = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }

        input.close();

        
        carFileManager.setListOfCars(cars); 
        try {
            carFileManager.saveToFile(); 
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
}