package test;

import sliders.colorSlider.colorRangeSlider.RangeColorSlider;
import sliders.colorSlider.simpleColorSlider.SimpleColorSlider;
import sliders.rangeSlider.RangeSlider;
import sliders.simpleSlider.SimpleSlider;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sliders.Slider;
import sliders.colorSlider.ColorSelectionMode;

public class TestApp extends Application {
	@Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);
        // Control control1 = new RangeSlider(0, 10, 2, 5, RangeSlider.Mode.LINEAR);
        // Control control2 = new RangeSlider(0, 10, 2, 5, RangeSlider.Mode.CYCLIC);
        // control2.getStylesheets().clear();
        // control2.getStylesheets().add("style/test.css");
        // Control control3 = SimpleColorSlider.SimpleHueColorSlider(60, 1, 1);
        // Control control3 = new SimpleColorSlider(0, 360, 60, Slider.Mode.CYCLIC, ColorSelectionMode.HUE);
        // Control control4 = new SimpleSlider(0, 10, 10, RangeSlider.Mode.CYCLIC);
        // Control control5 = RangeColorSlider.RangeHueSlider(40, 160, 0.5, 0.5);
        Control control5 = RangeColorSlider.RangeBrightnessSlider(0.2, 0.6, 100, 0.7);
        Control control6 = SimpleColorSlider.SimpleSaturationColorSlider(0.5, 100, 0.8);
        Control control7 = SimpleColorSlider.SimpleBrightnessColorSlider(0.5, 100, 0.5);
        // root.getChildren().add(control1);
        // root.getChildren().add(control2);
        // root.getChildren().add(control3);
        // root.getChildren().add(control4);
        root.getChildren().add(control5);
        root.getChildren().add(control6);
        root.getChildren().add(control7);
        // VBox.setVgrow(control, Priority.ALWAYS);
        Scene scene = new Scene(root, 320, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
