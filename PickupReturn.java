import java.time.*;
import java.io.*;
import java.util.*;
import java.text.*;

	public class PickupReturn{
		
		private Map<String, String[]> customerData;
		private FileManagement carFileManager;
		private ArrayList<CarManager> cars;
		private Scanner scanner;
		
		public PickupReturn(Map<String, String[]> customerData) {
	        this.customerData = customerData;
		}
	    public void readFilesAndProcess() {
	    	try {
	            String carPath = CarManager.findPath();
	            String carDetailPath = CarManager.carDetailPath();

	            BufferedReader carReader = new BufferedReader(new FileReader(carPath));
	            BufferedReader detailReader = new BufferedReader(new FileReader(carDetailPath));

	            String carLine;
	            String detailLine;

	            while ((carLine = carReader.readLine()) != null && (detailLine = detailReader.readLine()) != null) {
	                // Process each line from both files
	                processCarAndDetailLine(carLine, detailLine);
	            }

	            carReader.close();
	            detailReader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void processCarAndDetailLine(String carLine, String detailLine) {
	        String[] carData = carLine.split(",");
	        String[] detailData = detailLine.split(",");

	        if (carData.length >= 8 && detailData.length >= 3) {
	            String ic = carData[1].trim();
	            String[] combinedData = new String[9];

	            // Combine car and detail data into one array
	            System.arraycopy(carData, 0, combinedData, 0, carData.length);
	            System.arraycopy(detailData, 0, combinedData, carData.length, detailData.length);

	            // Calculate total payment
	            double duration = Double.parseDouble(combinedData[6]);
	            double ratePerDay = Double.parseDouble(combinedData[7]);
	            double totalPayment = duration * ratePerDay;
	            combinedData[8] = String.valueOf(totalPayment);

	            customerData.put(ic, combinedData);
	        }
	    }

	    public void pickupCar(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
	        // Get the IC and current time from the user
	        System.out.print("Enter your IC: ");
	        String customerIC = input.nextLine();

	        try {
	            // Read data from bookingDetail.txt
	            BufferedReader bookingDetailReader = new BufferedReader(new FileReader("bookingDetail.txt"));
	            String bookingLine;
	            boolean bookingFound = false;

	            while ((bookingLine = bookingDetailReader.readLine()) != null) {
	                String[] bookingInfo = bookingLine.split(",");
	                if (bookingInfo.length >= 3 && customerIC.equals(bookingInfo[1].trim())) {
	                    String name = bookingInfo[0].trim();
	                    String contact = bookingInfo[2].trim();
	                    String plateNumber = bookingInfo[4].trim();
	                    String model = bookingInfo[5].trim();
	                    String pickupTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
	                    int duration = Integer.parseInt(bookingInfo[8].trim());
                        double PayRate = Double.parseDouble(bookingInfo[9].trim());
	                    double totalAmount = PayRate*duration;

	                    // Find the corresponding car and update its status
	                    CarManager carInstance = findCarByPlateNumber(cars, plateNumber);
	                    if (carInstance != null) {
	                        carInstance.setStatus("Booked");
	                    }

	                    // Save pickup history to pickupHistory.txt
	                    String pickupHistoryData = name + "," + customerIC + "," + contact + "," + plateNumber + "," + model + ","
	                            + pickupTime + "," + "pickup";
	                    
	                    BufferedWriter pickupHistoryWriter = new BufferedWriter(new FileWriter("bookingHistory.txt", true));
	                    pickupHistoryWriter.write(pickupHistoryData);
	                    pickupHistoryWriter.newLine();
	                    pickupHistoryWriter.close();
	                    
	                    //Save payment history to paymentHistory.txt
	                    String paymentData = name + "," + customerIC + "," + contact + "," + plateNumber + "," + model + ","
	                            + pickupTime + "," + PayRate + "," + duration + "," + totalAmount;
	                    
	                    BufferedWriter paymentHistoryWriter = new BufferedWriter(new FileWriter("paymentHistory.txt", true));
	                    paymentHistoryWriter.write(paymentData);
	                    paymentHistoryWriter.newLine();
	                    paymentHistoryWriter.close();
	                    
	                    // Print the receipt-like format
	                    System.out.println("\n===== Car Pickup Receipt =====");
	                    System.out.println("Customer Name: " + name);
	                    System.out.println("Contact: " + contact);
	                    System.out.println("Plate Number: " + plateNumber);
	                    System.out.println("Car Model: " + model);
	                    System.out.println("Pay at: " + pickupTime);
	                    System.out.println("Duration (in days): " + duration);
	                    System.out.println("Hourly Rate: RM" + PayRate);
	                    System.out.println("Total Amount: RM" + totalAmount);
	                    System.out.println("==============================\n");

	                    // Save the updated car status to car.txt
	                    carFileManager.setListOfCars(cars);
	                    carFileManager.saveToFile();

	                    System.out.println("Car picked up successfully.");
	                    bookingFound = true;
	                    break; // Exit the loop since the booking has been found and processed
	                }
	            }

	            bookingDetailReader.close();

	            if (!bookingFound) {
	                System.out.println("No booking found with the specified IC.");
	            }
	        } catch (IOException e) {
	            System.out.println("Error processing pickup: " + e.getMessage());
	        }
	    }


	    
	    public void returnCar(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
	        // Get the IC and current time from the user
	        System.out.print("Enter your IC: ");
	        String customerIC = input.nextLine();

	        try {
	            // Read data from bookingDetail.txt
	            BufferedReader bookingDetailReader = new BufferedReader(new FileReader("bookingDetail.txt"));
	            String bookingLine;
	            boolean bookingFound = false;
	            ArrayList<String> updatedBookingDetails = new ArrayList<>(); // Store updated booking details

	            while ((bookingLine = bookingDetailReader.readLine()) != null) {
	                String[] bookingInfo = bookingLine.split(",");
	                if (bookingInfo.length >= 3 && customerIC.equals(bookingInfo[1].trim())) {
	                    String name = bookingInfo[0].trim();
	                    String contact = bookingInfo[2].trim();
	                    String plateNumber = bookingInfo[4].trim();
	                    String model = bookingInfo[5].trim();
	                    String returnTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

	                    // Find the corresponding car and update its status
	                    CarManager carInstance = findCarByPlateNumber(cars, plateNumber);
	                    if (carInstance != null) {
	                        carInstance.setStatus("Available");
	                    }

	                    // Save booking history to bookingHistory.txt
	                    String bookingHistoryData = name + "," + customerIC + "," + contact + "," + plateNumber + "," + model + ","
	                            + returnTime + "," + "return";

	                    BufferedWriter bookingHistoryWriter = new BufferedWriter(new FileWriter("bookingHistory.txt", true));
	                    bookingHistoryWriter.write(bookingHistoryData);
	                    bookingHistoryWriter.newLine();
	                    bookingHistoryWriter.close();

	                    System.out.println("Car returned successfully.");
	                    bookingFound = true;
	                } else {
	                    // If the booking doesn't match the current customer, keep it in the updated booking details list
	                    updatedBookingDetails.add(bookingLine);
	                }
	            }

	            bookingDetailReader.close();

	            if (!bookingFound) {
	                System.out.println("No booking found with the specified IC.");
	            } else {
	                // Write the updated booking details back to bookingDetail.txt, excluding the returned booking
	                BufferedWriter bookingDetailWriter = new BufferedWriter(new FileWriter("bookingDetail.txt"));
	                for (String updatedBooking : updatedBookingDetails) {
	                    bookingDetailWriter.write(updatedBooking);
	                    bookingDetailWriter.newLine();
	                }
	                bookingDetailWriter.close();
	            }
	        } catch (IOException e) {
	            System.out.println("Error processing return: " + e.getMessage());
	        }
	    }


	    
	    private CarManager findCarByPlateNumber(ArrayList<CarManager> cars, String plateNumber) {
	        for (CarManager car : cars) {
	            if (car.getPlateno().equalsIgnoreCase(plateNumber)) {
	                return car;
	            }
	        }
	        return null; // Car not found
	    }
	
	    public PickupReturn(Scanner scanner, ArrayList<CarManager> cars, FileManagement carFileManager) {
	        this.scanner = scanner;
	        this.cars = cars;
	        this.carFileManager = carFileManager;
	    }

	    
	    public static void displayBookingHistory(String filePath) {
	        String line = "";

	        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	        System.out.printf("%-20s || %-13s || %-15s || %-13s || %-15s || %-23s || %-13s%n", "Customer Name", "IC Number", "Contact Info", "License Info", "Car Model", "Pickup/Return Date", "Action");
	        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

	        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            while ((line = br.readLine()) != null) {
	                String[] bookingData = line.split(",");
	                if (bookingData.length == 7) {
	                    String customerName = bookingData[0].trim();
	                    String icNumber = bookingData[1].trim();
	                    String contactInfo = bookingData[2].trim();
	                    String licenseInfo = bookingData[3].trim();
	                    String carModel = bookingData[4].trim();
	                    String bookingDateString = bookingData[5].trim();
	                    String action = bookingData[6].trim();

	                    Date bookingDate = inputDateFormat.parse(bookingDateString);

	                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	                    String formattedBookingDate = outputDateFormat.format(bookingDate);

	                    System.out.printf("%-20s || %-13s || %-15s || %-13s || %-15s || %-23s || %-13s%n",
	                            customerName, icNumber, contactInfo, licenseInfo, carModel, formattedBookingDate, action);
	                }
	            }
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }

	        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    }
		
	    public static void displayPaymentHistory(String filePath) {
	        String line = "";

	        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	        System.out.printf("%-20s || %-13s || %-15s || %-15s || %-15s || %-23s || %-9s || %-11s%n", "Customer Name", "IC Number", "Contact Info", "License Info", "Car Model", "Booking Date", "Duration", "Total Amount");
	        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

	        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        
	        try (BufferedReader pr = new BufferedReader(new FileReader(filePath))) {
	            while ((line = pr.readLine()) != null) {
	                String[] paymentData = line.split(",");
	                if (paymentData.length >= 9) {
	                    String customerName = paymentData[0].trim();
	                    String icNumber = paymentData[1].trim();
	                    String contactInfo = paymentData[2].trim();
	                    String licenseInfo = paymentData[3].trim();
	                    String carModel = paymentData[4].trim();
	                    String payDateString = paymentData[5].trim();
	                    int duration = Integer.parseInt(paymentData[7].trim());
	                    double totalAmount = Double.parseDouble(paymentData[8].trim());
	                    
	                    Date payDate = inputDateFormat.parse(payDateString);

	                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	                    String formattedPayDate = outputDateFormat.format(payDate);

	                    System.out.printf("%-20s || %-13s || %-15s || %-15s || %-15s || %-23s || %-9s || %-11.2f%n",
	                            customerName, icNumber, contactInfo, licenseInfo, carModel, formattedPayDate, duration, totalAmount);
	                }
	            }
	        } catch (IOException | ParseException e) {
	            e.printStackTrace();
	        }
	        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	    }
	}
