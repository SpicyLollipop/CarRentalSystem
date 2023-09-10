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
    private String carStatus = "Available";

    public String getModel() {
        return carModel;
    }

    public int getSeats() {
        return carSeats;
    }

    public String getPlateno() {
        return carPlateno;
    }

    public String getPower() {
        return carPower;
    }

    public String getEngine() {
        return carEngine;
    }

    public String getCategory() {
        return carCategory;
    }

    public double getRate() {
        return carRate;
    }

    public String getStatus() {
        return carStatus;
    }

    public void setStatus(String status) {
        carStatus = status;
    }

    public CarManager(String model, int seat, String plateno, String power, String engine, String category, double rate) {
        carModel = model;
        carSeats = seat;
        carPlateno = plateno;
        carPower = power;
        carEngine = engine;
        carCategory = category;
        carRate = rate;
    }

    public CarManager(String model, int seat, String plateno, String power, String engine, String category, double rate, String status) {
        carModel = model;
        carSeats = seat;
        carPlateno = plateno;
        carPower = power;
        carEngine = engine;
        carCategory = category;
        carRate = rate;
        carStatus = status;
    }

    public static void addCar(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
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
    
    public static void displayCars(String filePath) {
        String line = "";

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s || %-13s || %-15s || %-13s || %-15s || %-13s || %-13s%n","Model", "Seat", "Plate No", "Power", "Engine", "Category", "Rate/Day");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                String[] carData = line.split(",");
                if (carData.length == 8) {
                    String model = carData[0].trim();
                    String seats = carData[1].trim();
                    String plateno = carData[2].trim();
                    String power = carData[3].trim();
                    String engine = carData[4].trim();
                    String category = carData[5].trim();
                    String rate = carData[6].trim();
                   

                    System.out.printf("%-15s || %-13s || %-15s || %-13s || %-15s || %-13s || %.2f%n", model, seats, plateno, power, engine, category, Double.parseDouble(rate));
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void removeCar(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
        System.out.print("\n\nEnter the plate number of the car to remove: ");
        String carToRemove = input.nextLine();

        boolean removed = false;

        Iterator<CarManager> iterator = cars.iterator();
        while (iterator.hasNext()) {
            CarManager car = iterator.next();
            if (car.getPlateno().equalsIgnoreCase(carToRemove)) {
                iterator.remove();
                System.out.println("Car removed successfully.\n");
                removed = true;
                break;
            }
        }

        if (!removed) {
            System.out.println("Car not found in the list./n");
        }

        carFileManager.setListOfCars(cars);
        try {
            carFileManager.saveToFile();
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public static void searchCar(Scanner input, ArrayList<CarManager> cars) {
        System.out.println("Search for a Car:");
        System.out.print("Enter Car Model (Press Enter to skip): ");
        String searchModel = input.nextLine();
        System.out.print("Enter Number of Seats (Press Enter to skip): ");
        String seatsInput = input.nextLine().trim();

        boolean found = false;
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    	System.out.printf("%-15s || %-13s || %-15s || %-13s || %-15s || %-13s || %-13s || %-8s%n", "PlateNo", "Status", "Model", "Seat", "Power", "Engine", "Category", "Rate/Day");
    	System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (CarManager car : cars) {
            boolean modelMatch = searchModel.isEmpty() || car.getModel().toLowerCase().contains(searchModel.toLowerCase()); boolean seatsMatch = seatsInput.isEmpty() || Integer.toString(car.getSeats()).equals(seatsInput);

            if (modelMatch && seatsMatch) {
            	System.out.printf("%-15s || %-13s || %-15s || %-13s || %-15s || %-13s || %-13s || %.2f%n", car.getPlateno(), car.getStatus(), car.getModel(), car.getSeats(), car.getPower(), car.getEngine(), car.getCategory(), car.getRate());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching cars found.");
        }
    }
    
    public static void updateStatus(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-15s || %-15s || %-15s%n", "Car Model", "Car Plate No", "Car Status");
        System.out.println("-----------------------------------------------------------------------");

        for (CarManager car : cars) {
            System.out.printf("%-15s || %-15s || %-15s%n", car.getModel(), car.getPlateno(), car.getStatus());
        }

        System.out.println("-----------------------------------------------------------------------");
        System.out.print("Enter the car plate number to change the car status: ");
        String plateNoToUpdate = input.nextLine();

        boolean updated = false;

        for (CarManager car : cars) {
            if (car.getPlateno().equalsIgnoreCase(plateNoToUpdate)) {
                System.out.println("Choose status:");
                System.out.println("1. Available");
                System.out.println("2. Not Available");
                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    car.setStatus("Available");
                    System.out.println("Car status set to Available.");
                } else if (choice == 2) {
                    car.setStatus("Not Available");
                    System.out.println("Car status set to Not Available.");
                } else {
                    System.out.println("Invalid choice. Car status not updated.");
                }

                updated = true;
                break;
            }
        }

        if (!updated) {
            System.out.println("Car not found with the specified plate no.");
        } else {
            // After updating the car status, save the changes to the file
            carFileManager.setListOfCars(cars);
            try {
                carFileManager.saveToFile();
            } catch (IOException e) {
                System.out.println("Error saving data to file: " + e.getMessage());
            }
        }
    }
    
    public static String findPath() {
        String TextName = "car.txt";
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = workingDirectory + File.separator + TextName;
        return absoluteFilePath;
    }
    
    public static String carDetailPath() {
        String TextName = "carDetail.txt";
        String workingDirectory = System.getProperty("user.dir");
        String absoluteFilePath = workingDirectory + File.separator + TextName;
        return absoluteFilePath;
    }
    
    public static void loadCarsData(ArrayList<CarManager> cars, FileManagement carFileManager) {
        String filePath = findPath();
        carFileManager = new FileManagement(filePath);

        try {
            carFileManager.loadFromFile();
            cars = carFileManager.getListOfCars();
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }



}
