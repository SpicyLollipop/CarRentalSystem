import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


	public class PaymentSystem {
		
		private Map<String, String[]> customerData;
		
		public PaymentSystem() {
	        customerData = new HashMap<>();
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

	    public void generateCustomerReceipt(String ic) {
	        String[] customerInfo = customerData.get(ic);
	        if (customerInfo != null) {
	            String receiptFileName = ic + "receipt.txt";
	            try {
	                BufferedWriter receiptWriter = new BufferedWriter(new FileWriter(receiptFileName));

	                String receiptData = String.join(",", customerInfo);
	                receiptWriter.write(receiptData);

	                receiptWriter.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("Customer not found!");
	        }
	    }

	    public void saveBookingHistory(String name, String ic, String contact, String plateNumber) {
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



	}

