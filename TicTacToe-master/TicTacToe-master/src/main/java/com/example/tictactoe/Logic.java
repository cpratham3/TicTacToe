//package com.example.tictactoe;
//
//import javafx.scene.control.Button;
//import javafx.scene.layout.GridPane;
//import javafx.scene.text.Text;
//
//public class Logic {
//    private static String currentPlayer = "X";
//    private static String[][] gameBoard = new String[3][3];
//    public static void buttonHandler(int row, int col, Button btn, GridPane buttonsContainer){
//        if (btn.getText().isEmpty()){
//            btn.setText(currentPlayer);
//            gameBoard[row][col] = currentPlayer;
//            checkWin(row,col,gameBoard);
//            checkDraw(row,col);
//            if (currentPlayer == "X"){
//                currentPlayer = "O";
//            }
//            else if (currentPlayer == "O"){
//                currentPlayer = "X";
//            }
//
//        }
//    }
//
//    private static void checkDraw(int row,int col,String[][] gameBoard) {
//
//    }
//
//    private static void checkWin(int row,int col,String[][] gameBoard) {
//
//    }
//}
