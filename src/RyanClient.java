/*********************************************************************************************************************
 *********************************************************************************************************************
 *****                                       Program #2: Tic-Tac-Toe                                             *****
 *****___________________________________________________________________________________________________________*****
 *****                                     Developed by: Ryan Huffman                                            *****
 *****                                             CSC-460-001                                                   *****
 *****                                         Professor Gary Newell                                             *****
 *********************************************************************************************************************
 ********************************************************************************************************************/

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RyanClient {
    private static char[][] board;

    public static void main(String[] args) throws IOException {
        Socket toserver = new Socket("localhost", 7788);
        DataInputStream instream = new DataInputStream(toserver.getInputStream());
        DataOutputStream outstream = new DataOutputStream(toserver.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(instream));
        PrintWriter out = new PrintWriter(outstream, true);

        board = new char[3][3];
        initBoard();
        playgame(in, out);
    }

    public static void initBoard() {
        for(int r = 0; r <= 2; ++r) {
            for(int c = 0; c <= 2; ++c) {
                board[r][c] = ' ';
            }
        }
    }

    public static void playgame(BufferedReader in, PrintWriter out) throws IOException {
        Scanner scannerInput = new Scanner(System.in);
        boolean playerTurn = false;
        int row = -1;
        int col = -1;

        for(boolean gameOver = false; !gameOver; playerTurn = !playerTurn) {
            if (!playerTurn) {
                String line = in.readLine();
                if (!line.equals("NONE")) {
                    String[] data = line.split("\\s+");
                    row = Integer.parseInt(data[1]);
                    col = Integer.parseInt(data[2]);

                    if (data.length > 3) {
                        if (!data[3].equals("WIN") && row != -1) {
                            board[row][col] = 'X';
                        }

                        String result = data[3];
                        switch(result) {
                            case "WIN":
                                System.out.println("\n\nCongratulations!!! You WON the game!");
                                break;
                            case "LOSS":
                                System.out.println("\nSORRY! You LOST the game!");
                                break;
                            case "TIE":
                                System.out.println("\nThe game was a TIE!");
                                break;
                        }

                        gameOver = true;
                    } else {
                        board[row][col] = 'X';
                    }
                } else {
                    System.out.println("\nYOU MOVE FIRST");
                }
            } else {
                while(true) {
                    do {
                        System.out.print("\nEnter Row : ");
                        row = scannerInput.nextInt();
                        System.out.print("Enter Column : ");
                        col = scannerInput.nextInt();
                    } while(row < 0);

                    if (row <= 2 && col <= 2 && col >= 0 && board[row][col] == ' ') {
                        board[row][col] = 'O';
                        out.println("MOVE " + row + " " + col);
                        break;
                    }
                }
            }

            printboard();
        }

        System.out.println("\n\nHere is the final game board");
        printboard();
    }

    public static void printboard() {
        System.out.println("\n\nCLIENT PRINT");

        for(int r = 0; r <= 2; ++r) {
            System.out.println(board[r][0] + " | " + board[r][1] + " | " + board[r][2]);
            if (r != 2) {
                System.out.println("----------");
            }
        }
    }
}