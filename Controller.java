package tictactoe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Window;

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


    private final int BOARD_SIZE = 3;

    private final int  PLAYER1_TURN = 0;
    private final int PLAYER2_TURN = 1;

    private final String PLAYER1_CHARACTER = "X";
    private final String PLAYER2_CHARACTER = "O";

    private final String PLAYER1_DIALOGUE = "Player 1 Turn";
    private final String PLAYER2_DIALOGUE = "Player 2 Turn";
    private final String INVALID_MOVE_DIALOGUE = "Invalid Move";



    private String[][] board;
    private int turn; 
    private String currentPlayer;
    private boolean player1Turn;
  

    public  Controller() {
        board = new String[3][3];
        turn = 0;

        currentPlayer = PLAYER1_CHARACTER;

        player1Turn = true;
    }

    void intitialize(){

    }
    
    boolean checkWin(String playerCharacter){
        
        int rowCount = 0; 
        int colCount = 0;

        try {
            
            for(int i = 0; i < BOARD_SIZE; i++) {
                for(int j = 0; i < BOARD_SIZE; i++){
                    if(board[i][j].equals(playerCharacter))
                        rowCount++;
                    if(board[j][i].equals(playerCharacter))
                        colCount++;
                }

                if (rowCount == BOARD_SIZE || colCount == BOARD_SIZE)
                    return true;
                else 
                    rowCount = colCount = 0;
        

            }

        } catch (Exception e) {
           
        }
              
                
        return false;
    }


    void updateDialogueLabel(String newPrompt){
        gameDialogue.setText(newPrompt);
    }


    //Validation done beforehand
    private void updateGrid(int row, int col, String characterToWrite){

        Node nodeToUpdate = gridPane.getChildren().get( ((row * 3) + col) );

        Button selectedSpot = (Button)nodeToUpdate;
        selectedSpot.setText(characterToWrite);

        Integer currX = gridPane.getColumnIndex(nodeToUpdate);
        Integer currY = gridPane.getRowIndex(nodeToUpdate);

        System.out.println("Node Retrieved: " + "(" + currX + ", " + currY  + ")");
        
        
        // for(Node node : gridPane.getChildren()){
            
        //     if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == col){
        //         ((Button)node).setText(characterToWrite);
        //     }
        // }
    }
    @FXML
    void boardItemClicked(ActionEvent event){
        

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
                if(board[row][col] != null){
                    //Will throw NullPointerException if null. 
                }

               
            }catch(Exception e){

                System.out.println("Error Here");
                System.out.println(INVALID_MOVE_DIALOGUE);
                updateDialogueLabel(INVALID_MOVE_DIALOGUE);
       
                return;

                
            }


            System.out.println("No Error Here");

            //Mark Spot with appropriate value
            board[row][col] = currentPlayer;

            updateGrid(row, col, currentPlayer);

            //Check win
            boolean hasWon = checkWin(currentPlayer);

            if (hasWon){
                String winDialogue = "Player " + currentPlayer + "has won.";
                System.out.println(winDialogue);
                updateDialogueLabel(winDialogue);

                return;
            }
                    

            if(player1Turn){
                currentPlayer = PLAYER2_CHARACTER;
                updateDialogueLabel(PLAYER2_DIALOGUE);
            }
                
            else{
                currentPlayer = PLAYER1_CHARACTER;
                updateDialogueLabel(PLAYER2_DIALOGUE);
            }
                

            player1Turn = !(player1Turn);
            
            
        

               

            
            



            // for(Node node : gridPane.getChildren()){

            //     Integer currX = gridPane.getColumnIndex(node);
            //     Integer currY = gridPane.getRowIndex(node);

            //     if (currX == null)
            //         currX = 0;
            //     if (currY == null)
            //         currY = 0;

            //     System.out.println("col: " + currX + " row: " + currY);
                
            // }
    }
    
    

}
