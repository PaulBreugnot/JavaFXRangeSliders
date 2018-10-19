package controls.simpleSlider;

import controls.Cursor;
import controls.rangeSlider.RangeSlider;
import controls.rangeSlider.RangeSliderSkin;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Bar {
	// Layout parameters
	public static final double barHeight = 10;

	// Components
	protected Rectangle bar1;

	// RangeSlider
	protected SimpleSlider slider;

	protected SimpleDoubleProperty width = new SimpleDoubleProperty();

	public Bar(SimpleSlider slider) {
		this.slider = slider;
		initBars();
		initBarWidthProperty();
	}

	protected void layoutBars(double width) {
		bar1.setWidth(width);
		bar1.setLayoutX(SimpleSliderSkin.horizontalPadding + Cursor.cursorSize);
	}

	public void setWidth(double width) {
		layoutBars(width);
	}

	public double getWidth() {
		return width.get();
	}

	public double getX() {
		return bar1.getLayoutX() - (SimpleSliderSkin.horizontalPadding + Cursor.cursorSize);
	}

	public double getY() {
		return bar1.getLayoutY();
	}

	public SimpleDoubleProperty widthProperty() {
		return width;
	}

	public Rectangle getBar1() {
		return bar1;
	}
	
	public double getStrokeWidth() {
		return bar1.getStrokeWidth();
	}

	protected void initBars() {
		bar1 = new Rectangle();
		bar1.setHeight(barHeight);
		bar1.setId("bar");
		bar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				((SimpleSliderSkin) slider.getSliderSkin()).getMidCursor()
						.updateValue(event.getX() + SimpleSliderSkin.horizontalPadding + Cursor.cursorSize / 2, width.get());
			}
		});

		bar1.setHeight(barHeight);
	}

	protected void initBarWidthProperty() {
		width.bind(bar1.widthProperty());
	}

	public void setColors(double value1, double value2) {
		bar1.setFill(subLinearGradient(0, 360));
	}

	protected LinearGradient subLinearGradient(double begin, double end) {
		int pointsNumber = 50;
		Stop[] stops = new Stop[pointsNumber];
		for (int i = 0; i < pointsNumber; i++) {
			double index = (double) i / (pointsNumber - 1);
			stops[i] = new Stop(index, Color.hsb(begin + index * (end - begin), 1, 1));
		}
		return new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	}
}
