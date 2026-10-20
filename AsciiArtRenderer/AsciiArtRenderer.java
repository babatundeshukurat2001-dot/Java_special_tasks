import java.util.Scanner;

public class AsciiArtRenderer {

    // ===================== Settings =====================

    static final int ROWS = 22;
    static final int COLS = 60;
    static final char BACKGROUND = ' ';

    // ===================== Pictures =====================

    static final String[] NAMES = {"Heart", "House", "Smiley", "Tree", "Cat"};

    static final String[][] PICTURES = {
        // Heart
        {
            "  ***   ***  ",
            " ***** ***** ",
            "*************",
            " *********** ",
            "  *********  ",
            "   *******   ",
            "    *****    ",
            "     ***     ",
            "      *      "
        },
        // House
        {
            "    /\\    ",
            "   /  \\   ",
            "  /    \\  ",
            " /______\\ ",
            " |  []  | ",
            " |      | ",
            " |______| "
        },
        // Smiley
        {
            "  *****  ",
            " *     * ",
            "* O   O *",
            "*   ^   *",
            "* \\___/ *",
            " *     * ",
            "  *****  "
        },
        // Tree
        {
            "    *    ",
            "   ***   ",
            "  *****  ",
            " ******* ",
            "*********",
            "   | |   ",
            "   | |   "
        },
        // Cat
        {
            " /\\_/\\ ",
            "( o.o )",
            " > ^ < "
        }
    };

    // ===================== Canvas =====================

    static char[][] createCanvas() {
        char[][] canvas = new char[ROWS][COLS];
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                canvas[r][c] = BACKGROUND;
            }
        }
        return canvas;
    }

    static void drawFrame(char[][] canvas) {
        for (int c = 0; c < COLS; c++) {
            canvas[0][c] = '-';
            canvas[ROWS - 1][c] = '-';
        }
        for (int r = 0; r < ROWS; r++) {
            canvas[r][0] = '|';
            canvas[r][COLS - 1] = '|';
        }
        canvas[0][0] = '+';
        canvas[0][COLS - 1] = '+';
        canvas[ROWS - 1][0] = '+';
        canvas[ROWS - 1][COLS - 1] = '+';
    }

    // ===================== Placing pictures =====================

    static int pictureWidth(String[] picture) {
        int width = 0;
        for (int r = 0; r < picture.length; r++) {
            if (picture[r].length() > width) {
                width = picture[r].length();
            }
        }
        return width;
    }

    static boolean fits(String[] picture, int startRow, int startCol) {
        int height = picture.length;
        int width = pictureWidth(picture);
        return startRow >= 1 && startCol >= 1
                && startRow + height <= ROWS - 1
                && startCol + width <= COLS - 1;
    }

    static void stamp(char[][] canvas, String[] picture, int startRow, int startCol, char drawChar) {
        for (int r = 0; r < picture.length; r++) {
            for (int c = 0; c < picture[r].length(); c++) {
                char ch = picture[r].charAt(c);
                if (ch != ' ') {
                    canvas[startRow + r][startCol + c] = (drawChar == 0) ? ch : drawChar;
                }
            }
        }
    }

    // ===================== Output =====================

    static void render(char[][] canvas) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                System.out.print(canvas[r][c]);
            }
            System.out.println();
        }
    }

    // ===================== Input =====================

    static int readInt(Scanner in, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = in.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException e) {
                // not a number: fall through to the message below
            }
            System.out.println("Please enter a number from " + min + " to " + max + ".");
        }
    }

    // ===================== Main =====================

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        char[][] canvas = createCanvas();
        drawFrame(canvas);

        System.out.println("=== ASCII Art Renderer ===");
        System.out.println("Drawing area: " + ROWS + " rows x " + COLS + " columns");
        System.out.println("(valid rows: 1-" + (ROWS - 2) + ", valid columns: 1-" + (COLS - 2) + ")");

        boolean again = true;
        while (again) {
            System.out.println();
            for (int i = 0; i < NAMES.length; i++) {
                System.out.print((i + 1) + ". " + NAMES[i] + "  ");
            }
            System.out.println();
            int choice = readInt(in, "Choose a picture: ", 1, NAMES.length) - 1;
            String[] picture = PICTURES[choice];

            System.out.print("Enter drawing character (press Enter to keep the original): ");
            String charInput = in.nextLine();
            char drawChar = charInput.isEmpty() ? 0 : charInput.charAt(0);

            int startRow = readInt(in, "Enter starting row: ", 0, ROWS - 1);
            int startCol = readInt(in, "Enter starting column: ", 0, COLS - 1);

            if (fits(picture, startRow, startCol)) {
                stamp(canvas, picture, startRow, startCol, drawChar);
            } else {
                System.out.println("That picture doesn't fit there. Try a different position.");
            }

            System.out.print("Add another picture? (y/n): ");
            again = in.nextLine().trim().equalsIgnoreCase("y");
        }

        System.out.println();
        render(canvas);
    }
}
