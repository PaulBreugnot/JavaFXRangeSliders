package controls.rangeSlider;

import controls.Bar;
import controls.Cursor;
import controls.simpleSlider.SimpleSliderSkin;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RangeBar extends Bar {
	// Layout parameters
	public static final double barHeight = 10;

	// Components
	private Rectangle barMid;
	private Rectangle bar2;

	// Colors
	private SimpleBooleanProperty internSelected = new SimpleBooleanProperty();

	public RangeBar(RangeSlider rangeSlider) {
		super(rangeSlider);
		initOpacityListener();
	}

	@Override
	protected void layoutBars(double width) {
		RangeSlider rangeSlider = (RangeSlider) slider;
		double value1 = rangeSlider.getValue1();
		double value2 = rangeSlider.getValue2();
		internSelected.set(value1 <= value2);
		if (internSelected.get()) {
			double bar1Distance = (value1 - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue());
			bar1.setWidth(bar1Distance);
			bar1.setLayoutX(SimpleSliderSkin.horizontalPadding + Cursor.cursorSize);

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
			bar1.setLayoutX(SimpleSliderSkin.horizontalPadding + Cursor.cursorSize);

			barMid.setWidth(((value1 - value2) - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()));
			barMid.setLayoutX(bar1.getLayoutX() + bar1.getWidth());

			bar2.setWidth(((rangeSlider.getMaxValue() - value1) - rangeSlider.getMinValue()) * width
					/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()));
			bar2.setLayoutX(barMid.getLayoutX() + barMid.getWidth());

		}
	}

	public Rectangle getBarMid() {
		return barMid;
	}

	public Rectangle getBar2() {
		return bar2;
	}

	@Override
	protected void initBars() {
		bar1 = new Rectangle();
		bar1.setHeight(barHeight);
		bar1.setId("bar");
		bar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				((RangeSliderSkin) slider.getSliderSkin()).getMinCursor()
						.updateValue(event.getX() + SimpleSliderSkin.horizontalPadding, width.get());
			}
		});

		barMid = new Rectangle();
		barMid.setHeight(barHeight);
		barMid.setId("bar");
		barMid.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (event.getX() < barMid.getWidth() / 2) {
					((RangeSliderSkin) slider.getSliderSkin()).getMinCursor().updateValue(
							event.getX() + bar1.getWidth() + SimpleSliderSkin.horizontalPadding, width.get());
				} else {
					((RangeSliderSkin) slider.getSliderSkin()).getMaxCursor().updateValue(
							bar1.getWidth() + event.getX() + SimpleSliderSkin.horizontalPadding + Cursor.cursorSize,
							width.get());
				}
			}
		});

		bar2 = new Rectangle();
		bar2.setHeight(barHeight);
		bar2.setId("bar");
		bar2.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				((RangeSliderSkin) slider.getSliderSkin()).getMaxCursor().updateValue(bar1.getWidth()
						+ barMid.getWidth() + event.getX() + SimpleSliderSkin.horizontalPadding + Cursor.cursorSize,
						width.get());
			}
		});

		bar1.setHeight(barHeight);
		barMid.setHeight(barHeight);
		bar2.setHeight(barHeight);

		((RangeSlider) slider).value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				layoutBars(width.get());
			}
		});

		((RangeSlider) slider).value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				layoutBars(width.get());
			}
		});
	}

	@Override
	protected void initBarWidthProperty() {
		width.bind(Bindings.add(Bindings.add(bar1.widthProperty(), barMid.widthProperty()), bar2.widthProperty()));
	}

	private void initOpacityListener() {
		internSelected.addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) {
					bar1.setOpacity(0.5);
					barMid.setOpacity(1);
					bar2.setOpacity(0.5);
				} else {
					bar1.setOpacity(1);
					barMid.setOpacity(0.5);
					bar2.setOpacity(1);
				}
			}
		});
	}

	public void linkColorListeners() {
		((RangeSlider) slider).value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors((double) newValue, ((RangeSlider) slider).getValue2());
			}
		});

		((RangeSlider) slider).value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors(((RangeSlider) slider).getValue1(), (double) newValue);
			}
		});
	}

	@Override
	public void setColors(double value1, double value2) {
		internSelected.set(value1 <= value2);
		if (internSelected.get()) {
			bar1.setFill(subLinearGradient(0, value1));
			((RangeSlider) slider).getRangeSliderSkin().getMinCursor().setFill(Color.hsb(value1, 1, 1));

			barMid.setFill(subLinearGradient(value1, value2));
			((RangeSlider) slider).getRangeSliderSkin().getMidCursor().setFill(Color.hsb((value2 + value1) / 2, 1, 1));

			bar2.setFill(subLinearGradient(value2, 360));
			((RangeSlider) slider).getRangeSliderSkin().getMaxCursor().setFill(Color.hsb(value2, 1, 1));

		} else {
			bar1.setFill(subLinearGradient(0, value2));
			((RangeSlider) slider).getRangeSliderSkin().getMinCursor().setFill(Color.hsb(value1, 1, 1));

			barMid.setFill(subLinearGradient(value2, value1));
			((RangeSlider) slider).getRangeSliderSkin().getMidCursor()
					.setFill(Color.hsb(((RangeSlider) slider).getValueMid(), 1, 1));

			bar2.setFill(subLinearGradient(value1, 360));
			((RangeSlider) slider).getRangeSliderSkin().getMaxCursor().setFill(Color.hsb(value2, 1, 1));

		}
	}
}
