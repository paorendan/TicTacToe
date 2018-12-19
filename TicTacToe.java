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
    public static int board[][] = new int[0][0];//declare 3x3 board without adding new variable
    public static int player = 0;// player control
    public static int boardRow=0,boardCol = 0;// board control
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
         } else if (drawToken == boardRow*boardCol) {
            System.out.println("It's a Draw! Bye!");
         }
         // Switch player
         player = player*-1;
      } while (winner == 0 && drawToken <9); // repeat if not game-over
   }
    
   /** Initialize the game-board contents and the current states */
   public static void initGame() {
      boolean validInput = false;
      String inRow = "",inCol="",read="";
      String[] separator= new String[2];
       do {
         System.out.print("Please create your TicTacToe Board with minimum of 3(example : 3x3 will create 3x3 board) :");
         read = in.nextLine();
         inRow = read.substring(0,1);
         inCol = read.substring(read.length()-1);
         //handle User Input and set warning
         if(inRow.matches("[0-9]+") ||inCol.matches("[0-9]+") || read.contains(" ")){
            boardRow = Integer.parseInt(inRow);
            boardCol = Integer.parseInt(inCol);
            if(boardRow<2){
                System.out.println("Row should be bigger than 2");
            } else if( boardCol < 2){
                System.out.println("Column should be bigger than 2");
            } else{
               validInput = true;  // input okay, exit loop
            }
         }
         else{
             System.out.println("Please input (number)x(number) format to create board and greater than 2"); 
             // win condition = 3 in a row, so min number of board should be 3
             // if board only 2x3 or 3x2, first player always win by making 2 diagonal(2x3) and 2 row (3x2)
             // kf win by 3 on board, no one wins or first player win with 3 in a row (2x3) and 3 in a column(3x2)
         }
        } while (!validInput);
       
       board = new int[boardRow][boardCol];
       System.out.println("board "+boardRow+" - "+boardCol);
       for (int row = 0; row < boardRow; ++row) {
         for (int col = 0; col < boardCol; ++col) {
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
            System.out.print("Player 'X', enter your move (row[1-"+boardRow+"] column[1-"+boardCol+"]): ");
         } else {
            System.out.print("Player 'O', enter your move (row[1-"+boardRow+"] column[1-"+boardCol+"]): ");
         }
         inRow = in.next();
         inCol = in.next();
         //handle User Input and set warning
         if(inRow.matches("[0-9]+") ||inCol.matches("[0-9]+")){
            row = Integer.parseInt(inRow)-1;
            col = Integer.parseInt(inCol)-1;
             System.out.println("row "+row+" col "+col);
            if(row<0 || row>boardRow){
                System.out.println("Row should be between 1 to "+boardRow+", input "+(row+1));
            } else if( col < 0 || col >boardCol){
                System.out.println("Column should be between 1 to "+boardCol+", input "+(col+1));
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
       (currentRow, currentCol), add read to assist in flexible format */
   public static boolean hasWon(int player,int currentRow,int currentCol) {
      return (currentCol-2>=0
                   && board[currentRow][currentCol-2] == player         // 3-in-the-row  backward
                   && board[currentRow][currentCol-1] == player
                   && board[currentRow][currentCol] == player
              || currentRow-2>=0
                   && board[currentRow-2][currentCol] == player      // 3-in-the-column upward
                   && board[currentRow-1][currentCol] == player
                   && board[currentRow][currentCol] == player
              || currentCol+2<boardCol
                   && board[currentRow][currentCol+2] == player         // 3-in-the-row forward
                   && board[currentRow][currentCol+1] == player
                   && board[currentRow][currentCol] == player
              || currentRow+2<boardRow
                   && board[currentRow+2][currentCol] == player      // 3-in-the-column downward
                   && board[currentRow+1][currentCol] == player
                   && board[currentRow][currentCol] == player
              || currentRow-2>=0 && currentCol-2>=0            // 3-in-the-diagonal
                   && board[currentRow][currentCol] == player
                   && board[currentRow-1][currentCol-1] == player
                   && board[currentRow-2][currentCol-2] == player
              || currentRow-2>=0 && currentCol-2>=0  // 3-in-the-opposite-diagonal
                   && board[currentRow][currentCol-2] == player
                   && board[currentRow-1][currentCol-1] == player
                   && board[currentRow-2][currentCol] == player
              || currentRow-2>=0 && currentCol+2<boardCol            // 3-in-the diagonal up right
                   && board[currentRow][currentCol] == player
                   && board[currentRow-1][currentCol+1] == player
                   && board[currentRow-2][currentCol+2] == player
              || currentRow+2<boardCol && currentCol-2>=0            // 3-in-the diagonal down left
                   && board[currentRow][currentCol] == player
                   && board[currentRow+1][currentCol-1] == player
                   && board[currentRow+2][currentCol-2] == player
              || currentRow+2<boardRow && currentCol+2<boardCol        // 3-in-the-diagonal down right
                   && board[currentRow][currentCol] == player
                   && board[currentRow+1][currentCol+1] == player
                   && board[currentRow+2][currentCol+2] == player
              || currentRow-1>=0 && currentCol-1>=0
                   && currentRow+1<boardRow && currentCol+1<boardCol// in the middle of diagonal
                   && board[currentRow][currentCol] == player
                   && board[currentRow-1][currentCol-1] == player
                   && board[currentRow+1][currentCol+1] == player
              || currentRow-1>=0 && currentCol-1>=0  // in the middle of opposite diagonal
                   && currentRow+1<boardRow && currentCol+1<boardCol
                   && board[currentRow][currentCol] == player
                   && board[currentRow-1][currentCol+1] == player
                   && board[currentRow+1][currentCol-1] == player
              || currentCol-1>=0 && currentCol+1<boardCol// in the middle of row
                   && board[currentRow][currentCol] == player
                   && board[currentRow][currentCol-1] == player
                   && board[currentRow][currentCol+1] == player
              || currentRow-1>=0 && currentRow+1<boardRow  // in the middle of column
                   && board[currentRow][currentCol] == player
                   && board[currentRow-1][currentCol] == player
                   && board[currentRow+1][currentCol] == player);
    }
 
   /** Print the game board, add index to help user*/
   public static void printBoard() {
       System.out.print("Col |");
       for(int i = 0 ; i<boardCol;i++)
       System.out.print(" "+(i+1)+" |");
       System.out.println("");//add enter
       
       for (int row = 0; row < boardRow; ++row) {
         for (int col = 0; col < boardCol; ++col) {
            if(col == 0 )System.out.print(" "+(row+1)+"  |");
            printCell(board[row][col]); // print each of the cells
               System.out.print("|");   // print vertical partition
         }
         System.out.println();
            System.out.print("-----");
            for(int i = 0 ; i<boardCol;i++)
            System.out.print("----");
            System.out.println("");//add enter // print horizontal partition
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