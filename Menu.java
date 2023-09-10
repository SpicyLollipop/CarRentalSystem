import java.io.File;
import java.io.IOException;
import java.util.*;

public class Menu {
	
	public static void Login(){
		 boolean isVerified = false;
	        Scanner scanner = new Scanner(System.in);

	        while (!isVerified) {
	            System.out.print("Welcome to Car Rental System\n");
	            System.out.print("Enter username: ");
	            String enteredUsername = scanner.nextLine();
	            System.out.print("Enter password: ");
	            String enteredPassword = scanner.nextLine();

	            if (enteredUsername.equals(User.getUsername()) && enteredPassword.equals(User.getPassword())) {
	                isVerified = true;
	                System.out.println("Login successfully!\n");
	            } else {
	                System.out.println("Wrong username or password! Try again.");
	            }
	        }
	}
	
	public static void MainMenu() {
        System.out.println( "Main Menu");
        System.out.println( "1 - Car Menu");
        System.out.println( "2 - Booking");
        System.out.println( "3 - Exit");
        System.out.print  ( "Your choice: ");
    }
	
	public static void displayCarMenu() {
        System.out.println( "Car Menu");
        System.out.println( "1 - Add a car");
        System.out.println( "2 - Display cars");
        System.out.println( "3 - Remove a car");
        System.out.println( "4 - Search a car");
        System.out.println( "5 - Update Car Status");     
        System.out.println( "6 - Quit");
        System.out.print  ( "Your choice: ");
    }
			
	public static void displayBookingMenu() {
        System.out.println( "Booking Menu");
        System.out.println( "1 - Check Availability");
        System.out.println( "2 - Rent a car");
        System.out.println( "3 - Check Booking Details");
        System.out.println( "4 - Cancel Booking");
        System.out.println( "5 - Quit");
        System.out.print  ( "Your choice: ");
    }
	
	public static void displayPaymentMenu() {
        System.out.println( "Payent Menu");
        System.out.println( "1 - Generate Receipt");
        System.out.println( "2 - View Booking History");
        System.out.println( "3 - View Payment History");
        System.out.println( "4 - Quit");
        System.out.print  ( "Your choice: ");
    }
	

}