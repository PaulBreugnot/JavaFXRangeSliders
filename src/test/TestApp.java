package test;

import controls.rangeSlider.RangeSlider;
import controls.rangeSlider.colorRangeSlider.ColorRangeSlider;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TestApp extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        Control control1 = new RangeSlider(0, 10, 2, 5, RangeSlider.Mode.LINEAR);
        Control control2 = new RangeSlider(0, 10, 2, 5, RangeSlider.Mode.LINEAR);
        control2.getStylesheets().clear();
        control2.getStylesheets().add("style/test.css");
        Control control3 = new ColorRangeSlider(100, 150);
        root.getChildren().addAll(control1);
        root.getChildren().addAll(control2);
        root.getChildren().addAll(control3);
        // VBox.setVgrow(control, Priority.ALWAYS);
        Scene scene = new Scene(root, 320, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
