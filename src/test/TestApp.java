package test;

import controls.RangeSlider.RangeSlider;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestApp extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        Control control = new RangeSlider(0, 10, 0.1, 9.9, RangeSlider.Mode.LINEAR);
        root.getChildren().addAll(control);
        VBox.setVgrow(control, Priority.ALWAYS);
        Scene scene = new Scene(root, 320, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
