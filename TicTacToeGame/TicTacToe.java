import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        char[][] board = new char[3][3];
        char number = '1';
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = number;
                number++;
            }
        }

        displayBoard(board);

       
        for (int move = 0; move < 9; move++) {
            char player = (move % 2 == 0) ? 'X' : 'O';

            System.out.print("Player " + player + ", select a position (1-9): ");
            int position = scanner.nextInt();

           
            int row = (position - 1) / 3;
            int col = (position - 1) % 3;

            board[row][col] = player;

            System.out.println();
            displayBoard(board);
        }

        
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
