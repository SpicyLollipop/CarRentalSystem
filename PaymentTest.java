import java.util.Scanner;
public class PaymentTest{
	


	public static void main(String[] args) {
        PaymentSystem paymentSystem = new PaymentSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu.displayPaymentMenu(); // Call the displayMenu method from the Menu class
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter IC of the customer for the receipt: ");
                    String icForReceipt = scanner.nextLine();
                    paymentSystem.generateCustomerReceipt(icForReceipt);
                    break;
                case 2:
                    System.out.println("Booking History:");
                    // Implement the logic to display booking history
                    break;
                case 3:
                    System.out.println("Payment History:");
                    // Implement the logic to display payment history
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
