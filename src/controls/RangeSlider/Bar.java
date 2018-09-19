package controls.RangeSlider;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Bar {
	// Layout parameters
	public static final double barHeight = 10;

	// Components
	private Rectangle bar1;
	private Rectangle barMid;
	private Rectangle bar2;

	// Colors
	private SimpleBooleanProperty internSelected = new SimpleBooleanProperty();
	private double opacity1;
	private double opacityMid;
	private double opacity2;

	// RangeSlider
	private RangeSlider rangeSlider;

	private SimpleDoubleProperty width = new SimpleDoubleProperty();

	public Bar(RangeSlider rangeSlider) {
		this.rangeSlider = rangeSlider;
		initBars();
		initOpacityListener();
		width.bind(Bindings.add(Bindings.add(bar1.widthProperty(), barMid.widthProperty()), bar2.widthProperty()));
	}

	private void layoutBars(double value1, double value2, double width) {
		internSelected.set(value1 <= value2);
		if (internSelected.get()) {
			double bar1Distance = (value1 - rangeSlider.getMinValue())
					* width / (rangeSlider.getMaxValue() - rangeSlider.getMinValue());
			bar1.setWidth(bar1Distance);
			bar1.setLayoutX(RangeSliderSkin.horizontalPadding + Cursor.cursorSize);

			barMid.setWidth(((value2 - value1) - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()));
			barMid.setLayoutX(bar1.getLayoutX() + bar1.getWidth());
			
			double bar2Distance = ((rangeSlider.getMaxValue() - value2) - rangeSlider.getMinValue()) * width
			/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue());
			bar2.setWidth(bar2Distance);
			bar2.setLayoutX(barMid.getLayoutX() + barMid.getWidth());
			
		} else {
			bar1.setWidth((value2 - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()));
			bar1.setLayoutX(RangeSliderSkin.horizontalPadding + Cursor.cursorSize);

			barMid.setWidth(((value1 - value2) - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()));
			barMid.setLayoutX(bar1.getLayoutX() + bar1.getWidth());

			bar2.setWidth(((rangeSlider.getMaxValue() - value1) - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()));
			bar2.setLayoutX(barMid.getLayoutX() + barMid.getWidth());

		}
	}

	public void setWidth(double width) {
		layoutBars(rangeSlider.getValue1(), rangeSlider.getValue2(), width);
	}

	public double getWidth() {
		return width.get();
	}

	public double getX() {
		return bar1.getLayoutX() - (RangeSliderSkin.horizontalPadding + Cursor.cursorSize);
	}

	public double getY() {
		return barMid.getLayoutY();
	}

	public SimpleDoubleProperty widthProperty() {
		return width;
	}

	public Rectangle getBar1() {
		return bar1;
	}

	public Rectangle getBarMid() {
		return barMid;
	}

	public Rectangle getBar2() {
		return bar2;
	}

	private void initBars() {
		bar1 = new Rectangle();
		bar1.setHeight(barHeight);
		bar1.setId("bar");
		//bar1.setStyle("-fx-fill: red;");
//		bar1.setStroke(Color.BLACK);
//		bar1.setStrokeWidth(1);
//		bar1.setFill(fill1);

		barMid = new Rectangle();
		barMid.setHeight(barHeight);
		barMid.setId("bar");
//		barMid.setStroke(Color.BLACK);
//		barMid.setStrokeWidth(1);
//		barMid.setFill(fillMid);

		bar2 = new Rectangle();
		bar2.setHeight(barHeight);
		bar2.setId("bar");
//		bar2.setStroke(Color.BLACK);
//		bar2.setStrokeWidth(1);
//		bar2.setFill(fill2);

		bar1.setHeight(barHeight);
		barMid.setHeight(barHeight);
		bar2.setHeight(barHeight);

		rangeSlider.value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				layoutBars((double) newValue, rangeSlider.getValue2(), width.get());
			}
		});

		rangeSlider.value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				layoutBars(rangeSlider.getValue1(), (double) newValue, width.get());
			}
		});
	}
	
	private void initOpacityListener() {
		internSelected.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					bar1.setOpacity(0.5);
					barMid.setOpacity(1);
					bar2.setOpacity(0.5);
				}
				else {
					bar1.setOpacity(1);
					barMid.setOpacity(0.5);
					bar2.setOpacity(1);
				}
			}
		});
	}
	
}
