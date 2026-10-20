import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class CinemaManagementSystem {

    
   public static String[] movieNames;
   public static char[][][] seats;                 
   public static String[][][] passengerNames;      
   public static String[][][] reservationNumbers;  
   public static Date[][][] reservationTimes;     

   public static int reservationCounter = 0;
   public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        movieNames = new String[0];
        seats = new char[0][][];
        passengerNames = new String[0][][];
        reservationNumbers = new String[0][][];
        reservationTimes = new Date[0][][];

        setupMovies();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Choose an option: ");
            System.out.println();

            switch (choice) {
                case 1: viewAllMovies(); break;
                case 2: viewSeatingArrangement(); break;
                case 3: reserveSeats(); break;
                case 4: checkRowOrColumn(); break;
                case 5: viewStatistics(); break;
                case 6: addMovieFromUser(); break;
                case 7: renameMovie(); break;
                case 8: changeDimensions(); break;
                case 9: removeMovie(); break;
                case 10: searchMovie(); break;
                case 11: cancelReservation(); break;
                case 12:
                    System.out.println("Thank you for using the Cinema Management System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println();
        }
    }

   
   public static void setupMovies() {
        System.out.println("Welcome to the Cinema Management System");
        System.out.println("1. Start with the default movies");
        System.out.println("2. Enter my own movies");
        int choice = readInt("Choose: ");

        if (choice == 2) {
            int count = readPositiveInt("How many movies will the cinema show? ");
            for (int index = 1; index <= count; index++) {
                System.out.print("Enter movie " + index + " name: ");
                String name = readNonEmptyLine();
                int rows = readPositiveInt("Enter number of rows: ");
                int cols = readPositiveInt("Enter seats per row: ");
                addMovie(name, rows, cols);
            }
        } else {
            String[] defaultNames = {"The Lion Adventure", "Space Explorers", "Mystery at Midnight"};
            int[] defaultRows = {4, 5, 6};
            int[] defaultCols = {5, 6, 4};
            for (int count = 0; count < defaultNames.length; count++) {
                addMovie(defaultNames[count], defaultRows[count], defaultCols[count]);
            }
        }
        System.out.println();
    }

   

   public static void printMenu() {
        System.out.println("====================================");
        System.out.println(" CINEMA MANAGEMENT SYSTEM");
        System.out.println("====================================");
        System.out.println("1. View all movies");
        System.out.println("2. View a movie's seating arrangement");
        System.out.println("3. Reserve a seat");
        System.out.println("4. Check row or column availability");
        System.out.println("5. View movie statistics");
        System.out.println("6. Add a movie");
        System.out.println("7. Rename a movie");
        System.out.println("8. Change seating dimensions");
        System.out.println("9. Remove a movie");
        System.out.println("10. Search for a movie");
        System.out.println("11. Cancel a reservation");
        System.out.println("12. Exit");
    }

    
   public static void createSeatTables(int m, int rows, int cols) {
        seats[m] = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                seats[m][r][c] = 'O';
            }
        }
        passengerNames[m] = new String[rows][cols];
        reservationNumbers[m] = new String[rows][cols];
        reservationTimes[m] = new Date[rows][cols];
    }

   
   public static void addMovie(String name, int rows, int cols) {
        int n = movieNames.length;
        movieNames = Arrays.copyOf(movieNames, n + 1);
        seats = Arrays.copyOf(seats, n + 1);
        passengerNames = Arrays.copyOf(passengerNames, n + 1);
        reservationNumbers = Arrays.copyOf(reservationNumbers, n + 1);
        reservationTimes = Arrays.copyOf(reservationTimes, n + 1);

        movieNames[n] = name;
        createSeatTables(n, rows, cols);
    }

   
   public static void removeMovieAt(int index) {
        int n = movieNames.length;
        String[] newNames = new String[n - 1];
        char[][][] newSeats = new char[n - 1][][];
        String[][][] newPassengers = new String[n - 1][][];
        String[][][] newNumbers = new String[n - 1][][];
        Date[][][] newTimes = new Date[n - 1][][];

        int j = 0;
        for (int i = 0; i < n; i++) {
            if (i == index) continue;
            newNames[j] = movieNames[i];
            newSeats[j] = seats[i];
            newPassengers[j] = passengerNames[i];
            newNumbers[j] = reservationNumbers[i];
            newTimes[j] = reservationTimes[i];
            j++;
        }
        movieNames = newNames;
        seats = newSeats;
        passengerNames = newPassengers;
        reservationNumbers = newNumbers;
        reservationTimes = newTimes;
    }

   

  public static int totalSeats(int m) {
        return seats[m].length * seats[m][0].length;
    }

   public static int reservedSeats(int m) {
        int count = 0;
        for (int r = 0; r < seats[m].length; r++) {
            for (int c = 0; c < seats[m][r].length; c++) {
                if (seats[m][r][c] == 'X') count++;
            }
        }
        return count;
    }

   

   public static void showMovieNames() {
        System.out.println("Available Movies");
        for (int i = 0; i < movieNames.length; i++) {
            System.out.println((i + 1) + ". " + movieNames[i]);
        }
    }

    
   public static void viewAllMovies() {
        if (noMovies()) return;
        System.out.println("MOVIES CURRENTLY SHOWING");
        for (int i = 0; i < movieNames.length; i++) {
            System.out.println("Screen " + (i + 1) + ": " + movieNames[i]);
            System.out.println("  Rows: " + seats[i].length);
            System.out.println("  Seats per row: " + seats[i][0].length);
            System.out.println("  Total seats: " + totalSeats(i));
        }
    }

   public static void displaySeats(int m) {
        System.out.println("Movie: " + movieNames[m]);
        System.out.print("       ");
        for (int c = 0; c < seats[m][0].length; c++) {
            System.out.print(String.format("%-3d", c + 1));
        }
        System.out.println();
        for (int r = 0; r < seats[m].length; r++) {
            System.out.print("Row " + String.format("%-3d", r + 1));
            for (int c = 0; c < seats[m][r].length; c++) {
                System.out.print(String.format("%-3s", seats[m][r][c]));
            }
            System.out.println();
        }
        System.out.println("O = Available, X = Reserved");
    }

    static void viewSeatingArrangement() {
        int m = selectMovie();
        if (m == -1) return;
        System.out.println();
        displaySeats(m);
    }

    
   public static void reserveSeats() {
        int m = selectMovie();
        if (m == -1) return;

        boolean again = true;
        while (again) {
            System.out.println();
            displaySeats(m);

            System.out.print("Enter passenger's full name: ");
            String name = readNonEmptyLine();
            int row = readInt("Enter row number: ");
            int seat = readInt("Enter seat number: ");

            if (!validSeat(m, row, seat)) {
                System.out.println("Invalid seat. This screen has " + seats[m].length
                        + " rows and " + seats[m][0].length + " seats per row.");
            } else if (seats[m][row - 1][seat - 1] == 'X') {
                // Challenge 3: no double booking, no receipt
                System.out.println("Sorry, row " + row + " seat " + seat
                        + " is already reserved. Please choose another seat.");
            } else {
                reservationCounter++;
                int r = row - 1;   
                int c = seat - 1;
                seats[m][r][c] = 'X';
                passengerNames[m][r][c] = name;
                reservationNumbers[m][r][c] = String.format("CM-%04d", reservationCounter);
                reservationTimes[m][r][c] = new Date();

                System.out.println("Seat reserved successfully!");
                System.out.println();
                printReceipt(m, r, c);
                System.out.println();
                System.out.println("Updated seating arrangement:");
                displaySeats(m);
            }

            System.out.print("Reserve another seat for this movie? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();
            again = answer.equals("y") || answer.equals("yes");
        }
    }

    public static void printReceipt(int m, int r, int c) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        Date time = reservationTimes[m][r][c];

        System.out.println("====================================");
        System.out.println(" CINEMA RESERVATION");
        System.out.println("====================================");
        System.out.println("Reservation No: " + reservationNumbers[m][r][c]);
        System.out.println("Passenger: " + passengerNames[m][r][c]);
        System.out.println("Movie: " + movieNames[m]);
        System.out.println("Screen: " + (m + 1));
        System.out.println("Seat: Row " + (r + 1) + ", Seat " + (c + 1));
        System.out.println("Date: " + dateFormat.format(time));
        System.out.println("Time: " + timeFormat.format(time));
        System.out.println("Status: CONFIRMED");
        System.out.println("====================================");
    }

   
   public static void checkRowOrColumn() {
        int m = selectMovie();
        if (m == -1) return;

        System.out.println("1. Check a row");
        System.out.println("2. Check a seat column");
        int choice = readInt("Choose: ");
        int available = 0;

        if (choice == 1) {
            int row = readInt("Enter row number: ");
            if (row < 1 || row > seats[m].length) {
                System.out.println("Invalid row.");
                return;
            }
            System.out.println("Row " + row + " of " + movieNames[m] + ":");
            for (int c = 0; c < seats[m][row - 1].length; c++) {
                char state = seats[m][row - 1][c];
                System.out.println("  Seat " + (c + 1) + ": " + state);
                if (state == 'O') available++;
            }
            System.out.println("Available seats in this row: " + available);
        } else if (choice == 2) {
            int col = readInt("Enter seat (column) number: ");
            if (col < 1 || col > seats[m][0].length) {
                System.out.println("Invalid seat number.");
                return;
            }
            System.out.println("Seat " + col + " of " + movieNames[m] + ":");
            for (int r = 0; r < seats[m].length; r++) {
                char state = seats[m][r][col - 1];
                System.out.println("  Row " + (r + 1) + ": " + state);
                if (state == 'O') available++;
            }
            System.out.println("Available seats in this column: " + available);
        } else {
            System.out.println("Invalid choice.");
        }
    }

   

   public static void viewStatistics() {
        if (noMovies()) return;
        System.out.println("1. Statistics for one movie");
        System.out.println("2. Statistics for every movie");
        int choice = readInt("Choose: ");

        if (choice == 1) {
            int m = selectMovie();
            if (m != -1) printStatistics(m);
        } else if (choice == 2) {
            for (int i = 0; i < movieNames.length; i++) {
                printStatistics(i);
                System.out.println();
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

   public static void printStatistics(int m) {
        int total = totalSeats(m);
        int reserved = reservedSeats(m);
        int available = total - reserved;
        double percentage = reserved * 100.0 / total;

        System.out.println("Statistics for " + movieNames[m] + ":");
        System.out.println("  Total seats: " + total);
        System.out.println("  Reserved seats: " + reserved);
        System.out.println("  Available seats: " + available);
        System.out.println("  Percentage reserved: " + String.format("%.1f", percentage) + "%");
    }

    
   public static void addMovieFromUser() {
        System.out.print("Enter movie name: ");
        String name = readNonEmptyLine();
        int rows = readPositiveInt("Enter number of rows: ");
        int cols = readPositiveInt("Enter seats per row: ");
        addMovie(name, rows, cols);
        System.out.println("Movie added successfully!");
    }

    
   public static void renameMovie() {
        int m = selectMovie();
        if (m == -1) return;
        System.out.print("Enter new name for \"" + movieNames[m] + "\": ");
        String newName = readNonEmptyLine();
        movieNames[m] = newName;
        System.out.println("Movie renamed successfully!");
    }

    
   public static void changeDimensions() {
        int m = selectMovie();
        if (m == -1) return;

        if (reservedSeats(m) > 0) {
            System.out.println("Cannot change dimensions: this movie has " + reservedSeats(m)
                    + " reservation(s). Cancel them first.");
            return;
        }
        System.out.println("Current size: " + seats[m].length + " rows x " + seats[m][0].length + " seats per row");
        int rows = readPositiveInt("Enter new number of rows: ");
        int cols = readPositiveInt("Enter new seats per row: ");
        createSeatTables(m, rows, cols);
        System.out.println("Seating dimensions updated. All seats are available.");
    }

   
   public static void removeMovie() {
        int m = selectMovie();
        if (m == -1) return;

        System.out.println();
        System.out.println("Movie: " + movieNames[m]);
        System.out.println("Screen: " + (m + 1));
        System.out.println("Rows: " + seats[m].length + ", Seats per row: " + seats[m][0].length);
        System.out.println("Total seats: " + totalSeats(m) + ", Reserved: " + reservedSeats(m));

        if (reservedSeats(m) > 0) {
            System.out.println("WARNING: this movie has existing reservations that will be lost!");
        }
        System.out.print("Are you sure you want to remove it? (y/n): ");
        String answer = scanner.nextLine().trim().toLowerCase();

        if (answer.equals("y") || answer.equals("yes")) {
            String removedName = movieNames[m];
            removeMovieAt(m);
            System.out.println("\"" + removedName + "\" has been removed.");
        } else {
            System.out.println("Removal cancelled.");
        }
    }

    

   public static void searchMovie() {
        if (noMovies()) return;
        System.out.print("Enter movie name (or part of it): ");
        String keyword = readNonEmptyLine().toLowerCase();

        boolean found = false;
        for (int i = 0; i < movieNames.length; i++) {
            if (movieNames[i].toLowerCase().contains(keyword)) {
                found = true;
                System.out.println();
                System.out.println("Screen " + (i + 1) + ": " + movieNames[i]);
                System.out.println("  Rows: " + seats[i].length + ", Seats per row: " + seats[i][0].length);
                System.out.println("  Available seats: " + (totalSeats(i) - reservedSeats(i)) + " of " + totalSeats(i));
                displaySeats(i);
            }
        }
        if (!found) {
            System.out.println("No movie found matching \"" + keyword + "\".");
        }
    }

   

   public static void cancelReservation() {
        if (noMovies()) return;
        System.out.println("1. Cancel using reservation number");
        System.out.println("2. Cancel by choosing the seat");
        int choice = readInt("Choose: ");

        int m = -1;
        int r = -1;
        int c = -1;

        if (choice == 1) {
            System.out.print("Enter reservation number (e.g., CM-0001): ");
            String number = readNonEmptyLine().toUpperCase();
            // look through every movie, row and seat for that number
            for (int i = 0; i < movieNames.length; i++) {
                for (int row = 0; row < seats[i].length; row++) {
                    for (int col = 0; col < seats[i][row].length; col++) {
                        if (number.equals(reservationNumbers[i][row][col])) {
                            m = i;
                            r = row;
                            c = col;
                        }
                    }
                }
            }
            if (m == -1) {
                System.out.println("No active reservation found with number " + number + ".");
                return;
            }
        } else if (choice == 2) {
            m = selectMovie();
            if (m == -1) return;
            int row = readInt("Enter row number: ");
            int seat = readInt("Enter seat number: ");
            if (!validSeat(m, row, seat)) {
                System.out.println("Invalid seat.");
                return;
            }
            r = row - 1;
            c = seat - 1;
            if (seats[m][r][c] == 'O') {
                System.out.println("That seat is not reserved, so there is nothing to cancel.");
                return;
            }
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        String number = reservationNumbers[m][r][c];
        String name = passengerNames[m][r][c];

       
        seats[m][r][c] = 'O';
        passengerNames[m][r][c] = null;
        reservationNumbers[m][r][c] = null;
        reservationTimes[m][r][c] = null;

        System.out.println("Reservation " + number + " (" + name + ") for " + movieNames[m]
                + ", row " + (r + 1) + " seat " + (c + 1) + " has been cancelled.");
        System.out.println();
        displaySeats(m);
    }

   

   public static boolean noMovies() {
        if (movieNames.length == 0) {
            System.out.println("There are no movies in the cinema. Add one first (option 6).");
            return true;
        }
        return false;
    }

    
   public static int selectMovie() {
        if (noMovies()) return -1;
        showMovieNames();
        int choice = readInt("Choose a movie: ");
        if (choice < 1 || choice > movieNames.length) {
            System.out.println("Invalid movie choice.");
            return -1;
        }
        return choice - 1;
    }

   public static boolean validSeat(int m, int row, int seat) {
        return row >= 1 && row <= seats[m].length
                && seat >= 1 && seat <= seats[m][0].length;
    }

  public static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

   public static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) return value;
            System.out.println("The number must be greater than 0.");
        }
    }

   public static String readNonEmptyLine() {
        while (true) {
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) return line;
            System.out.print("This cannot be empty. Try again: ");
        }
    }
}
