import java.io.BufferedReader;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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

	    public void generatePaymentHistoryFile() {
	        try {
	            String basePath = CarManager.findPath();
	            BufferedWriter paymentHistoryWriter = new BufferedWriter(new FileWriter(basePath + "paymentHistory.txt"));

	            for (String[] data : customerData.values()) {
	                String line = String.join(",", data);
	                paymentHistoryWriter.write(line);
	                paymentHistoryWriter.newLine();
	            }

	            paymentHistoryWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void savePaymentHistory(String name, String ic, String contact, String plateNumber, String model,
	            String startDate, String endDate, double duration, double ratePerDay, double totalPayment) {
	        try {
	            String basePath = CarManager.findPath();
	            BufferedWriter paymentHistoryWriter = new BufferedWriter(new FileWriter(basePath + "paymentHistory.txt", true));

	            // Format the payment history data
	            String paymentHistoryData = String.format("%s,%s,%s,%s,%s,%s,%s,%.2f,%.2f,%.2f", name, ic, contact, plateNumber,
	                    model, startDate, endDate, duration, ratePerDay, totalPayment);

	            paymentHistoryWriter.write(paymentHistoryData);
	            paymentHistoryWriter.newLine();
	            paymentHistoryWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
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

	                    // Find the corresponding car and update its status
	                    CarManager carInstance = findCarByPlateNumber(cars, plateNumber);
	                    if (carInstance != null) {
	                        carInstance.setStatus("Booked");
	                    }

	                    // Save pickup history to pickupHistory.txt
	                    String pickupHistoryData = name + "," + customerIC + "," + contact + "," + plateNumber + "," + model + ","
	                            + pickupTime + "," + "pickup";

	                    BufferedWriter pickupHistoryWriter = new BufferedWriter(new FileWriter("paymentHistory.txt", true));
	                    pickupHistoryWriter.write(pickupHistoryData);
	                    pickupHistoryWriter.newLine();
	                    pickupHistoryWriter.close();

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


	    // You need to implement the calculateDuration, createReceipt, and savePaymentHistory methods.





/*	    public void saveBookingHistory(String name, String ic, String contact, String plateNumber) {
	        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	        String bookingHistoryData = name + "," + ic + "," + contact + "," + plateNumber + "," + timestamp;

	        try {
	            String basePath = CarManager.findPath();
	            BufferedWriter bookingHistoryWriter = new BufferedWriter(new FileWriter(basePath + "bookingHistory.txt", true));
	            bookingHistoryWriter.write(bookingHistoryData);
	            bookingHistoryWriter.newLine();
	            bookingHistoryWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  */
	    
	    public void returnCar(Scanner input, ArrayList<CarManager> cars, FileManagement carFileManager) {
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

	                    // Save the updated car status to car.txt
	                    carFileManager.setListOfCars(cars);
	                    carFileManager.saveToFile();

	                    System.out.println("Car returned successfully.");
	                    bookingFound = true;
	                    break; // Exit the loop since the booking has been found and processed
	                }
	            }

	            bookingDetailReader.close();

	            if (!bookingFound) {
	                System.out.println("No booking found with the specified IC.");
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

	    
	}