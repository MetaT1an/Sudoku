package sudoku;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
/*
this class is designed to organize all the elements when playing
add some events on the game
 */
public class SudoGame extends Application{
    private SudokuUI ui = new SudokuUI();

    public void start(Stage stage){
        ui.init();
        ui.load();
        ui.setGame();
        addClearEvent();
        addRestartEvent();
        addQuitEvent();
        addCheckEvent();

        Scene scene = new Scene(ui.getFrame(),750,600);
        stage.setScene(scene);

        stage.show();
    }
    public void addClearEvent(){
        ui.getControl(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ui.clean();
            }
        });
    }
    public void addRestartEvent(){
        ui.getControl(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ui.reStart();
            }
        });
    }
    public void addQuitEvent(){
        ui.getControl(2).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }
    public void addCheckEvent(){
        ui.getControl(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(ui.checkValid())
                    ui.setResult("Correct!");
                else
                    ui.setResult("Incorrect!");
            }
        });
    }
}

