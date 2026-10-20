import java.util.Arrays;
import java.util.Scanner;

public class ContactManagementSystem {

   public static final Scanner SCANNER = new Scanner(System.in);

   
    public static class InitialData {
    public static final String[] GROUPS = {"Family", "Friends", "Work", "School"};
    public static final String[][] CONTACTS = {
                {"Opeyemi Babatunde", "08031234567", "ope@gmail.com", "Friends", "yes"},
                {"Obi Marvellous", "08059876543", "obimarve@gmail.com", "Family", "yes"},
                {"Oni Samiat", "08123456789", "samoni@gmail.com", "Work", "no"},
        };
    }

    public static String[] names = new String[10];
    public static String[] phones = new String[10];     
    public static String[] emails = new String[10];     
    public static String[] contactGroups = new String[10];
    public static boolean[] favourites = new boolean[10];
    public static String[] addresses = new String[10];  
    public static String[] birthdays = new String[10];
    public static String[] companies = new String[10];
    public static String[] notes = new String[10];
    public static int contactCount = 0;

    
    public static String[] groupNames = new String[10];
    public static int groupCount = 0;

    public static void main(String[] args) {
        startUp();
        runMenu();
        System.out.println("Goodbye!");
    }

    
    public  static void startUp() {
        System.out.println("Welcome to the Contact Management System");
        System.out.println("1. Start with the predefined contacts");
        System.out.println("2. Enter my own contacts");
        int choice = readInt("Choose an option: ", 1, 2);

        for (int i = 0; i < InitialData.GROUPS.length; i++) {
            addGroupName(InitialData.GROUPS[i]);
        }

        if (choice == 1) {
            loadPredefinedContacts();
        } else {
            int total = readInt("How many contacts do you want to enter? ", 0, 1000);
            for (int i = 1; i <= total; i++) {
                String name = readRequired("Enter contact " + i + " name: ");
                String phone = readUniquePhone("Enter phone number: ", -1);
                String email = readOptional("Enter email address (press Enter to skip): ");
                addContact(name, phone, email, "", false, "", "", "", "");
            }
        }
    }

