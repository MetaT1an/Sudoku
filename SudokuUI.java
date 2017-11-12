package six;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
/*
this class is designed to add UI elements of the plain Sudo game
define the simple rules of the users' operation on ui
and the layout of all these elements
 */
public class SudoUI {
    private Sudo rule;
    private Font fontButton;
    private Font fontDigit;

    private GridPane board;
    private GridPane infoCenter;
    private BorderPane frame;

    private TextField result;
    private Button[] control;
    private TextField[] grid;

    public SudoUI(){
        this.rule = new Sudo();
        this.infoCenter = new GridPane();
        this.board = new GridPane();
        this.frame = new BorderPane();
        this.grid = new TextField[81];
        this.result = new TextField();
        this.control = new Button[4];
        this.fontButton = Font.font(null, FontWeight.NORMAL,20);
        this.fontDigit = Font.font(null, FontWeight.BOLD,30);
    }
    public void init(){
        result.setPrefSize(150,40);
        result.setEditable(false);
        result.setAlignment(Pos.CENTER);

        control[0] = new Button("Clear");
        control[1] = new Button("Restart");
        control[2] = new Button("Quit");
        control[3] = new Button("Check");
        for(int i = 0;i<4;++i){
            control[i].setPrefSize(150,40);
            control[i].setFont(fontButton);
        }
        control[2].setStyle("-fx-background-color:rgba(230,0,0);");
        control[0].setStyle("-fx-background-color:orangered;");
        control[1].setStyle("-fx-background-color:rgba(0,230,0);");
        control[3].setStyle("-fx-background-color:rgba(72,118,255);");
        //set the property of all the textfields
        for(int i = 0;i<81;++i){
            grid[i] = new TextField();
            grid[i].setPrefSize(60,60);
            grid[i].setFont(fontDigit);
            grid[i].setAlignment(Pos.CENTER);
        }
    }
    public void load(){
        for(int i = 0;i<81;++i)
            board.add(grid[i],i/9,i%9);
        for(int i = 0;i<4;++i){
            infoCenter.add(control[i],0,i);
            infoCenter.setVgap(30);
        }
        infoCenter.add(result,0,7);
        board.setGridLinesVisible(true);
        frame.setRight(infoCenter);
        frame.setCenter(board);
        BorderPane.setMargin(infoCenter,new Insets(40,20,0,0));
    }
    public BorderPane getFrame(){
        return frame;
    }
    public Button getControl(int i){
        return control[i];
    }

    public void setGame(){
        for(int i = 0;i<9;++i)
            for(int j = 0;j<9;++j){
                //set the different color in textfields ,hence,the area
                //of the 9 small square are clearer
                boolean isGray = (i/3 + j/3) % 2 == 0;

                int content = rule.getDigit(i,j);
                if(content!=0 && isGray){
                    grid[i*9+j].setEditable(false);
                    grid[i*9+j].setStyle("-fx-background-color: lightgray;");
                    grid[i*9+j].setText(String.valueOf(content));
                }
                else if(content == 0 && isGray){
                    //for user to fill in
                    grid[i*9+j].setEditable(true);
                    grid[i*9+j].setText("");
                    grid[i*9+j].setStyle("-fx-background-color: lightgray;-fx-text-fill:blue;");
                }
                else if(content == 0 && !isGray){
                    grid[i*9+j].setEditable(true);
                    grid[i*9+j].setText("");
                    grid[i*9+j].setStyle("-fx-text-fill:blue;");
                }
                else{
                    grid[i*9+j].setEditable(false);
                    grid[i*9+j].setText(String.valueOf(content));
                }
            }
    }
    public boolean checkValid(){

        for(int i = 0;i<9;++i)
            for(int j = 0;j<9;++j){
                //find the grid in which user input answer
                if(grid[i*9+j].isEditable()){
                    String data = grid[i*9+j].getText();
                    int value = (data.equals("")) ? 0: Integer.parseInt(data);//if no input data
                    if(!rule.isValid(i,j,value))
                        return false;
                }
            }
            return true;
    }

    //restart the game using new game scheme
    public void reStart(){
        this.rule = new Sudo();//new rule
        cleanStrains();
        setGame();
    }

    //clear all the answers that user input
    public void clean(){
        for(int i = 0;i<9;++i)
            for(int j = 0;j<9;++j){
                if(grid[i*9+j].isEditable())
                    grid[i*9+j].clear();
            }
    }

    //clear all the data in grid[]
    public void cleanStrains(){
        for(int i = 0;i<81;i++){
            grid[i].clear();
            grid[i].setStyle("");//need clear the style of the textfield too
        }
    }
    public void setResult(String resultInfo){
        result.setText(resultInfo);
        result.setFont(fontButton);
    }
}
