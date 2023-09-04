import java.util.*;
import java.io.IOException;

public class CarMenuTest {
    public static void main(String[] args) {
        ArrayList<CarMenu> cars = new ArrayList<CarMenu>();
        Scanner input = new Scanner(System.in);
        Files1 carFileManager = new Files1("car.txt"); // Create an instance of the Files1 class

        try {
            carFileManager.loadFromFile(); // Load data from "car.txt" if the file exists
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
            input.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addCar(input, cars, carFileManager);
                    break;
                case 2:
                    displayCars(cars);
                    break;
                case 3:
                    removeCar(input, cars, carFileManager);
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

        // Save the car data to "car.txt" before exiting
        try {
            carFileManager.saveToFile();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static void addCar(Scanner input, ArrayList<CarMenu> cars, Files1 carFileManager) {
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

        cars.add(new CarMenu(carModel, carSeats, carPlateno, carPower, carEngine, carCategory, carRate));
        System.out.println("Car added successfully.");

        // Update the file with the new car data
        carFileManager.setListOfCars(cars);
        try {
            carFileManager.saveToFile();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static void displayCars(ArrayList<CarMenu> cars) {
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("     Model      ||     Seat      ||     Plate No     ||     Power    ||     Engine    ||    Category     ||     Rate/Day     ");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        for (CarMenu car : cars) {
        	System.out.printf("%-15s || %-13d || %-15s || %-13s || %-15s || %.2f%n",
        		    car.getModel(), car.getSeats(), car.getPlateno(), car.getPower(), car.getEngine(), car.getCategory(), car.getRate());
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }

    public static void removeCar(Scanner input, ArrayList<CarMenu> cars, Files1 carFileManager) {
        System.out.print("Enter the model of the car to remove: ");
        String modelToRemove = input.nextLine();

        boolean removed = false;

        Iterator<CarMenu> iterator = cars.iterator();
        while (iterator.hasNext()) {
            CarMenu car = iterator.next();
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

        // Update the file with the updated car data
        carFileManager.setListOfCars(cars);
        try {
            carFileManager.saveToFile();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
}
