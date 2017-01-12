import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AutoClickerFX extends Application {

	static int numClicks = 350;
	static int interval = 5;
	static int delay = 4000;

	TextField numClicksField, intervalField, delayField;
	CheckBox holdControl;

	 @Override
	    public void start(Stage primaryStage) {

		 	GridPane grid = new GridPane();
	        grid.setAlignment(Pos.CENTER);
	        grid.setHgap(10);
	        grid.setVgap(10);
	        grid.setPadding(new Insets(25, 25, 25, 25));

		 	Text scenetitle = new Text("Auto-Clicker");
	        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 30));
	        grid.add(scenetitle, 0, 0, 2, 1);

	        Label numClicksLabel = new Label("Number of Clicks:");
	        grid.add(numClicksLabel, 0, 1);

	        numClicksField = new TextField(numClicks + "");
	        grid.add(numClicksField, 1, 1);

	        Label intervalLabel = new Label("Interval:");
	        grid.add(intervalLabel, 0, 2);

	        intervalField = new TextField(interval + "");
	        grid.add(intervalField, 1, 2);

	        Label delayLabel = new Label("Delay:");
	        grid.add(delayLabel, 0, 3);

	        delayField = new TextField(delay + "");
	        grid.add(delayField, 1, 3);

	        holdControl = new CheckBox("Control On");
	        holdControl.setSelected(true);
	        grid.add(holdControl, 0, 4);


	        Button btn = new Button();
	        btn.setText("Begin Clicking");


	        HBox hbBtn = new HBox(10);
	        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	        hbBtn.getChildren().add(btn);
	        grid.add(hbBtn, 1, 4);

	        final Text actiontarget = new Text();
	        grid.add(actiontarget, 1, 6);

	        btn.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent e) {

	            	actiontarget.setText(" ");

	            	int currentDelay = Integer.parseInt(delayField.getText());
	            	int currentNumClicks = Integer.parseInt(numClicksField.getText());
	            	int currentInterval = Integer.parseInt(intervalField.getText());

	            	Task<Void> sleeper = new Task<Void>() {
	                    @Override
	                    protected Void call() throws Exception {
	                        try {
	                        	Robot robot = new Robot();
	                            Thread.sleep(currentDelay);

	                            if(holdControl.isSelected()) {
	                            	robot.keyPress(KeyEvent.VK_CONTROL);
	                            }

	                			for( int i=0; i<currentNumClicks; i++ ) {
	                				robot.mousePress(InputEvent.BUTTON1_MASK);
	                				robot.mouseRelease(InputEvent.BUTTON1_MASK);

	                				Thread.sleep(currentInterval);
	                			}

	                			if(holdControl.isSelected()) {
	                				robot.keyRelease(KeyEvent.VK_CONTROL);
	                			}
	                		}
	                        catch (InterruptedException e) {
	                        }
	                        return null;
	                    }
	                };

	                sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
	                    @Override
	                    public void handle(WorkerStateEvent event) {
	    	                actiontarget.setFill(Color.FIREBRICK);
	                    	actiontarget.setText("Completed.");
	                    }
	                });

	                new Thread(sleeper).start();
	            }
	        });

	        Scene scene = new Scene(grid, 325, 250);
	        primaryStage.setScene(scene);
	        primaryStage.show();

	        primaryStage.setTitle("AutoClicker");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	    }
	 public static void main(String[] args) {
	        launch(args);
	    }
}
