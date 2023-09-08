package com.example.tictactoe;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.Flow;

public class TicTacToe extends Application {
    public static String[][] gameBoard = new String[3][3];
    public static boolean currentUser = true;

    public static String currentPlayer = "X";

    @Override
    public void start(Stage stage) throws Exception {
        FlowPane firstContainer = new FlowPane(Orientation.VERTICAL);
        GridPane secondContainer = new GridPane();
        Button twoPlayer = new Button("2 Player");
        Button vsComp = new Button("vs Computer");
        firstContainer.getChildren().addAll(twoPlayer,vsComp);
        firstContainer.setAlignment(Pos.CENTER);
        firstContainer.setVgap(20);


        GridPane buttonsContainer = new GridPane();

        Scene theScene = new Scene(buttonsContainer, 640, 640);

        Scene firstScene = new Scene(firstContainer, 640,640);

        Scene compScene = new Scene(secondContainer, 640,640);
        buttonsContainer.setAlignment(Pos.CENTER);
        secondContainer.setAlignment(Pos.CENTER);

        theScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        firstScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        compScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        twoPlayer.getStyleClass().add("normalButton");
        vsComp.getStyleClass().add("normalButton");


        twoPlayer.setOnAction(actionEvent -> {
            stage.setScene(theScene);
        });



        vsComp.setOnAction(actionEvent -> {
            stage.setScene(compScene);
        });



        for (int row = 0 ; row<3;row++){
            for (int col = 0; col<3 ; col++){
                Button btn = new Button();
                btn.setMinSize(100,100);
                btn.getStyleClass().add("boardButton");
                int currentRow = row;
                int currentCol = col;
                btn.setOnAction(e->{
                    buttonHandler(currentRow,currentCol,btn,buttonsContainer,stage,firstScene);
                });
                buttonsContainer.add(btn,col,row);
            }
        }

        for (int row = 0 ; row<3;row++){
            for (int col = 0; col<3 ; col++){
                Button btn = new Button();
                btn.setMinSize(100,100);
                btn.getStyleClass().add("boardButton");
                int currentRow = row;
                int currentCol = col;
                btn.setOnAction(e->{
                    compButtonHandler(currentRow,currentCol,btn,secondContainer,stage,firstScene);
                });
                secondContainer.add(btn,col,row);
            }
        }
        stage.setTitle("TIC-TAC-TOE");
        stage.setScene(firstScene);
        stage.show();
    }

    private static void compButtonHandler(int row, int column , Button btn, GridPane container,Stage stage,
                                          Scene firstScene){
        if (btn.getText().isEmpty()){
            btn.setText(currentPlayer);
            gameBoard[row][column] = currentPlayer;
            disableButtons(container,stage,firstScene);
            if (currentPlayer == "X"){
                currentPlayer = "O";
            }
            else if(currentPlayer == "O"){
                currentPlayer = "X";
            }

            if (!checkWin() && !checkDraw(container)) {
                System.out.println("yes");
                playTurnComp(container,stage,firstScene);
            }
            else{
                System.out.println("NO");
            }
        }
    }

    private static void playTurnComp(GridPane container,Stage stage,Scene firstScene) {
        if (!checkDraw(container) && !checkWin()) {
            List<Node> emptyButtonsList = new ArrayList<>();
            for (Node n : container.getChildren()) {
                Button btn = (Button) n;
                if (btn.getText().isEmpty()) {
                    emptyButtonsList.add(btn);
                }
            }
            if (!emptyButtonsList.isEmpty()) {
                Random random = new Random();
                int randomIndex = random.nextInt(emptyButtonsList.size());

                Button selectedButton = (Button) emptyButtonsList.get(randomIndex);
                selectedButton.setText(currentPlayer);

                int row = GridPane.getRowIndex(selectedButton);
                int col = GridPane.getColumnIndex(selectedButton);

                gameBoard[row][col] = currentPlayer;

                disableButtons(container,stage,firstScene);

                if (currentPlayer.equals("X")) {
                    currentPlayer = "O";
                }

                else {
                    currentPlayer = "X";
                }

            }
            else{
                System.out.println("No empty buttons left!");
            }

        }
    }

    public static void buttonHandler(int row, int column, Button btn, GridPane container,Stage stage,Scene firstScene){
        System.out.println("Button clickedL: "+currentPlayer);

        if (btn.getText().isEmpty()){
            btn.setText(currentPlayer);
            gameBoard[row][column] = currentPlayer;
            disableButtons(container,stage,firstScene);
//            checkDraw(container);
            System.out.println("Button dabane par currentPlayer: "+ currentPlayer);
            if (currentPlayer == "X"){
                currentPlayer = "O";
            }
            else if(currentPlayer == "O"){
                currentPlayer = "X";
            }
        }
    }

