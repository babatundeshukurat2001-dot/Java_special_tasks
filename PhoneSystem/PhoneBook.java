import java.util.Scanner;


public class PhoneBook {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int maxUsers = 10;
        int maxContacts = 50;

        String[] usernames = new String[maxUsers];
        String[] passwords = new String[maxUsers];
        String[][][] contacts = new String[maxUsers][maxContacts][2]; 
        int[] contactCounts = new int[maxUsers];
        int userCount = 0;

        
        usernames[userCount] = "ope";
        passwords[userCount] = "ope123";
        addContact(contacts, contactCounts, userCount, "Daddy", "08023425964");
        addContact(contacts, contactCounts, userCount, "Mummy", "09039180195");
        userCount++;

        usernames[userCount] = "ola";
        passwords[userCount] = "ola2001";
        addContact(contacts, contactCounts, userCount, "Dad", "08055556666");
        addContact(contacts, contactCounts, userCount, "Tunde", "08077778888");
        userCount++;

        boolean running = true;
        while (running) {
            int currentUser = login(input, usernames, passwords, userCount);
            if (currentUser == -1) {
                System.out.println("Invalid username or password. Goodbye!");
                break;
            }
            System.out.println("Login successful!");
            running = runPhonebookMenu(input, currentUser, usernames, contacts, contactCounts);
        }
        System.out.println("Application closed.");
    }

    static void addContact(String[][][] contacts, int[] contactCounts, int user, String name, String phone) {
        int slot = contactCounts[user];
        contacts[user][slot][0] = name;
        contacts[user][slot][1] = phone;
        contactCounts[user]++;
    }

   
    static int login(Scanner input, String[] usernames, String[] passwords, int userCount) {
        System.out.println("\n===== PHONEBOOK SYSTEM =====");
        System.out.print("Username: ");
        String username = input.nextLine().trim();
        System.out.print("Password: ");
        String password = input.nextLine().trim();

        for (int count = 0; count < userCount; count++) {
            if (usernames[count].equalsIgnoreCase(username) && passwords[count].equals(password)) {
                return count;
            }
        }
        return -1;
    }

   
    static boolean runPhonebookMenu(Scanner input, int currentUser, String[] usernames,
                                    String[][][] contacts, int[] contactCounts) {
        String name = usernames[currentUser];
        System.out.println("Welcome, " + name.substring(0, 1).toUpperCase() + name.substring(1) + "!");

        while (true) {
            System.out.println("\n===== MY PHONEBOOK =====");
            System.out.println("1. View Contacts");
            System.out.println("2. View Contact Details");
            System.out.println("3. Logout");
            System.out.println("0. Exit Application");
            System.out.print("Choose an option: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    viewContacts(currentUser, usernames, contacts, contactCounts);
                    break;
                case "2":
                    viewContactDetails(input, currentUser, usernames, contacts, contactCounts);
                    break;
                case "3":
                    System.out.println("Logged out.");
                    return true;
                case "0":
                    return false;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    static void viewContacts(int user, String[] usernames, String[][][] contacts, int[] contactCounts) {
        System.out.println("\n===== " + usernames[user].toUpperCase() + "'S CONTACTS =====");
        if (contactCounts[user] == 0) {
            System.out.println("No contacts yet.");
            return;
        }
        for (int count = 0; count < contactCounts[user]; count++) {
            System.out.println((count + 1) + ". " + contacts[user][count][0]);
        }
    }

    static void viewContactDetails(Scanner input, int user, String[] usernames,
                                   String[][][] contacts, int[] contactCounts) {
        if (contactCounts[user] == 0) {
            System.out.println("No contacts yet.");
            return;
        }
        viewContacts(user, usernames, contacts, contactCounts);
        System.out.print("Enter contact position: ");
        String text = input.nextLine().trim();

        int position;
        try {
            position = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        if (position < 1 || position > contactCounts[user]) {
            System.out.println("No contact at that position.");
            return;
        }

        System.out.println("\n===== CONTACT DETAILS =====");
        System.out.println("Name: " + contacts[user][position - 1][0]);
        System.out.println("Phone: " + contacts[user][position - 1][1]);
    }
}
