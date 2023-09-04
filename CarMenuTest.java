package Run;
import java.util.*;
public class CarMenuTest {
	public static void main(String[] args) {
        ArrayList<CarMenu> cars = new ArrayList<CarMenu>();
        cars.add(new CarMenu("Honda City", 5, "Petrol", "Auto", "Sedan", 328.00));
        cars.add(new CarMenu("Perodua Alza", 7, "Petrol", "Auto", "MPV", 353.00));
        cars.add(new CarMenu("Proton X50", 5, "Petrol", "Auto", "SUV", 455.00));
        cars.add(new CarMenu("Toyota Vellfire", 7, "Hybrid", "Auto", "Minivan", 530.00));
        
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("     Model      ||     Seat      ||     Power    ||     Engine    ||    Category     ||     Rate/Day     ");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        for (CarMenu car : cars) {
            System.out.printf("%-15s || %-13d || %-12s || %-13s || %-15s || %.2f%n",
                    car.getModel(), car.getSeats(), car.getPower(), car.getEngine(), car.getCategory(), car.getRate());
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }
}