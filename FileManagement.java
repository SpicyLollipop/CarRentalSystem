import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManagement {
    private ArrayList<CarManager> carsList = new ArrayList<CarManager>(); 
    private File carFile;
    
   
    public FileManagement(String carFile) {
        this.carFile = new File(carFile);
    }
    
    public ArrayList<CarManager> getListOfCars() {
        return carsList;
    }

    public void setListOfCars(ArrayList<CarManager> carsList) {
        this.carsList = carsList;
    }

    public void addCar(String model, int seat, String plateno, String power, String engine, String category, double rate, String status) {
        carsList.add(new CarManager(model, seat, plateno, power, engine, category, rate, status));
    }
    
    
    
    //save to file method
    public void saveToFile() throws IOException {
        try (FileWriter carWriter = new FileWriter(carFile, false)) {
            for (CarManager car : carsList) {
                String line = String.format("%s,%d,%s,%s,%s,%s,%.2f,%s\n", car.getModel(), car.getSeats(),
                        car.getPlateno(), car.getPower(), car.getEngine(), car.getCategory(), car.getRate(), car.getStatus());

                carWriter.write(line);
            }
        }
    }

    //load from file method
    public void loadFromFile() throws IOException {
        if (!carFile.exists()) {
            return;
        }

        BufferedReader carReader = null;
        try {
            carReader = new BufferedReader(new FileReader(carFile));
            String line;

            while ((line = carReader.readLine()) != null) {
                String[] carData = line.split(",");
                if (carData.length == 8) {
                    String model = carData[0].trim();
                    int seats = Integer.parseInt(carData[1].trim());
                    String plateno = carData[2].trim();
                    String power = carData[3].trim();
                    String engine = carData[4].trim();
                    String category = carData[5].trim();
                    double rate = Double.parseDouble(carData[6].trim());
                    String status = carData[7].trim(); // Read status as a string

                    carsList.add(new CarManager(model, seats, plateno, power, engine, category, rate, status));
                }
            }
        } finally {
            if (carReader != null) {
                carReader.close();
            }
        }
    }
    
}
