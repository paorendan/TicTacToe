/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.util.Scanner;

/**
 *
 * @author Bol-PC
 */
public class TicTacToe {
//// Name-constants to represent the seeds and cell contents
    public static Scanner in = new Scanner(System.in); // the input Scanner
    public static int board[][] = new int[3][3];//declare 3x3 board without adding new variable
    public static int player = 0;// player control
    public static int winner =0;// winner control
    public static int drawToken = 0;// max number of input should be 8 in 3x3 board
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     // Initialize the game-board and current status
      initGame();
      // Play the game once
      do {
         playerMove(); // update currentRow and currentCol
         printBoard();//print board
         // Print message if game-over
         if (winner >0) {
            System.out.println("'X' won! Bye!");
         } else if (winner <0) {
            System.out.println("'O' won! Bye!");
         } else if (drawToken == 9) {
            System.out.println("It's a Draw! Bye!");
         }
         // Switch player
         player = player*-1;
      } while (winner == 0 && drawToken <9); // repeat if not game-over
   }
    
   /** Initialize the game-board contents and the current states */
   public static void initGame() {
      for (int row = 0; row < 3; ++row) {
         for (int col = 0; col < 3; ++col) {
            board[row][col] = 0;  
         }
      }
      //to interchange between 2 players by randomizing starting player
      //not really neccesary, just to add new stuff
      double a = Math.random(),b = Math.random();
      if(a>b)player = -1;
      else player =1;
   }
 
   //not getting any variable, use variable instead
   public static void playerMove() {
      boolean validInput = false;  // for input validation
      int row=0,col=0;
      String inRow = "",inCol="";
      do {
         if (player > 0) {
            System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
         }
         inRow = in.next();
         inCol = in.next();
         //handle User Input and set warning
         if(inRow.matches("[0-9]+") ||inCol.matches("[0-9]+")){
            row = Integer.parseInt(inRow)-1;
            col = Integer.parseInt(inCol)-1;
            if(row<0 || row>2){
                System.out.println("Row should be between 1 to 3, input "+(row+1));
            } else if( col < 0 || col >2){
                System.out.println("Column should be between 1 to 3, input "+(col+1));
            } else if(board[row][col]!=0) {
                System.out.println("You should pick another one");
            } else{
               board[row][col] = player;  // update game-board content
               validInput = true;  // input okay, exit loop
            }
         }
         else{
             System.out.println(" Please Input Number Only");
         }
      } while (!validInput);  // repeat until input is valid
      drawToken++;
      if(hasWon(player,row,col)){
          winner = player;
      }
   }
 
   /** Return true if the player with "theSeed" has won after placing at
       (currentRow, currentCol), unchanged */
   public static boolean hasWon(int player,int currentRow,int currentCol) {
      return (board[currentRow][0] == player         // 3-in-the-row
                   && board[currentRow][1] == player
                   && board[currentRow][2] == player
              || board[0][currentCol] == player      // 3-in-the-column
                   && board[1][currentCol] == player
                   && board[2][currentCol] == player
              || currentRow == currentCol            // 3-in-the-diagonal
                   && board[0][0] == player
                   && board[1][1] == player
                   && board[2][2] == player
              || currentRow + currentCol == 2  // 3-in-the-opposite-diagonal
                   && board[0][2] == player
                   && board[1][1] == player
                   && board[2][0] == player);
   }
 
   /** Print the game board, add index to help user*/
   public static void printBoard() {
       System.out.println("Col | 1 | 2 | 3");
      for (int row = 0; row < 3; ++row) {
         for (int col = 0; col < 3; ++col) {
            if(col == 0 )System.out.print(" "+(row+1)+"  |");
            printCell(board[row][col]); // print each of the cells
            if (col != 3 - 1) {
               System.out.print("|");   // print vertical partition
            }
         }
         System.out.println();
         if (row != 3 - 1) {
            System.out.println("---------------"); // print horizontal partition
         }
      }
      System.out.println();
   }
 
   /** Print a cell with the specified "content", change to -1 and 1*/
   public static void printCell(int content) {
      switch (content) {
         case 0:  System.out.print("   "); break;
         case -1: System.out.print(" O "); break;
         case 1:  System.out.print(" X "); break;
      }
   }
}