    public  static void loadPredefinedContacts() {
        for (int i = 0; i < InitialData.CONTACTS.length; i++) {
            String[] row = InitialData.CONTACTS[i];
            addContact(row[0], row[1], row[2], row[3], row[4].equalsIgnoreCase("yes"),
                    "", "", "", "");
        }
    }

    
    public static void runMenu() {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("====================================");
            System.out.println(" CONTACT MANAGEMENT");
            System.out.println("====================================");
            System.out.println("1. Display all contacts");
            System.out.println("2. Display all contacts with full details");
            System.out.println("3. View contact details");
            System.out.println("4. Add a contact");
            System.out.println("5. Search by name");
            System.out.println("6. Search by phone number");
            System.out.println("7. Edit contact");
            System.out.println("8. Delete contact");
            System.out.println("9. Display contact statistics");
            System.out.println("10. Sort contacts");
            System.out.println("11. Manage groups");
            System.out.println("12. Display contacts by group or letter");
            System.out.println("13. Favourite contacts");
            System.out.println("14. Find contacts sharing a phone number or email");
            System.out.println("15. Exit");
            int choice = readInt("Choose an option: ", 1, 15);

            switch (choice) {
                case 1: displayAllNames(); break;
                case 2: displayAllFullDetails(); break;
                case 3: viewContactDetails(); break;
                case 4: addContactFromUser(); break;
                case 5: searchByName(); break;
                case 6: searchByPhone(); break;
                case 7: editContact(); break;
                case 8: deleteContact(); break;
                case 9: displayStatistics(); break;
                case 10: sortContacts(); break;
                case 11: manageGroups(); break;
                case 12: displayByGroupOrLetter(); break;
                case 13: favouriteMenu(); break;
                case 14: findSharedContacts(); break;
                default: running = false;
            }
        }
    }

    
    public  static void ensureCapacity() {
        if (contactCount == names.length) {
            int size = names.length * 2;
            names = Arrays.copyOf(names, size);
            phones = Arrays.copyOf(phones, size);
            emails = Arrays.copyOf(emails, size);
            contactGroups = Arrays.copyOf(contactGroups, size);
            favourites = Arrays.copyOf(favourites, size);
            addresses = Arrays.copyOf(addresses, size);
            birthdays = Arrays.copyOf(birthdays, size);
            companies = Arrays.copyOf(companies, size);
            notes = Arrays.copyOf(notes, size);
        }
    }

    public  static void addContact(String name, String phone, String email, String group,
                                   boolean favourite, String address, String birthday,
                                   String company, String note) {
        ensureCapacity();
        names[contactCount] = name;
        phones[contactCount] = phone;
        emails[contactCount] = email;
        contactGroups[contactCount] = group;
        favourites[contactCount] = favourite;
        addresses[contactCount] = address;
        birthdays[contactCount] = birthday;
        companies[contactCount] = company;
        notes[contactCount] = note;
        contactCount++;
    }

   public static void removeContactAt(int index) {
        for (int i = index; i < contactCount - 1; i++) {
            copyContact(i + 1, i);
        }
        int last = contactCount - 1;
        names[last] = null;
        phones[last] = null;
        emails[last] = null;
        contactGroups[last] = null;
        favourites[last] = false;
        addresses[last] = null;
        birthdays[last] = null;
        companies[last] = null;
        notes[last] = null;
        contactCount--;
    }

    public  static void copyContact(int from, int to) {
        names[to] = names[from];
        phones[to] = phones[from];
        emails[to] = emails[from];
        contactGroups[to] = contactGroups[from];
        favourites[to] = favourites[from];
        addresses[to] = addresses[from];
        birthdays[to] = birthdays[from];
        companies[to] = companies[from];
        notes[to] = notes[from];
    }

    public  static void swapContacts(int a, int b) {
        String tName = names[a]; names[a] = names[b]; names[b] = tName;
        String tPhone = phones[a]; phones[a] = phones[b]; phones[b] = tPhone;
        String tEmail = emails[a]; emails[a] = emails[b]; emails[b] = tEmail;
        String tGroup = contactGroups[a]; contactGroups[a] = contactGroups[b]; contactGroups[b] = tGroup;
        boolean tFav = favourites[a]; favourites[a] = favourites[b]; favourites[b] = tFav;
        String tAddr = addresses[a]; addresses[a] = addresses[b]; addresses[b] = tAddr;
        String tBirth = birthdays[a]; birthdays[a] = birthdays[b]; birthdays[b] = tBirth;
        String tComp = companies[a]; companies[a] = companies[b]; companies[b] = tComp;
        String tNote = notes[a]; notes[a] = notes[b]; notes[b] = tNote;
    }

    
    public static boolean hasContacts() {
        if (contactCount == 0) {
            System.out.println("There are no contacts yet.");
            return false;
        }
        return true;
    }

    public static void displayAllNames() {
        if (!hasContacts()) return;
        System.out.println("------------------------------------");
        System.out.println(" CONTACT LIST");
        System.out.println("------------------------------------");
        for (int i = 0; i < contactCount; i++) {
            System.out.println((i + 1) + ". " + names[i] + (favourites[i] ? " *" : ""));
        }
        System.out.println("(* = favourite)");
    }

    public static void displayAllFullDetails() {
        if (!hasContacts()) return;
        System.out.println("CONTACT LIST");
        for (int i = 0; i < contactCount; i++) {
            System.out.println((i + 1) + ". " + oneLine(i));
        }
    }

    public static String oneLine(int i) {
        String email = emails[i].isEmpty() ? "-" : emails[i];
        return names[i] + " Phone: " + phones[i] + " Email: " + email;
    }

    public static void viewContactDetails() {
        if (!hasContacts()) return;
        displayAllNames();
        int index = readInt("Enter contact number to view: ", 1, contactCount) - 1;
        printDetails(index);
    }

    public static String valueOrDash(String value) {
        return value.isEmpty() ? "-" : value;
    }

    public static void printDetails(int i) {
        System.out.println("------------------------------------");
        System.out.println(" CONTACT DETAILS");
        System.out.println("------------------------------------");
        System.out.println("Name: " + names[i]);
        System.out.println("Phone: " + phones[i]);
        System.out.println("Email: " + valueOrDash(emails[i]));
        System.out.println("Group: " + valueOrDash(contactGroups[i]));
        System.out.println("Favourite: " + (favourites[i] ? "Yes" : "No"));
        System.out.println("Address: " + valueOrDash(addresses[i]));
        System.out.println("Date of birth: " + valueOrDash(birthdays[i]));
        System.out.println("Company: " + valueOrDash(companies[i]));
        System.out.println("Notes: " + valueOrDash(notes[i]));
        System.out.println("------------------------------------");
    }

   
    public static void addContactFromUser() {
        String name = readRequired("Enter full name: ");
        String phone = readUniquePhone("Enter phone number: ", -1);
        String email = readOptional("Enter email address (press Enter to skip): ");
        String group = chooseGroupOrNone();
        String address = readOptional("Enter home address (press Enter to skip): ");
        String birthday = readOptional("Enter date of birth (press Enter to skip): ");
        String company = readOptional("Enter company/organization (press Enter to skip): ");
        String note = readOptional("Enter notes (press Enter to skip): ");
        addContact(name, phone, email, group, false, address, birthday, company, note);
        System.out.println("Contact added successfully!");
    }

    public static int findPhone(String phone) {
        for (int i = 0; i < contactCount; i++) {
            if (phones[i].equals(phone)) {
                return i;
            }
        }
        return -1;
    }

   
    public static String readUniquePhone(String prompt, int skipIndex) {
        while (true) {
            String phone = readPhone(prompt);
            int owner = findPhone(phone);
            if (owner == -1 || owner == skipIndex) {
                return phone;
            }
            System.out.println("A contact with this phone number already exists ("
                    + names[owner] + "). Duplicate not added - please enter a different number.");
        }
    }

   
    public static void searchByName() {
        if (!hasContacts()) return;
        String term = readRequired("Enter name to search: ").toLowerCase();
        int found = 0;
        for (int i = 0; i < contactCount; i++) {
            if (names[i].toLowerCase().contains(term)) {
                found++;
                System.out.println(found == 1 ? "Contact found!" : "Another match:");
                printSimple(i);
            }
        }
        if (found == 0) {
            System.out.println("No contact found matching \"" + term + "\".");
        } else if (found > 1) {
            System.out.println(found + " contacts matched.");
        }
    }

    public static void searchByPhone() {
        if (!hasContacts()) return;
        String phone = readRequired("Enter phone number to search: ");
        int found = 0;
        for (int i = 0; i < contactCount; i++) {
            if (phones[i].equals(phone)) {
                found++;
                System.out.println("Contact found!");
                printSimple(i);
            }
        }
        if (found == 0) {
            System.out.println("No contact found with phone number " + phone + ".");
        }
    }

    private static void printSimple(int i) {
        System.out.println("Name: " + names[i]);
        System.out.println("Phone: " + phones[i]);
        System.out.println("Email: " + valueOrDash(emails[i]));
    }

    
    private static void editContact() {
        if (!hasContacts()) return;
        displayAllNames();
        int i = readInt("Select contact: ", 1, contactCount) - 1;

        System.out.println("1. Change name");
        System.out.println("2. Change phone number");
        System.out.println("3. Change email address");
        System.out.println("4. Change all details (name, phone, email)");
        System.out.println("5. Change group");
        System.out.println("6. Change home address");
        System.out.println("7. Change date of birth");
        System.out.println("8. Change company/organization");
        System.out.println("9. Change notes");
        int choice = readInt("Choose an option: ", 1, 9);

        switch (choice) {
            case 1:
                names[i] = readRequired("Enter new name: ");
                break;
            case 2:
                phones[i] = readUniquePhone("Enter new phone number: ", i);
                break;
            case 3:
                emails[i] = readOptional("Enter new email address (Enter to clear): ");
                break;
            case 4:
                names[i] = readRequired("Enter new name: ");
                phones[i] = readUniquePhone("Enter new phone number: ", i);
                emails[i] = readOptional("Enter new email address (Enter to clear): ");
                break;
            case 5:
                contactGroups[i] = chooseGroupOrNone();
                break;
            case 6:
                addresses[i] = readOptional("Enter new home address (Enter to clear): ");
                break;
            case 7:
                birthdays[i] = readOptional("Enter new date of birth (Enter to clear): ");
                break;
            case 8:
                companies[i] = readOptional("Enter new company/organization (Enter to clear): ");
                break;
            default:
                notes[i] = readOptional("Enter new notes (Enter to clear): ");
        }
        System.out.println("Contact updated successfully!");
    }

    
    public static void deleteContact() {
        if (!hasContacts()) return;
        displayAllNames();
        int i = readInt("Select contact to delete: ", 1, contactCount) - 1;
        printDetails(i);
        if (readYesNo("Are you sure you want to delete this contact? (yes/no): ")) {
            removeContactAt(i);
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

   
    public static void displayStatistics() {
        int withEmail = 0;
        int favouriteTotal = 0;
        int withGroup = 0;
        for (int i = 0; i < contactCount; i++) {
            if (!emails[i].isEmpty()) withEmail++;
            if (favourites[i]) favouriteTotal++;
            if (!contactGroups[i].isEmpty()) withGroup++;
        }
        System.out.println("CONTACT STATISTICS");
        System.out.println("Total contacts: " + contactCount);
        System.out.println("Contacts with email addresses: " + withEmail);
        System.out.println("Contacts without email addresses: " + (contactCount - withEmail));
        System.out.println("Favourite contacts: " + favouriteTotal);
        System.out.println("Contacts in a group: " + withGroup);
        System.out.println("Contacts without a group: " + (contactCount - withGroup));
        for (int g = 0; g < groupCount; g++) {
            int members = 0;
            for (int i = 0; i < contactCount; i++) {
                if (contactGroups[i].equals(groupNames[g])) members++;
            }
            System.out.println("  " + groupNames[g] + ": " + members);
        }
    }

   
    public static void sortContacts() {
        if (!hasContacts()) return;
        for (int i = 0; i < contactCount - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j < contactCount; j++) {
                if (names[j].compareToIgnoreCase(names[smallest]) < 0) {
                    smallest = j;
                }
            }
            if (smallest != i) {
                swapContacts(i, smallest);
            }
        }
        System.out.println("Contacts sorted alphabetically.");
        displayAllNames();
    }

    
    public static void addGroupName(String name) {
        if (groupCount == groupNames.length) {
            groupNames = Arrays.copyOf(groupNames, groupNames.length * 2);
        }
        groupNames[groupCount++] = name;
    }

       public  static int findGroup(String name) {
        for (int g = 0; g < groupCount; g++) {
            if (groupNames[g].equalsIgnoreCase(name)) return g;
        }
        return -1;
    }

   public static void printGroups() {
        System.out.println("CONTACT GROUPS");
        for (int g = 0; g < groupCount; g++) {
            System.out.println((g + 1) + ". " + groupNames[g]);
        }
    }

   
    public static String chooseGroupOrNone() {
        if (groupCount == 0) return "";
        printGroups();
        System.out.println("0. No group");
        int choice = readInt("Choose a group: ", 0, groupCount);
        return choice == 0 ? "" : groupNames[choice - 1];
    }

   public static void manageGroups() {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("GROUP MANAGEMENT");
            System.out.println("1. View groups");
            System.out.println("2. Create a group");
            System.out.println("3. Rename a group");
            System.out.println("4. Remove a group");
            System.out.println("5. Assign a contact to a group");
            System.out.println("6. Back to main menu");
            int choice = readInt("Choose an option: ", 1, 6);

            switch (choice) {
                case 1:
                    if (groupCount == 0) System.out.println("There are no groups.");
                    else printGroups();
                    break;
                case 2: createGroup(); break;
                case 3: renameGroup(); break;
                case 4: removeGroup(); break;
                case 5: assignContactToGroup(); break;
                default: back = true;
            }
        }
    }

    public static void createGroup() {
        String name = readRequired("Enter new group name: ");
        if (findGroup(name) != -1) {
            System.out.println("That group already exists.");
            return;
        }
        addGroupName(name);
        System.out.println("Group created successfully!");
    }

    public static void renameGroup() {
        if (groupCount == 0) {
            System.out.println("There are no groups.");
            return;
        }
        printGroups();
        int g = readInt("Choose a group to rename: ", 1, groupCount) - 1;
        String newName = readRequired("Enter new group name: ");
        int existing = findGroup(newName);
        if (existing != -1 && existing != g) {
            System.out.println("Another group already uses that name.");
            return;
        }
        String oldName = groupNames[g];
        groupNames[g] = newName;
        
        for (int i = 0; i < contactCount; i++) {
            if (contactGroups[i].equals(oldName)) contactGroups[i] = newName;
        }
        System.out.println("Group renamed successfully!");
    }

   public static void removeGroup() {
        if (groupCount == 0) {
            System.out.println("There are no groups.");
            return;
        }
        printGroups();
        int g = readInt("Choose a group to remove: ", 1, groupCount) - 1;
        String name = groupNames[g];
        int members = 0;
        for (int i = 0; i < contactCount; i++) {
            if (contactGroups[i].equals(name)) members++;
        }
       
        String question = "Remove \"" + name + "\"? " + members
                + " contact(s) will be left without a group (yes/no): ";
        if (!readYesNo(question)) {
            System.out.println("Removal cancelled.");
            return;
        }
        for (int i = 0; i < contactCount; i++) {
            if (contactGroups[i].equals(name)) contactGroups[i] = "";
        }
        for (int k = g; k < groupCount - 1; k++) {
            groupNames[k] = groupNames[k + 1];
        }
        groupNames[--groupCount] = null;
        System.out.println("Group removed successfully!");
    }

    public static void assignContactToGroup() {
        if (!hasContacts()) return;
        displayAllNames();
        int i = readInt("Select contact: ", 1, contactCount) - 1;
        contactGroups[i] = chooseGroupOrNone();
        System.out.println("Contact updated successfully!");
    }

  
    public static void displayByGroupOrLetter() {
        if (!hasContacts()) return;
        System.out.println("1. Display by group");
        System.out.println("2. Display by first letter of name");
        int choice = readInt("Choose an option: ", 1, 2);

        if (choice == 1) {
            if (groupCount == 0) {
                System.out.println("There are no groups.");
                return;
            }
            printGroups();
            int g = readInt("Choose a group: ", 1, groupCount) - 1;
            System.out.println(groupNames[g].toUpperCase());
            int shown = 0;
            for (int i = 0; i < contactCount; i++) {
                if (contactGroups[i].equals(groupNames[g])) {
                    shown++;
                    System.out.println(shown + ". " + oneLine(i));
                }
            }
            if (shown == 0) System.out.println("No contacts in this group.");
        } else {
            String letter = readRequired("Enter a letter: ").substring(0, 1);
            int shown = 0;
            for (int i = 0; i < contactCount; i++) {
                if (names[i].substring(0, 1).equalsIgnoreCase(letter)) {
                    shown++;
                    System.out.println(shown + ". " + oneLine(i));
                }
            }
            if (shown == 0) System.out.println("No contacts begin with \"" + letter + "\".");
        }
    }

    public static void favouriteMenu() {
        System.out.println("1. Mark a contact as a favourite");
        System.out.println("2. Remove a contact from favourites");
        System.out.println("3. Display all favourite contacts");
        int choice = readInt("Choose an option: ", 1, 3);

        if (choice == 3) {
            displayFavourites();
            return;
        }
        if (!hasContacts()) return;
        displayAllNames();
        int i = readInt("Select contact: ", 1, contactCount) - 1;
        favourites[i] = (choice == 1);
        System.out.println(choice == 1 ? "Added to favourites!" : "Removed from favourites!");
    }

    public static void displayFavourites() {
        System.out.println("FAVOURITE CONTACTS");
        int shown = 0;
        for (int i = 0; i < contactCount; i++) {
            if (favourites[i]) {
                shown++;
                System.out.println(shown + ". " + names[i]);
            }
        }
        if (shown == 0) System.out.println("You have no favourite contacts.");
    }

    
    public static void findSharedContacts() {
        int matches = 0;
        for (int i = 0; i < contactCount; i++) {
            for (int j = i + 1; j < contactCount; j++) {
                boolean samePhone = phones[i].equals(phones[j]);
                boolean sameEmail = !emails[i].isEmpty() && emails[i].equalsIgnoreCase(emails[j]);
                if (samePhone || sameEmail) {
                    matches++;
                    String reason = samePhone && sameEmail ? "phone number and email address"
                            : samePhone ? "phone number" : "email address";
                    System.out.println("Shared " + reason + ":");
                    System.out.println("  " + (i + 1) + ". " + oneLine(i));
                    System.out.println("  " + (j + 1) + ". " + oneLine(j));
                }
            }
        }
        if (matches == 0) System.out.println("No contacts share a phone number or email address.");
    }

   
    public static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            try {
                int number = Integer.parseInt(line);
                if (number >= min && number <= max) return number;
            } catch (NumberFormatException e) {
                // fall through
            }
            System.out.println("Invalid input. Enter a number from " + min + " to " + max + ".");
        }
    }

    public static String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.println("This field cannot be empty.");
        }
    }

   public static String readOptional(String prompt) {
        System.out.print(prompt);
        return SCANNER.nextLine().trim();
    }

    public static String readPhone(String prompt) {
        while (true) {
            String phone = readRequired(prompt);
            if (phone.matches("\\+?[0-9]{7,15}")) return phone;
            System.out.println("Phone number must contain 7 to 15 digits (optionally starting with +).");
        }
    }

    public static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = SCANNER.nextLine().trim().toLowerCase();
            if (line.equals("yes") || line.equals("y")) return true;
            if (line.equals("no") || line.equals("n")) return false;
            System.out.println("Please type yes or no.");
        }
    }
}
