import java.util.Scanner;

public class CreditCardValidator {

    private static final String BANNER = "*".repeat(40);

    public static String getCardType(String cardNumber) {
        if (cardNumber.startsWith("37")) {
            return "American Express";
        }
        if (cardNumber.startsWith("4")) {
            return "Visa";
        }
        if (cardNumber.startsWith("5")) {
            return "MasterCard";
        }
        if (cardNumber.startsWith("6")) {
            return "Discover";
        }
        return "Invalid Card";
    }

    public static int sumOfDoubleEvenPlaces(String cardNumber) {
        int total = 0;
        for (int count = cardNumber.length() - 2; count >= 0; count -= 2) {
            int doubled = (cardNumber.charAt(count) - '0') * 2;
            total += doubled > 9 ? doubled / 10 + doubled % 10 : doubled;
        }
        return total;
    }

   
    public static int sumOfOddPlaces(String cardNumber) {
        int total = 0;
        for (int count = cardNumber.length() - 1; count >= 0; count -= 2) {
            total += cardNumber.charAt(count) - '0';
        }
        return total;
    }

    
    public static boolean passesLuhnCheck(String cardNumber) {
        return (sumOfDoubleEvenPlaces(cardNumber) + sumOfOddPlaces(cardNumber)) % 10 == 0;
    }

    private static boolean isAllDigits(String s) {
        if (s.isEmpty()) {
            return false;
        }
        for (int count = 0; count < s.length(); count++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(String cardNumber) {
        if (!isAllDigits(cardNumber)) {
            return false;
        }
        if (cardNumber.length() < 13 || cardNumber.length() > 16) {
            return false;
        }
        if (getCardType(cardNumber).equals("Invalid Card")) {
            return false;
        }
        return passesLuhnCheck(cardNumber);
    }

    public static String formatResult(String cardNumber) {
        String type = getCardType(cardNumber);
        String status = isValid(cardNumber) ? "Valid" : "Invalid";
        return String.join("\n",
                BANNER,
                "**Credit Card Type: " + type,
                "**Credit Card Number: " + cardNumber,
                "**Credit Card Digit Length: " + cardNumber.length(),
                "**Credit Card Validity Status: " + status,
                BANNER);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, Kindly Enter Card details to verify");
        String cardNumber = scanner.nextLine().trim();
        System.out.println(formatResult(cardNumber));
       
    }
}
