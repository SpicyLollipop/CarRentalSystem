import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main implements Continuity{
    public static void main(String[] args) {
        Menu.Login();
        // Initialize FileManagement and load car data
        String filePath = CarManager.findPath();
        FileManagement carFileManager = new FileManagement(filePath);
        BookingManagement bookingManager = new BookingManagement(null, null, null, null, filePath, filePath, 0, filePath, 0);
        // Load car data from the file
        try {
            carFileManager.loadFromFile();
        } catch (IOException e) {
            System.out.println("Error loading car data from file: " + e.getMessage());
        }

        //scanner for input
        Scanner scanner = new Scanner(System.in);

        // Main menu loop
        boolean mainMenuLoop = true;
        while (mainMenuLoop) {
            Menu.MainMenu();

            int mainMenuChoice = scanner.nextInt();
            scanner.nextLine(); 

            switch (mainMenuChoice) {
                case 1:
        
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
                                // Quit car menu loop
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
                    			//check car availability
                        		case 1:
                        			BookingManagement.checkAvailability(scanner, carFileManager.getListOfCars());
                           		break;
                        		case 2:
                        			//Rent a car
                        			BookingManagement.registerCustomer(scanner);
                            		BookingManagement.getDateDuration();
                                    boolean carSelected = false;
                                    while (!carSelected) {
                                        carSelected = BookingManagement.selectCar(scanner, carFileManager.getListOfCars(), carFileManager);
                                        if (!carSelected) {
                                            System.out.println("Returning to the booking menu.");
                                        }
                                    }
                            		BookingManagement.storeBookingDetails();
                                    Continuity.backMenu();
                            		break;
                        		case 3:
                        			//Check Booking detail
                        			BookingManagement.checkBookingDetail(scanner, carFileManager.getListOfCars());
                        			break;
                        		case 4:
                        			//Cancel booking detail
                            		BookingManagement.CancelBooking(scanner, carFileManager.getListOfCars(), carFileManager);
                            		break;
                        		case 5:
                        			//Quit booking menu loop
                                    bookingMenuLoop = false;
                        			break;
                        		default:
                            		System.out.println("Invalid choice. Please select a valid option.");
                            		break;
                    		}
                    }
                    break;
                case 3:
                //Car pick up and return loop
               	 boolean pickupReturnLoop = true;
                    while (pickupReturnLoop) {
                        Menu.displayPickupReturnMenu();
                        int pickupReturnChoice = scanner.nextInt();
                        scanner.nextLine();
                        
                        switch (pickupReturnChoice) {
                        	case 1:
                        		//Car pickup
                            	PickupReturn pickupReturn = new PickupReturn(scanner, carFileManager.getListOfCars(), carFileManager);
                                pickupReturn.pickupCar(scanner, carFileManager.getListOfCars(), carFileManager);
                                break;
                            case 2:
                            	//Car return
                                PickupReturn pickupReturn1 = new PickupReturn(scanner, carFileManager.getListOfCars(), carFileManager);
                                pickupReturn1.returnCar(scanner, carFileManager.getListOfCars(), carFileManager);
                                break;
                            case 3:
                            	//Quit pickup and return loop
                                pickupReturnLoop = false; // Quit payment menu
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                                break;
                        }
                    }
                    break;
                case 4:
                    // Payment menu loop
                    boolean historyMenuLoop = true;
                    while (historyMenuLoop) {
                        Menu.displayHistoryMenu();
                        int historyMenuChoice = scanner.nextInt();
                        scanner.nextLine();
                        
                        switch (historyMenuChoice) {
                            case 1:
                                //View Booking History
                                PickupReturn.displayBookingHistory("bookingHistory.txt");
                                break;
                            case 2:
                                // View Payment History
                            	PickupReturn.displayPaymentHistory("paymentHistory.txt");
                                break;
                            case 3:
                            	//Quit payment menu loop
                                historyMenuLoop = false; // Quit payment menu
                                break;
                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                                break;
                        }
                    }
                    break;

               
                     case 5:
                    	 //exit whole main menu
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
        scanner.close();
    }
}
