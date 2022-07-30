package tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField player1Score_textfield;

    @FXML
    private TextField player2Score_textField;

    @FXML
    private Button top_left_button;

    @FXML
    private Button top_middle_button;

    @FXML
    private Button top_right_button;

    @FXML
    private Button center_left_button;

    @FXML
    private Button center_middle_button;

    @FXML
    private Label titleLabel;

    @FXML
    private Button bottom_left_button;

    @FXML
    private Button bottom_middle_button;

    @FXML
    private Button bottom_right_button;

    @FXML
    private Label gameDialogue;

    @FXML
    private Label player1ScoreLabel;

    @FXML
    private Label player2ScoreLabel;

    @FXML
    private Button resetButton;


    private final int BOARD_SIZE = 3;

    private final String PLAYER1_CHARACTER = "X";
    private final String PLAYER2_CHARACTER = "O";

    private final String PLAYER1_DIALOGUE = "Player 1 Turn";
    private final String PLAYER2_DIALOGUE = "Player 2 Turn";
    private final String INVALID_MOVE_DIALOGUE = "Invalid Move";

    private String[][] board;

    private String currentPlayerCharacter;
    private boolean isPlayer1Turn;

    private Alert playerWinsAlert;
    private Alert unknownErrorAlert;
  
    public  Controller() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        intitializeBoard();

        currentPlayerCharacter = PLAYER1_CHARACTER;
        isPlayer1Turn = true;

        playerWinsAlert = new Alert(AlertType.INFORMATION);
        playerWinsAlert.setTitle("You win.");
        playerWinsAlert.setContentText("Player " + currentPlayerCharacter +  " has won. ");

        unknownErrorAlert = new Alert(AlertType.ERROR);
        unknownErrorAlert.setTitle("Unknown Error.");
        unknownErrorAlert.setContentText("An unknown error has occurred. Program will be exiting.");
    }

    void intitialize(){

    }

    void intitializeBoard(){
       try {
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++)
                board[i][j] = "";

       } catch (Exception e) {
            System.out.println("Error while initiaizing board.");
            System.exit(1);
       }
    }
    
   
    void updateTurnLabel(String newPrompt){
        gameDialogue.setText(newPrompt);
    }


    //Validation done beforehand
    private void updateGrid(int row, int col, String characterToWrite){

        Node nodeToUpdate = gridPane.getChildren().get( ((row * 3) + col) );
        ((Button)nodeToUpdate).setText(characterToWrite);
        
        //Debugging
        // Integer currX = gridPane.getColumnIndex(nodeToUpdate);
        // Integer currY = gridPane.getRowIndex(nodeToUpdate);
        // System.out.println("Node Retrieved: " + "(" + currX + ", " + currY  + ")");
        
    }

   

    @FXML
    void onBoardSpotClicked(ActionEvent event){
        Node sourceNode = (Node)(event.getTarget());

        //System.out.println("Source x,y: " + GridPane.getColumnIndex(sourceNode) + " " + GridPane.getRowIndex(sourceNode));
        Integer row = GridPane.getRowIndex(sourceNode);
        Integer col = GridPane.getColumnIndex(sourceNode);

        if(row == null)
            row = 0;
        
        if(col == null)
            col = 0;
        
        System.out.println("\n\n  x,y:" + row + " " + col );

        //Check if valid button
        try {
            if(board[row][col] == null){
                //Will throw NullPointerException if null. 
            }
            
        }catch(Exception e){
            System.out.println(INVALID_MOVE_DIALOGUE);
            updateTurnLabel(INVALID_MOVE_DIALOGUE);
            return;
        }

        //Mark Spot with appropriate value
        board[row][col] = currentPlayerCharacter;

        updateGrid(row, col, currentPlayerCharacter);
        printBoard();

        //Check win
        boolean hasWon = checkWin(currentPlayerCharacter);
        System.out.println("has won: " + hasWon);
        
        if (hasWon){
            String winDialogue = "Player " + currentPlayerCharacter + " has won.";
            System.out.println(winDialogue);
            updateTurnLabel(winDialogue);
            playerWinsAlert.setContentText("Player " + currentPlayerCharacter +  " has won. ");
            playerWinsAlert.showAndWait();
            updateScores();
            clearBoard();
            return;
        }

        if(isPlayer1Turn){
            currentPlayerCharacter = PLAYER2_CHARACTER;
            updateTurnLabel(PLAYER2_DIALOGUE);
        } 
        else{
            currentPlayerCharacter = PLAYER1_CHARACTER;
            updateTurnLabel(PLAYER1_DIALOGUE);
        }
            
        isPlayer1Turn = !(isPlayer1Turn);

    }

    private void updateScores(){
        try{
            if(isPlayer1Turn)
                player1ScoreLabel.setText(  String.valueOf ( Integer.parseInt((player1ScoreLabel).getText()) + 1 ) );
            else 
                player2ScoreLabel.setText(  String.valueOf ( Integer.parseInt((player2ScoreLabel).getText()) + 1 ) );
        }catch(Exception e){
            System.out.println("An error has occurred while updating the score.");
            System.exit(1);
        }
    }
    
    /*
      In case that the board was instead very large it would be more computationally effictive to 
      change this mehod.
     */

    boolean checkWin(String playerCharacter){
        
       boolean winsRow = true;
       boolean winsCol = true;
       boolean winsLeftDiag = true;
       boolean winsRightDiag = true;
        
        for(int i = 0; i < BOARD_SIZE; i++) {
            for(int j = 0; j < BOARD_SIZE; j++){    
                //System.out.println("i, j: " + i + " " + j);     
                if ( !board[i][j].equals(playerCharacter) )
                    winsRow = false;
                if( !board[j][i].equals(playerCharacter) )
                    winsCol = false;
            }
            
            if (winsCol || winsRow){
                //DEBUG
                System.out.println("Wins row: " + winsRow);
                System.out.println("Wins col: " + winsCol);

                return true;
            } else   
                winsCol = winsRow = true;
            

            if( !board[i][i].equals(playerCharacter) )
               winsLeftDiag = false;

            if( !board[i][BOARD_SIZE - i - 1].equals(playerCharacter) )
               winsRightDiag = false;
        }
        //DEBUG
        System.out.println(
           "\nleft diag win: " + winsLeftDiag
        +   "\nright diag win: " + winsRightDiag
            );

        return (winsRightDiag || winsLeftDiag );
    }

    @FXML
    void onResetGame() {
        player1ScoreLabel.setText("0");
        player2ScoreLabel.setText("0");

        clearBoard();
        
    }

    void clearBoard(){
        for(Node node : gridPane.getChildren())
            try {
                ((Button)(node)).setText("");
            } catch (Exception e) {
               // Strange Group casting exception occurrs. Do nothing.
            }

       for (int i = 0; i < board.length; i++) 
            for (int j = 0; j < board.length; j++) 
                board[i][j] = ""; 
           
        intitializeBoard(); //resets the internal 2d array with empty slots
        currentPlayerCharacter = PLAYER1_CHARACTER;
        isPlayer1Turn = true;
        updateTurnLabel(PLAYER1_DIALOGUE);
    }
    
    //DEBUG
    void printBoard(){
        for(String[] row : board){
            for(String item: row)
                System.out.print(item + " ");
            System.out.println();
        }

        System.out.println();
           
    }
}
