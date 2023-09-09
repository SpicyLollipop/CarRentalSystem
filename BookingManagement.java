import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;


public class BookingManagement {
    private static String customerName;
    private static String icNumber;
    private static String contactInfo;
    private static String licenseInfo;
    private static String startDate;
    private static String endDate;
    private static long durationInDays;
    private static String rentCarNo;
    private static double rentCarPay;

    public BookingManagement(String customerName, String icNumber, String contactInfo, String licenseInfo) {
        this.customerName = customerName;
        this.icNumber = icNumber;
        this.contactInfo = contactInfo;
        this.licenseInfo = licenseInfo;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
    
    public static String getRentCarNo() {
        return rentCarNo;
    }

    public static double getRentCarPay() {
        return durationInDays*rentCarPay;
    }
    
    public static long getDuration() {
    	return durationInDays;
    }
    
    public static void registerCustomer(Scanner scanner) {
        System.out.println("Customer Registration");
        System.out.print("Enter customer name: ");
        customerName = scanner.nextLine();
        System.out.print("Enter customer IC: ");
        icNumber = scanner.nextLine();
        System.out.print("Enter customer contact: ");
        contactInfo = scanner.nextLine();
        System.out.print("Enter customer license: ");
        licenseInfo = scanner.nextLine();
        System.out.println("Customer registered successfully!");
    }
    
    public static void getDateDuration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the date start booking (dd/mm/yyyy): ");
        startDate = scanner.nextLine();

        System.out.print("Enter the date end booking (dd/mm/yyyy): ");
        endDate = scanner.nextLine();

        // Define the date format pattern
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        // Check if the input date strings have single-digit days or months and add leading zeros if needed
        String[] startParts = startDate.split("/");
        String[] endParts = endDate.split("/");
        
        if (startParts[0].length() == 1) {
            startParts[0] = "0" + startParts[0];
        }
        if (startParts[1].length() == 1) {
            startParts[1] = "0" + startParts[1];
        }
        if (endParts[0].length() == 1) {
            endParts[0] = "0" + endParts[0];
        }
        if (endParts[1].length() == 1) {
            endParts[1] = "0" + endParts[1];
        }

        // Reconstruct the corrected date strings
        startDate = String.join("/", startParts);
        endDate = String.join("/", endParts);

        // Parse the input into LocalDate objects using the specified format
        LocalDate date1 = LocalDate.parse(startDate, inputFormatter);
        LocalDate date2 = LocalDate.parse(endDate, inputFormatter);

        // Calculate the duration between the two dates in days
        durationInDays = Math.abs(date1.toEpochDay() - date2.toEpochDay());

        // Define the date format pattern for displaying the result
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Display the result with the new format
        System.out.println("Duration between the two dates: " + outputFormatter.format(date1) +
                " and " + outputFormatter.format(date2) + " is " + durationInDays + " days");
        
    }

    public static void checkAvailability(Scanner input, ArrayList<CarManager> cars) {
        System.out.print("Enter car model (Press Enter to skip): ");
        String searchModel = input.nextLine().trim();
        System.out.print("Enter number of seats (Press Enter to skip): ");
        String seatsInput = input.nextLine().trim();

        // List available cars matching the search criteria
        ArrayList<CarManager> availableCars = new ArrayList<>();
        for (CarManager car : cars) {
            if (car.getStatus().equalsIgnoreCase("Available") &&
                (searchModel.isEmpty() || car.getModel().equalsIgnoreCase(searchModel)) &&
                (seatsInput.isEmpty() || Integer.toString(car.getSeats()).equals(seatsInput))) {
                availableCars.add(car);
            }
        }

        if (availableCars.isEmpty()) {
            System.out.println("No matching available cars found.");
        }

        System.out.println("Available cars:");
        System.out.println("----------------------------------------------------");
        System.out.printf("%-15s || %-20s || %-15s%n", "Car Plate No", "Car Model", "Payment Rate");
        System.out.println("----------------------------------------------------");
        for (CarManager car : availableCars) {
            System.out.printf("%-15s || %-20s || %-15.2f%n", car.getPlateno(), car.getModel(), car.getRate());
        }
    }

    public static void selectCar(Scanner input, ArrayList<CarManager> cars) {
        System.out.print("Enter the plate number of the car you want to select: ");
        String plateNumber = input.nextLine();

        // Find the car with the specified plate number
        for (CarManager car : cars) {
            if (car.getPlateno().equalsIgnoreCase(plateNumber) && car.getStatus().equalsIgnoreCase("Available")) 
            {
                rentCarNo = car.getPlateno();
                rentCarPay = car.getRate();
                car.setStatus("Not Available"); // Update car status to Not Available
                return; // Return the selected car
            }
        }

        System.out.println("Car not found with the specified plate number or not available.");
        return; // Return null if the car is not found or not available
    }
    
    // Method to store booking details and save to a file
    public static void storeBookingDetails() {
        // Implement code to store booking details into an array or object.
        // Include customer details, chosen car details, booking dates, duration, and payment rate.

        // Create a string with booking details
        String bookingDetail = customerName + "," + icNumber + "," + contactInfo + ","
                + licenseInfo + "," + BookingManagement.getRentCarNo()+ "," + startDate + ","
                + endDate + "," + getDuration() + "," + BookingManagement.getRentCarPay();

        // Save the booking detail to a file
        saveBookingDetailToFile(bookingDetail);
    }

    private static void saveBookingDetailToFile(String bookingDetail) {
        try {
            FileWriter writer = new FileWriter("bookingDetail.txt", true); // Append mode
            writer.write(bookingDetail + "\n");
            writer.close();
            System.out.println("Booking details saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving booking details: " + e.getMessage());
        }
    }
    
    public static void CheckBookingDetail(Scanner input) {
    	System.out.print("Enter Customer IC: ");
        String IcNumber = input.nextLine();
        
        boolean found = false;
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader("bookingDetail.txt"))) {

            // Display a header for the booking details table
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s%n",
                    "Name", "IC", "Contact", "License", "Start", "End", "Duration", "Car", "Rental");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");

            // Read each line from the file until the end is reached
            while ((line = reader.readLine()) != null) {
                // Split the line into individual fields using a comma (,) as the delimiter
                String[] bookingInfo = line.split(",");
                
                // Assuming IC number is in the second column (index 1 in the array)
                String bookedICNumber = bookingInfo[1];

                // Check if the IC number from the file matches the provided IC number
                if (bookedICNumber.equals(icNumber)) {
                    found = true; // Set the flag to indicate a match was found

                    // Display booking details for the matching booking
                    System.out.printf("%-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s || %-15s%n",
                            bookingInfo[0], bookingInfo[1], bookingInfo[2], bookingInfo[3],
                            bookingInfo[5], bookingInfo[6], bookingInfo[7], bookingInfo[4], bookingInfo[8]);
                }
            }
        } catch (IOException e) {
            // Handle any potential IOException (e.g., file not found or error reading the file)
            System.out.println("Error reading booking details: " + e.getMessage());
        }

        // Check if no booking details were found for the provided IC number
        if (!found) {
            System.out.println("No booking details found for the provided IC number.");
        }
    }
    
	public static void CancelBooking() {
		// TODO Auto-generated method stub
		
	}
	
	
}
