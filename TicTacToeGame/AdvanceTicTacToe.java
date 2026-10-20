import java.util.Scanner;

public class AdvanceTicTacToe {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    boolean again;
                    do {
                        playRound();
                        again = askPlayAgain();
                    } while (again);
                    break;
                case "2":
                    showInstructions();
                    break;
                case "3":
                    System.out.println("Thanks for playing. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2 or 3.\n");
            }
        }
    }

   

    private static void showMenu() {
        System.out.println("=============================");
        System.out.println("        TIC TAC TOE");
        System.out.println("=============================");
        System.out.println("1. Play game");
        System.out.println("2. How to play");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void showInstructions() {
        System.out.println();
        System.out.println("HOW TO PLAY");
        System.out.println("- Two players take turns: X goes first, then O.");
        System.out.println("- Enter a number from 1 to 9 to place your mark.");
        System.out.println("- Get 3 in a row (across, down or diagonal) to win.");
        System.out.println("- If all 9 squares fill up with no winner, it's a draw.");
        System.out.println();
        displayBoard(newBoard());
        System.out.println();
    }

   

    private static void playRound() {
        char[][] board = newBoard();
        System.out.println();
        displayBoard(board);

        for (int move = 0; move < 9; move++) {
            char player = (move % 2 == 0) ? 'X' : 'O';
            int position = getValidPosition(board, player);

            int row = (position - 1) / 3;
            int col = (position - 1) % 3;
            board[row][col] = player;

            System.out.println();
            displayBoard(board);

            if (hasWinner(board, player)) {
                System.out.println("\nPlayer " + player + " wins!\n");
                return;
            }
        }

        System.out.println("\nIt's a draw!\n");
    }

    private static int getValidPosition(char[][] board, char player) {
        while (true) {
            System.out.print("Player " + player + ", select a position (1-9): ");
            String input = scanner.nextLine().trim();

            int position;
            try {
                position = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number from 1 to 9.");
                continue;
            }

            if (position < 1 || position > 9) {
                System.out.println("Invalid position. Choose a number between 1 and 9.");
                continue;
            }

            int row = (position - 1) / 3;
            int col = (position - 1) % 3;

            if (board[row][col] == 'X' || board[row][col] == 'O') {
                System.out.println("That position is already taken. Try another one.");
                continue;
            }

            return position;
        }
    }

    private static boolean askPlayAgain() {
        while (true) {
            System.out.print("Play again? (y/n): ");
            String answer = scanner.nextLine().trim().toLowerCase();

            if (answer.equals("y") || answer.equals("yes")) {
                return true;
            } else if (answer.equals("n") || answer.equals("no")) {
                System.out.println();
                return false;
            }
            System.out.println("Please type y or n.");
        }
    }

   
    private static char[][] newBoard() {
        char[][] board = new char[3][3];
        char number = '1';
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = number++;
            }
        }
        return board;
    }

    private static boolean hasWinner(char[][] b, char p) {
        for (int i = 0; i < 3; i++) {
            if (b[i][0] == p && b[i][1] == p && b[i][2] == p) return true; 
            if (b[0][i] == p && b[1][i] == p && b[2][i] == p) return true; 
        }
        if (b[0][0] == p && b[1][1] == p && b[2][2] == p) return true;     
        if (b[0][2] == p && b[1][1] == p && b[2][0] == p) return true;     
        return false;
    }

    private static void displayBoard(char[][] board) {
        for (int row = 0; row < 3; row++) {
            System.out.println(" " + board[row][0] + " | " + board[row][1] + " | " + board[row][2]);
            if (row < 2) {
                System.out.println("---+---+---");
            }
        }
    }
}
