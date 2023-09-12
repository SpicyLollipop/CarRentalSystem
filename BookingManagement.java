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
    private static String rentCarModel;
    private static double rentCarPay;
    
    //Constructor for the class
    public BookingManagement(String customerName, String icNumber, String contactInfo, String licenseInfo, String startDate, String endDate, long durationInDays, String rentCarNo, double rentCarPay) {
        this.customerName = customerName;
        this.icNumber = icNumber;
        this.contactInfo = contactInfo;
        this.licenseInfo = licenseInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.durationInDays = durationInDays;
        this.rentCarNo = rentCarNo;
        this.rentCarModel = ""; // Initialize to an empty string
        this.rentCarPay = rentCarPay;
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

    public static String getRentCarModel() {
        return rentCarModel;
    }
	
    public static double getRentCarPay() {
        return rentCarPay;
    }
    
    public static long getDuration() {
    	return durationInDays;
    }
    //Register for a customer information
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
    }

    //Compute duration of day for car rental
    public static void getDateDuration() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the date start booking (dd/mm/yyyy): ");
        startDate = scanner.nextLine();

        System.out.print("Enter the date end booking (dd/mm/yyyy): ");
        endDate = scanner.nextLine();

        // Data format can be in 1/1/2023, and 01/01/2023
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

        
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

        startDate = String.join("/", startParts);
        endDate = String.join("/", endParts);
        LocalDate date1 = LocalDate.parse(startDate, inputFormatter);
        LocalDate date2 = LocalDate.parse(endDate, inputFormatter);

        durationInDays = Math.abs(date1.toEpochDay() - date2.toEpochDay())+1;

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\nDuration between the two dates: " + outputFormatter.format(date1) +
                " and " + outputFormatter.format(date2) + " is " + durationInDays + " days\n\n");
        
    }
    //check the availability of the car
    public static void checkAvailability(Scanner input, ArrayList<CarManager> cars) {
        System.out.println("Press 'x' to return to the menu at any time.");
        System.out.println("Press Enter twice to show all available car for booking.");
        
        String searchModel = "";
        String seatsInput = "";
        
        while (true) {
            System.out.print("Enter car model (Press Enter to skip): ");
            searchModel = input.nextLine().trim();

            if (searchModel.equalsIgnoreCase("x")) {
                System.out.println("\nReturning to the menu.");
                Continuity.backMenu();
                return;
            }

            System.out.print("Enter number of seats (Press Enter to skip): ");
            seatsInput = input.nextLine().trim();

            if (seatsInput.equalsIgnoreCase("x")) {
                System.out.println("\nReturning to the menu.");
                Continuity.backMenu();
                return;
            }

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
            System.out.println("-------------------------------------------------------------------");
            System.out.printf("%-15s || %-20s || %-15s%n", "Car Plate No", "Car Model", "Payment Rate");
            System.out.println("-------------------------------------------------------------------");
            for (CarManager car : availableCars) {
                System.out.printf("%-15s || %-20s || %-15.2f%n", car.getPlateno(), car.getModel(), car.getRate());
            }
            System.out.println("-------------------------------------------------------------------");
            Continuity.backMenu();
            return;
            }
        }
    
    //select car by unique plate number
    public static boolean selectCar(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
        System.out.print("Enter the plate number of the car you want to select: ");
        String plateNumber = input.nextLine();
        boolean select = false;

        for (CarManager car : cars) {
            if (car.getPlateno().equalsIgnoreCase(plateNumber) && car.getStatus().equalsIgnoreCase("Available")) 
            {
                rentCarNo = car.getPlateno();
                rentCarPay = car.getRate();
                rentCarModel = car.getModel();
                car.setStatus("Not Available"); // Update car status to Not Available

                select = true;
                break;
            }
        }

        if (!select) {
            System.out.println("Car not found with the specified plate number or not available.");
        } else {
            carFileManager.setListOfCars(cars);
            try {
                carFileManager.saveToFile();
            } catch (IOException e) {
                System.out.println("Error saving data to file: " + e.getMessage());
            }
        }

        return select;
    }
    
    //save the line into the bookingDetail.txt
    public static void storeBookingDetails() {
        String bookingDetail = customerName + "," + icNumber + "," + contactInfo + ","
                + licenseInfo + "," + BookingManagement.getRentCarNo() + "," + BookingManagement.getRentCarModel() +"," + startDate + ","
                + endDate + "," + BookingManagement.getDuration() + "," + BookingManagement.getRentCarPay();

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
    
    //Check booking detail with unique IC
    public static void checkBookingDetail(Scanner input,ArrayList<CarManager> cars) {
        System.out.print("Enter Customer IC: ");
        String icNumber = input.nextLine();

        try {
        	BufferedReader reader = new BufferedReader(new FileReader("bookingDetail.txt"));
            String line;
            boolean found = false;

            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-15s || %-15s || %-15s || %-15s || %-15s || %-25s || %-15s || %-15s ||  %-15s || %-15s%n", "Name", "IC", "Contact", "License", "PlateNo", "Car", "Start", "End", "Duration", "Rate");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            while ((line = reader.readLine()) != null) {
                String[] bookingInfo = line.split(",");
                
                // Check if the IC number from the file matches the input IC number
                if (bookingInfo.length >= 5 && icNumber.equals(bookingInfo[1].trim())) {
                    found = true;
                    System.out.printf("%-15s || %-15s || %-15s || %-15s || %-15s || %-25s || %-15s || %-15s || %-16s || %-15s%n",
                            bookingInfo[0], bookingInfo[1], bookingInfo[2], bookingInfo[3],
                            bookingInfo[4], bookingInfo[5], bookingInfo[6], bookingInfo[7], bookingInfo[8], bookingInfo[9]);
                }
            }

            if (!found) {
                System.out.println("No booking details found for the specified IC number.");
            }
            }catch (IOException e) {
            System.out.println("Error reading booking details: " + e.getMessage());
        }
    }
    
    //Cancel booking from the bookingDetail.txt
    public static void CancelBooking(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
        System.out.print("Enter customer IC to remove booking: ");
        String customerIC = input.nextLine();

        try {
            File inputFile = new File("bookingDetail.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            ArrayList<String> updatedBookingDetails = new ArrayList<>();

            String lineToRemove;
            boolean found = false;

            while ((lineToRemove = reader.readLine()) != null) {
                String[] bookingInfo = lineToRemove.split(",");

                if (bookingInfo.length >= 2 && customerIC.equals(bookingInfo[1].trim())) {
                    found = true;
                    for (CarManager car : cars) {
                        if (car.getPlateno().equals(bookingInfo[4].trim())) {
                            car.setStatus("Available");
                            break;
                        }
                    }
                    continue;
                }
                updatedBookingDetails.add(lineToRemove);
            }

            reader.close();

            if (found) {
                // Rewrite the entire file with the updated booking details
                BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));

                for (String updatedBooking : updatedBookingDetails) {
                    writer.write(updatedBooking + "\n");
                }

                writer.close();
                
                carFileManager.setListOfCars(cars);
                try {
                    carFileManager.saveToFile();
                } catch (IOException e) {
                    System.out.println("Error saving data to file: " + e.getMessage());
                }

                System.out.println("Booking removed successfully.");
            } else {
                System.out.println("No booking found with the specified customer IC.");
            }
        } catch (IOException e) {
            System.out.println("Error removing booking: " + e.getMessage());
        }
    }

}