    private static boolean checkWin() {
        for (int row = 0; row < 3; row++) {
            try {
                if ((gameBoard[row][0] == currentPlayer && gameBoard[row][1] == currentPlayer && gameBoard[row][2] == currentPlayer)) {
                    System.out.println(currentPlayer+" WON");
                    return true;
                }

            }catch (Exception e){
                System.out.println("khelte raho");
            }
        }

        for (int col = 0; col < 3; col++) {
            try {
                if ((gameBoard[0][col] == currentPlayer && gameBoard[1][col] == currentPlayer && gameBoard[2][col] == currentPlayer)) {
                    System.out.println("WON");
                    return true;
                }

            }catch (Exception e){
                System.out.println("khelte raho");
            }
        }

        if((gameBoard[0][0] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][2] == currentPlayer) || (gameBoard[0][2] == currentPlayer && gameBoard[1][1] == currentPlayer && gameBoard[2][0] == currentPlayer)){
            System.out.println("WoN");
            return true;
        }

        return false;

    }
    private static boolean checkDraw(GridPane container){
        int countCellsNotEmpty = 0;
        for (Node n: container.getChildren()){
            if (n instanceof Button){
                Button btn = (Button) n;
                if (!btn.getText().isEmpty()){
                    countCellsNotEmpty++;
                }
            }
        }
        if (countCellsNotEmpty==9 && !checkWin()){
            return true;
        }
        return false;

    }


    private static void disableButtons(GridPane container,Stage stage,Scene firstScene){
        if (checkWin() || checkDraw(container)) {
            for (Node n : container.getChildren()) {
                if (n instanceof Button) {
//                n.setDisable(true);
                    Button btn = (Button) n;
                    btn.setDisable(true);

                }
            }
            showRespectiveDialog(container,stage,firstScene);
        }
        else{
            System.out.println("checkwin and checkdraw false!");
        }


    }
    private static void showRespectiveDialog(GridPane container,Stage stage,Scene firstScene){
        if (checkWin()){
            showPlayAgainDialog(container,stage,firstScene);
        } else if (checkDraw(container)) {
            showDrawDialog(container,stage,firstScene);
        }
    }
    private static void showDrawDialog(GridPane container,Stage stage,Scene firstScene){
        if (checkDraw(container)) {
            Alert drawDialog = new Alert(Alert.AlertType.CONFIRMATION);
            drawDialog.setTitle("Draw!");
            drawDialog.setHeaderText("Game Drawn!");
            drawDialog.setContentText("Want to play again?");

            ButtonType playAgainButton = new ButtonType("Play Again");
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            drawDialog.getButtonTypes().setAll(playAgainButton, exitButton);

            Optional<ButtonType> result = drawDialog.showAndWait();
            if (result.isPresent() && result.get() == playAgainButton) {
                resetGame(container);
                currentPlayer = "X";
            } else {
                stage.setScene(firstScene);
                resetGame(container);
            }
        }
    }
    private static void showPlayAgainDialog(GridPane container, Stage stage, Scene firstScene){
        if (checkWin()) {
            Alert playAgainDialog = new Alert(Alert.AlertType.CONFIRMATION);
            playAgainDialog.setTitle("Game Over!");
            playAgainDialog.setHeaderText(currentPlayer + " won the game!");
            playAgainDialog.setContentText("Do you want to play again?");

            ButtonType playAgainButton = new ButtonType("Play Again");
            ButtonType exitButton = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
            playAgainDialog.getButtonTypes().setAll(playAgainButton, exitButton);

            Optional<ButtonType> result = playAgainDialog.showAndWait();
            if (result.isPresent() && result.get() == playAgainButton) {
                resetGame(container);
            }
            else {
                System.out.println("Thanks");
                stage.setScene(firstScene);
                resetGame(container);
            }
        }
    }
    private static void resetGame(GridPane container){
        for(Node n : container.getChildren()){
            if (n instanceof Button){
                System.out.println("btn found");
                Button btn = (Button) n;
                btn.setDisable(false);
                ((Button) n).setText("");
            }
        }
        clearGameBoard();
//        currentPlayer = "X";
        System.out.println("Current player reset to : "+ currentPlayer);
    }
    private static void clearGameBoard(){
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col] = "";
            }
        }

    }

}

