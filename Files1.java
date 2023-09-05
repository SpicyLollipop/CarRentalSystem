import java.io.*;
import java.util.ArrayList;

public class Files1 {
    private ArrayList<CarMenu> carsList = new ArrayList<CarMenu>();
    private File carFile; // Use File type for the carFile

    public Files1(String carFile) {
        this.carFile = new File(carFile); // Assign the fileName to the carFile
    }

    public ArrayList<CarMenu> getListOfCars() {
        return carsList;
    }

    public void setListOfCars(ArrayList<CarMenu> carsList) {
        this.carsList = carsList;
    }

    public void addCar(String model, int seat, String plateno, String power, String engine, String category, double rate) {
        carsList.add(new CarMenu(model, seat, plateno, power, engine, category, rate));
    }

    public void saveToFile() throws IOException {
        try (FileWriter carWriter = new FileWriter(carFile, false)) {
            for (CarMenu car : carsList) {
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

                    carsList.add(new CarMenu(model, seats, plateno, power, engine, category, rate));
                }
            }
        } finally {
            if (carReader != null) {
                carReader.close();
            }
        }
    }
}
