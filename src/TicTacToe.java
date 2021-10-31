import java.util.*;

/**
 *
 * @author MeneXia (Xavi Ablaza)
 *
 */
public class TicTacToe {
    static Scanner in;
    static String[] board;
    static String turn;

    public static void main(String[] args) {
        in = new Scanner(System.in);
        board = new String[9];
        turn = "X";
        String winner = null;
        populateEmptyBoard();

        System.out.println("Welcome to 2 Player Tic Tac Toe.");
        System.out.println("--------------------------------");
        printBoard(board);
        System.out.println("X's will play first. Enter a slot number to place X in:");

        boolean firstStep = true;

        while (winner == null) {
            int numInput;
            try {
                numInput = in.nextInt();
                if (!(numInput > 0 && numInput <= 9)) {
                    System.out.println("Invalid input; re-enter slot number:");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input; re-enter slot number:");
                continue;
            }
            if (board[numInput-1].equals(String.valueOf(numInput))) {
                board[numInput-1] = turn;
                printBoard(board);
                winner = checkWinner(board);
                if(firstStep == true && (numInput == 1 || numInput == 3 || numInput == 7 || numInput == 9)){
                    board[4] = "O";
                }
                else {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    HashMap<Integer, Double> results = new HashMap<>();
                    for (int i = 0; i < 9; i++) {
                        if (!(board[i].equals("X") || board[i].equals("O"))) {
                            arrayList.add(i);
                        }
                    }
                    for (Integer i : arrayList) {
                        results.put(i, playSmart(i, 1, arrayList.size(), "O", board));
                    }
                    int key = 0;
                    double value = -1000000;
                    for (Integer i : results.keySet()) {
                        //System.out.println(i + " " + results.get(i));
                        if (results.get(i) > value) {
                            value = results.get(i);
                            key = i;
                        }
                    }
               board[key] = "O";
                }
                printBoard(board);
                winner = checkWinner(board);
                firstStep = false;
            } else {
                System.out.println("Slot already taken; re-enter slot number:");
                continue;
            }
        }
        if (winner.equalsIgnoreCase("draw")) {
            System.out.println("It's a draw! Thanks for playing.");
        } else {
            System.out.println("Congratulations! " + winner + "'s have won! Thanks for playing.");
        }
    }

    private static double playSmart(int position, int level, int brothers, String turn, String[] b) {
        String[] string = new String[9];
        for (int a = 0; a<9; a++){
            string[a] = b[a];
        }
        string[position] = turn;

        String result = checkWinner(string);

        //System.out.println(result);
        if(result == null){
            String newTurn = (turn.equals("X")) ? "O" : "X";
            ArrayList<Integer> arrayList = new ArrayList<>();
            double ret = 0;
            for(int i = 0; i<9; i++){
                if(!(string[i].equals("X") || string[i].equals("O"))) {
                    arrayList.add(i);
                }
            }
            for (Integer i : arrayList) {
                //System.out.println(i);
                double a = playSmart(i, level++, arrayList.size(), newTurn, string);
                //System.out.println(a + "hh");
                ret += a;
            }
            return ret/level/brothers;
        }
        else if(result.equalsIgnoreCase("draw")){
            return 0;
        }
        else if(result.equalsIgnoreCase("X")){
            return -1.0;
        }
        else if(result.equalsIgnoreCase("O")){
            //System.out.println(1.0/brothers/level);
            return 1.0;
        }
        else{
            return 0;
        }
    }

    static String checkWinner(String[] board) {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line = board[6] + board[7] + board[8];
                    break;
                case 3:
                    line = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line = board[2] + board[5] + board[8];
                    break;
                case 6:
                    line = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line = board[2] + board[4] + board[6];
                    break;
            }
            if (line.equals("XXX")) {
                return "X";
            } else if (line.equals("OOO")) {
                return "O";
            }
        }

        for (int a = 0; a < 9; a++) {
            if (Arrays.asList(board).contains(String.valueOf(a+1))) {
                break;
            }
            else if (a == 8) return "draw";
        }

        //System.out.println(turn + "'s turn; enter a slot number to place " + turn + " in:");
        return null;
    }

    static void printBoard(String[] board) {
        System.out.println("/---|---|---\\");
        System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
        System.out.println("|-----------|");
        System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
        System.out.println("/---|---|---\\");
    }

    static void populateEmptyBoard() {
        for (int a = 0; a < 9; a++) {
            board[a] = String.valueOf(a+1);
        }
    }
}