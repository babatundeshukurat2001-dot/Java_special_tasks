import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TicTacToeTest {

    @Test
    void shouldReturnRowZeroForPositionOne() {

        int result = TicTacToe.getRow(1);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnRowZeroForPositionThree() {

        int result = TicTacToe.getRow(3);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnRowOneForPositionFour() {

        int result = TicTacToe.getRow(4);

        assertEquals(1, result);
    }

    @Test
    void shouldReturnRowOneForPositionFive() {

        int result = TicTacToe.getRow(5);

        assertEquals(1, result);
    }

    @Test
    void shouldReturnRowOneForPositionSix() {

        int result = TicTacToe.getRow(6);

        assertEquals(1, result);
    }

    @Test
    void shouldReturnRowTwoForPositionSeven() {

        int result = TicTacToe.getRow(7);

        assertEquals(2, result);
    }

    @Test
    void shouldReturnRowTwoForPositionNine() {

        int result = TicTacToe.getRow(9);

        assertEquals(2, result);
    }


    // COLUMN TESTS

    @Test
    void shouldReturnColumnZeroForPositionOne() {

        int result = TicTacToe.getColumn(1);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnColumnOneForPositionTwo() {

        int result = TicTacToe.getColumn(2);

        assertEquals(1, result);
    }

    @Test
    void shouldReturnColumnTwoForPositionThree() {

        int result = TicTacToe.getColumn(3);

        assertEquals(2, result);
    }

    @Test
    void shouldReturnColumnZeroForPositionFour() {

        int result = TicTacToe.getColumn(4);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnColumnOneForPositionFive() {

        int result = TicTacToe.getColumn(5);

        assertEquals(1, result);
    }

    @Test
    void shouldReturnColumnTwoForPositionSix() {

        int result = TicTacToe.getColumn(6);

        assertEquals(2, result);
    }

    @Test
    void shouldReturnColumnZeroForPositionSeven() {

        int result = TicTacToe.getColumn(7);

        assertEquals(0, result);
    }

    @Test
    void shouldReturnColumnTwoForPositionNine() {

        int result = TicTacToe.getColumn(9);

        assertEquals(2, result);
    }
}
