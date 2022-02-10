import java.util.Random;

public class Game2048 {
    private int[][] board = new int[4][4];
    private Random random = new Random();

    public Game2048 () {
        for (int[] row : board) {
            for (int i = 0; i < 4; i++) {
                row[i] = 0;
            }
        }

        board[random.nextInt(4)][random.nextInt(4)] = 2;
    }

    public String toString() {
        String s = "+---------+\n";
        for (int[] row : board) {
            s += "| ";
            for (int i = 0; i < 4; i++) {
                s += row[i] + " ";
            }
            s += "|\n";
        }
        return s + "+---------+";
    }

    private boolean full (int[][] a) {
        boolean f = true;
        for (int[] b : a) {
            for (int i = 0; i < 4; i++) {
                f = f && (b[i] > 0);
            }
        }
        return f;
    }

    public boolean gameOver () {
        return full (move(0)) && full (move(1)) && full (move(2)) && full (move(3));
    }

    private int[] copy (int[] a) {
        return new int[] {a[0], a[1], a[2], a[3]};
    }

    private int[] reverse (int[] a) {
        return new int[] {a[3], a[2], a[1], a[0]};
    }

    private void printArray (int[] a) {
        System.out.println("{ " + a[0] + ", "+ a[1] + ", "+ a[2] + ", "+ a[3] + " }");
    }

    public boolean compareBoards (int[][] a, int[][] b) {
        boolean same = true;
        for ( int i = 0; i < 4; i++ ) {
            for ( int c = 0; c < 4; c++ ) {
                same = same && (a[i][c] == b[i][c]);
            }
        }
        return same;
    }

    //This one doesn't join like tiles.
    private void scootArrayRight (int[] array) {

        int nonzeroPointer = 3;
        for (int i = 3; i >= 0; i--) {
            array[nonzeroPointer] = array[i];
            if (array[i] > 0) {
                nonzeroPointer--;
            }
        }

        for(int i = nonzeroPointer; i >= 0; i--) {
            array[i] = 0;
        }

    }

    //This one does.
    private int[] shiftArrayRight (int[] a) {

        int[] array = copy(a);

        scootArrayRight(array);

        for (int i = 3; i > 0; i--) {

            if (array[i - 1] == array[i]) {
                array[i] = 2 * array[i];
                array[i - 1] = 0;
            }

            scootArrayRight(array);
        }

        return array;
    }

    private int[] shiftArrayLeft (int[] a) {
        return reverse(shiftArrayRight(reverse(a)));
    }

    private int[] getColumn (int num) {
        int[] col = new int[4];
        for ( int i = 0; i < 4; i++ ) {
            col[i] = board[i][num];
        }
        return col;
    }

    private void setColumn (int[][] b, int[] col, int colnum) {
        for ( int i = 0; i < 4; i++ ) {
            b[i][colnum] = col[i];
        }
    }

    /* 

    Returns the board after a given move.
    0 is right, 1 is up, 2 is left, 3 is down 
    
    */
    private int[][] move (int direction) {
        System.out.println("Trying move "+direction);
        int[][] newBoard = new int[4][4];

        if (direction == 0) {
            for ( int i = 0; i < 4; i++) {
                newBoard[i] = shiftArrayRight(board[i]);
            }
        }

        if (direction == 1) {
            for ( int i = 0; i < 4; i++) {
                setColumn(newBoard, shiftArrayRight(getColumn(i)), i);
            }
        }

        if (direction == 2) {
            for ( int i = 0; i < 4; i++) {
                newBoard[i] = shiftArrayLeft(board[i]);
            }
        }

        if (direction == 3) {
            for ( int i = 0; i < 4; i++) {
                setColumn(newBoard, shiftArrayLeft(getColumn(i)), i);
            }
        }
            
        return newBoard;
    }

    public void applyMove (int direction) {

        int[][] newBoard = move(direction);

        if (!compareBoards(board, newBoard) ) {
            board = newBoard;

            int x = random.nextInt(4);
            int y = random.nextInt(4);
            while (board[x][y] != 0) {
                x = random.nextInt(4);
                y = random.nextInt(4);
            }
            if (random.nextInt(10) == 0) {
                board[x][y] = 4;
            }
            else {
                board[x][y] = 2;
            }
        }

    }
}
