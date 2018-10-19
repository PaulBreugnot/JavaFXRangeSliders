package controls;

import controls.simpleSlider.SimpleSliderSkin;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public abstract class Bar {
	// Layout parameters
	public static final double barHeight = 10;

	// Components
	protected Rectangle bar1;

	// RangeSlider
	protected Slider slider;

	protected SimpleDoubleProperty width = new SimpleDoubleProperty();

	public Bar(Slider slider) {
		this.slider = slider;
		initBars();
		initBarWidthProperty();
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
	
	/*****************************************************************/
	/* The following functions need to be implemented according
	 * to the desired slider behavior.
	 */
	protected abstract void initBars();
	
	protected abstract void layoutBars(double width);
	
	protected abstract void initBarWidthProperty();
	
	protected abstract void setColors(double value1, double value2);
	/*****************************************************************/
	
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
