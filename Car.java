
public class Car {
	private String carModel;
    private int carSeats;
    private String carPlateno;
    private String carPower;
    private String carEngine;
    private String carCategory;
    private double carRate;
    private String carStatus = "Available";

    public String getModel() {
        return carModel;
    }

    public int getSeats() {
        return carSeats;
    }

    public String getPlateno() {
        return carPlateno;
    }

    public String getPower() {
        return carPower;
    }

    public String getEngine() {
        return carEngine;
    }

    public String getCategory() {
        return carCategory;
    }

    public double getRate() {
        return carRate;
    }

    public String getStatus() {
        return carStatus;
    }

    public void setStatus(String status) {
        carStatus = status;
    }

    public Car(String model, int seat, String plateno, String power, String engine, String category, double rate) {
        carModel = model;
        carSeats = seat;
        carPlateno = plateno;
        carPower = power;
        carEngine = engine;
        carCategory = category;
        carRate = rate;
    }

    public Car(String model, int seat, String plateno, String power, String engine, String category, double rate, String status) {
        carModel = model;
        carSeats = seat;
        carPlateno = plateno;
        carPower = power;
        carEngine = engine;
        carCategory = category;
        carRate = rate;
        carStatus = status;
    }
    
}
