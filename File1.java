package TestRun;

import java.io.*;
import java.util.ArrayList;

public class File1 {
    private ArrayList<CarManager> carsList = new ArrayList<CarManager>();
    private File carFile;

    public File1(String carFile) {
        this.carFile = new File(carFile);
    }

    public ArrayList<CarManager> getListOfCars() {
        return carsList;
    }

    public void setListOfCars(ArrayList<CarManager> carsList) {
        this.carsList = carsList;
    }

    public void addCar(String model, int seat, String plateno, String power, String engine, String category, double rate) {
        carsList.add(new CarManager(model, seat, plateno, power, engine, category, rate));
    }

    public void saveToFile() throws IOException {
        try (FileWriter carWriter = new FileWriter(carFile, false)) {
            for (CarManager car : carsList) {
                String line = String.format("%s,%d,%s,%s,%s,%s,%.2f\n", car.getModel(), car.getSeats(),
                        car.getPlateno(), car.getPower(), car.getEngine(), car.getCategory(), car.getRate());

                carWriter.write(line);
            }
        }
    }

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
                if (carData.length == 7) {
                    String model = carData[0].trim();
                    int seats = Integer.parseInt(carData[1].trim());
                    String plateno = carData[2].trim();
                    String power = carData[3].trim();
                    String engine = carData[4].trim();
                    String category = carData[5].trim();
                    double rate = Double.parseDouble(carData[6].trim());

                    carsList.add(new CarManager(model, seats, plateno, power, engine, category, rate));
                }
            }
        } finally {
            if (carReader != null) {
                carReader.close();
            }
        }
    }
    
    public static void displayCars(String filePath) {
        String line = "";

        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s || %-13s || %-15s || %-13s || %-15s || %-13s || %-13s%n",
                "Model", "Seat", "Plate No", "Power", "Engine", "Category", "Rate/Day");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                String[] carData = line.split(",");
                if (carData.length == 7) {
                    String model = carData[0].trim();
                    String seats = carData[1].trim();
                    String plateno = carData[2].trim();
                    String power = carData[3].trim();
                    String engine = carData[4].trim();
                    String category = carData[5].trim();
                    String rate = carData[6].trim();

                    System.out.printf("%-15s || %-13s || %-15s || %-13s || %-15s || %-13s || %.2f%n",
                            model, seats, plateno, power, engine, category, Double.parseDouble(rate));
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }
}

