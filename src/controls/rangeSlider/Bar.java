package controls.rangeSlider;

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
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
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
		bar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				rangeSlider.getRangeSliderSkin().getMinCursor().updateValue(
						event.getX() + RangeSliderSkin.horizontalPadding, width.get());
			}
		});

		barMid = new Rectangle();
		barMid.setHeight(barHeight);
		barMid.setId("bar");
		barMid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getX() < barMid.getWidth() / 2) {
					rangeSlider.getRangeSliderSkin().getMinCursor().updateValue(
							event.getX() + bar1.getWidth() + RangeSliderSkin.horizontalPadding,
							width.get());
				}
				else {
					rangeSlider.getRangeSliderSkin().getMaxCursor().updateValue(
							bar1.getWidth() + event.getX()
							+ RangeSliderSkin.horizontalPadding + Cursor.cursorSize,
							width.get());
				}
			}
		});

		bar2 = new Rectangle();
		bar2.setHeight(barHeight);
		bar2.setId("bar");
		bar2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				rangeSlider.getRangeSliderSkin().getMaxCursor().updateValue(
						bar1.getWidth() + barMid.getWidth() + event.getX()
						+ RangeSliderSkin.horizontalPadding + Cursor.cursorSize,
						width.get());
			}
		});

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
	
	public ChangeListener<Number> colorSetterForValue1 () {
		return new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				layoutBars((double) newValue, rangeSlider.getValue2(), width.get());
			}
		};
	}
	
	public void linkColorListeners() {
		rangeSlider.value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors((double) newValue, rangeSlider.getValue2());
			}
		});
		
		rangeSlider.value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors(rangeSlider.getValue1(), (double) newValue);
			}
		});
	}
	
	public void setColors(double value1, double value2) {
		internSelected.set(value1 <= value2);
		if (internSelected.get()) {
			bar1.setFill(subLinearGradient(0, value1));
			rangeSlider.getRangeSliderSkin().getMinCursor().setFill(Color.hsb(value1, 1, 1));

			barMid.setFill(subLinearGradient(value1, value2));
			rangeSlider.getRangeSliderSkin().getMidCursor().setFill(Color.hsb((value2 + value1) / 2, 1, 1));
			
			bar2.setFill(subLinearGradient(value2, 360));
			rangeSlider.getRangeSliderSkin().getMaxCursor().setFill(Color.hsb(value2, 1, 1));
			
		} else {
			bar1.setFill(subLinearGradient(0, value2));
			rangeSlider.getRangeSliderSkin().getMinCursor().setFill(Color.hsb(value1, 1, 1));

			barMid.setFill(subLinearGradient(value2, value1));
			rangeSlider.getRangeSliderSkin().getMidCursor().setFill(Color.hsb(rangeSlider.getValueMid(), 1, 1));
			
			bar2.setFill(subLinearGradient(value1, 360));
			rangeSlider.getRangeSliderSkin().getMaxCursor().setFill(Color.hsb(value2, 1, 1));

		}
	}
	
	private LinearGradient subLinearGradient(double begin, double end) {
		int pointsNumber = 50;
		Stop[] stops = new Stop[pointsNumber];
		for (int i = 0; i < pointsNumber; i++) {
			double index = (double) i / (pointsNumber - 1);
			stops[i] = new Stop(index, Color.hsb(begin + index * (end - begin), 1, 1));
		}
		return new LinearGradient(0, 0, 1, 0, true,
		CycleMethod.NO_CYCLE, stops);
	}
}
