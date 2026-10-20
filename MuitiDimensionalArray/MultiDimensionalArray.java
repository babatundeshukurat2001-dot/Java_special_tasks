import java.util.Scanner;

/**
 * Byte to Seven-Segment Converter
 *
 * Input bit order (index 0..7):  DP G F E D C B A
 *   e.g. 00111111 -> 0,  00000110 -> 1,  01111111 -> 8
 * Bit 0 is the decimal point, which a 5 x 4 grid cannot show, so it is ignored.
 *
 * Grid layout (5 rows x 4 columns):
 *   row 0: ####   <- A (top)
 *   row 1: #  #   <- F, B (upper left / upper right, rows 0-2)
 *   row 2: ####   <- G (middle)
 *   row 3: #  #   <- E, C (lower left / lower right, rows 2-4)
 *   row 4: ####   <- D (bottom)
 */
public class MultiDimensionalArray {

    static final int ROWS = 5;
    static final int COLS = 4;

    // Challenge 2: binary input needed for each decimal digit 0-9
    static final String[] DIGITS = {
        "00111111", // 0
        "00000110", // 1
        "01011011", // 2
        "01001111", // 3
        "01100110", // 4
        "01101101", // 5
        "01111101", // 6
        "00000111", // 7
        "01111111", // 8
        "01101111"  // 9
    };

    // Challenge 4: segment patterns for hex digits 0-F
    static final String[] HEX = {
        "00111111", // 0
        "00000110", // 1
        "01011011", // 2
        "01001111", // 3
        "01100110", // 4
        "01101101", // 5
        "01111101", // 6
        "00000111", // 7
        "01111111", // 8
        "01101111", // 9
        "01110111", // A
        "01111100", // b
        "00111001", // C
        "01011110", // d
        "01111001", // E
        "01110001"  // F
    };

    // Challenge 1: exactly 8 characters, each 0 or 1
    static boolean isValid(String input) {
        if (input == null || input.length() != 8) {
            return false;
        }
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '0' && c != '1') {
                return false;
            }
        }
        return true;
    }

    // Reads the bits, works out which segments are ON, and fills the 5 x 4 array
    static char[][] buildDisplay(String input) {
        char[][] display = new char[ROWS][COLS];

        // start with a blank grid
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                display[r][c] = ' ';
            }
        }

        // read individual bits (index 0 is the decimal point and is ignored)
        boolean g = input.charAt(1) == '1';
        boolean f = input.charAt(2) == '1';
        boolean e = input.charAt(3) == '1';
        boolean d = input.charAt(4) == '1';
        boolean c = input.charAt(5) == '1';
        boolean b = input.charAt(6) == '1';
        boolean a = input.charAt(7) == '1';

        // horizontal segments span every column of their row
        for (int col = 0; col < COLS; col++) {
            if (a) display[0][col] = '#';
            if (g) display[2][col] = '#';
            if (d) display[4][col] = '#';
        }

        // vertical segments: left = column 0, right = column 3.
        // Each one covers half of its column (including the corner and the
        // middle row), so 0 and 1 match the sample output in the assignment.
        for (int row = 0; row <= 2; row++) {
            if (f) display[row][0] = '#';
            if (b) display[row][3] = '#';
        }
        for (int row = 2; row < ROWS; row++) {
            if (e) display[row][0] = '#';
            if (c) display[row][3] = '#';
        }

        return display;
    }

    // Prints one display using nested loops
    static void printDisplay(char[][] display) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(display[r][c]);
            }
            System.out.println();
        }
    }

    // Challenge 4: prints two displays side by side
    static void printTwoDisplays(char[][] left, char[][] right) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(left[r][c]);
            }
            System.out.print("   ");
            for (int c = 0; c < COLS; c++) {
                System.out.print(right[r][c]);
            }
            System.out.println();
        }
    }

    // Challenge 2: show every digit with its binary code
    static void showAllDigits() {
        for (int i = 0; i < DIGITS.length; i++) {
            System.out.println("Digit " + i + "  (" + DIGITS[i] + ")");
            printDisplay(buildDisplay(DIGITS[i]));
            System.out.println();
        }
    }

    // Challenge 4: byte as two hex digits, e.g. 10110101 -> B 5
    static void hexMode(String input) {
        int value = Integer.parseInt(input, 2);
        int high = value >> 4;    // upper nibble
        int low = value & 0x0F;   // lower nibble

        char[][] leftDisplay = buildDisplay(HEX[high]);
        char[][] rightDisplay = buildDisplay(HEX[low]);

        System.out.println("Hex value: "
                + Integer.toHexString(high).toUpperCase()
                + Integer.toHexString(low).toUpperCase());
        printTwoDisplays(leftDisplay, rightDisplay);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("=== Byte to Seven-Segment Converter ===");
            System.out.println("1. Convert 8-bit segment value (single display)");
            System.out.println("2. Show all digits 0-9");
            System.out.println("3. Convert byte to two hex digits (two displays)");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                boolean again = true;
                while (again) {
                    System.out.print("Enter an 8-bit binary value (or 'back'): ");
                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("back")) {
                        again = false;
                    } else if (!isValid(input)) {
                        System.out.println("Invalid input! Enter exactly 8 characters, each 0 or 1.\n");
                    } else {
                        char[][] display = buildDisplay(input);
                        printDisplay(display);
                        System.out.println();
                    }
                }
            } else if (choice.equals("2")) {
                showAllDigits();
            } else if (choice.equals("3")) {
                boolean again = true;
                while (again) {
                    System.out.print("Enter an 8-bit binary value (or 'back'): ");
                    String input = scanner.nextLine().trim();

                    if (input.equalsIgnoreCase("back")) {
                        again = false;
                    } else if (!isValid(input)) {
                        System.out.println("Invalid input! Enter exactly 8 characters, each 0 or 1.\n");
                    } else {
                        hexMode(input);
                        System.out.println();
                    }
                }
            } else if (choice.equals("4")) {
                running = false;
                System.out.println("Goodbye!");
            } else {
                System.out.println("Invalid option.\n");
            }
        }
        scanner.close();
    }
}
