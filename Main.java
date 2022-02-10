import java.util.Scanner;

public class Main {

    private static Game2048 game;

    public static void main(String[] args) {

        game = new Game2048();
        Scanner s = new Scanner(System.in);
        try {

            int move = -1;
            do {
    
                if (move >= 0 && move < 4){
                    game.applyMove(move);
                }
    
                System.out.println(game);
                System.out.print("Enter your move: ");
            } while ((move = s.nextInt()) >= 0 && move < 4);
            
        }

        finally {
            s.close();
        }



    }
}