import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize FileManagement and load car data
        String filePath = CarManager.findPath();
        FileManagement carFileManager = new FileManagement(filePath);
        BookingManagement bookingManager = new BookingManagement(null, null, null, null, filePath, filePath, 0, filePath, 0);
        // Load car data from the file
        try {
            carFileManager.loadFromFile();
        } catch (IOException e) {
            System.out.println("Error loading car data from file: " + e.getMessage());
            // Handle the exception gracefully
        }

        // Initialize Scanner
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        boolean mainMenuLoop = true;
        while (mainMenuLoop) {
            Menu.MainMenu();

            int mainMenuChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (mainMenuChoice) {
                case 1:
                    // Car menu loop
                    boolean carMenuLoop = true;
                    while (carMenuLoop) {
                        Menu.displayCarMenu();

                        int carMenuChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch (carMenuChoice) {
                            case 1:
                                // Add a car
                                CarManager.addCar(scanner, carFileManager.getListOfCars(), carFileManager);
                                break;
                            case 2:
                                // Display cars
                                CarManager.displayCars(filePath);
                                break;
                            case 3:
                                // Remove a car
                                CarManager.removeCar(scanner, carFileManager.getListOfCars(), carFileManager);
                                break;
                            case 4:
                                // Search for a car
                                CarManager.searchCar(scanner, carFileManager.getListOfCars());
                                break;
                            case 5:
                                // Update car status
                                CarManager.updateStatus(scanner, carFileManager.getListOfCars(), carFileManager);
                                break;
                            case 6:
                                // Quit car menu
                                carMenuLoop = false;
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                                break;
                        }
                    }
                    break;
                case 2:
                    // Booking menu loop
                    boolean bookingMenuLoop = true;
                    while (bookingMenuLoop) {
                        Menu.displayBookingMenu();
                        
                        int bookingMenuChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        
                    		switch (bookingMenuChoice) 
                    		{
                        		case 1:
                        			BookingManagement.checkAvailability(scanner, carFileManager.getListOfCars());
                           		break;
                        		case 2:
                        			BookingManagement.registerCustomer(scanner);
                            		BookingManagement.getDateDuration();
                            		BookingManagement.selectCar(scanner, carFileManager.getListOfCars(), carFileManager);
                            		BookingManagement.storeBookingDetails();
                            		break;
                        		case 3:
                        			BookingManagement.checkBookingDetail(scanner, carFileManager.getListOfCars());
                        			break;
                        		case 4:
                            		BookingManagement.CancelBooking(scanner, carFileManager.getListOfCars(), carFileManager);
                            		break;
                        		case 5:
                                    bookingMenuLoop = false;
                        			break;
                        		default:
                            		System.out.println("Invalid choice. Please select a valid option.");
                            		break;
                    		}
                    }
                    break;
                case 3:
                    // Save car data and exit
                    try {
                        carFileManager.saveToFile();
                    } catch (IOException e) {
                        System.out.println("Error saving car data to file: " + e.getMessage());
                    }
                    mainMenuLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }

        // Close the scanner
        scanner.close();
    }
}