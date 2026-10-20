import java.util.Scanner;

public class ContactApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        String[] names = {"Opeyemi Bababtunde", "Obi Marvellous", "Oni Samiat"};
        String[] phones = {"08023425964", "08059876543", "08123456789"};
        String[] emails = {"ope@gmail.com", "obimarve@gmail.com", "samoni@gmail.com"};

        displayContactList(names);

        
        int position = readPosition(scanner, "Enter contact number to view: ", names.length);

        int index = position - 1;

        displayContactDetails(names[index], phones[index], emails[index]);
    }

    private static void displayContactList(String[] names) {
        System.out.println("====================================");
        System.out.println(" CONTACT MANAGEMENT");
        System.out.println("====================================");
        for (int count = 0; count < names.length; count++) {
            System.out.println((count + 1) + ". " + names[count]);
        }
    }

    private static int readPosition(Scanner scanner, String prompt, int total) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(line);
                if (number >= 1 && number <= total) {
                    return number;
                }
            } catch (NumberFormatException e) {
               
            }
            System.out.println("Invalid choice. Enter a number from 1 to " + total + ".");
        }
    }

    private static void displayContactDetails(String name, String phone, String email) {
        System.out.println("------------------------------------");
        System.out.println(" CONTACT DETAILS");
        System.out.println("------------------------------------");
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("------------------------------------");
    }
}
