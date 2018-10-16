package controls.RangeSlider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class RangeSlider extends Control {

	private RangeSliderSkin rangeSliderSkin;

	public static enum Mode {
		LINEAR, CYCLIC
	}

	private Mode mode = Mode.LINEAR;

	private double minValue;
	private double maxValue;
	private SimpleDoubleProperty value1;
	private SimpleDoubleProperty valueMid;
	private SimpleDoubleProperty value2;
	private SimpleDoubleProperty range;

	public RangeSlider(double minValue, double maxValue, double value1, double value2, Mode mode) {
		this(minValue, maxValue, value1, value2);
		this.mode = mode;
	}

	public RangeSlider(double minValue, double maxValue, double value1, double value2) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value1 = new SimpleDoubleProperty(value1);
		this.value1.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				rangeSliderSkin.updateCursorXPos(rangeSliderSkin.getMinCursor(), (double) newValue);
				// System.out.println("Value 1 : " + newValue);
			}
		});
		this.value2 = new SimpleDoubleProperty(value2);
		this.value2.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				rangeSliderSkin.updateCursorXPos(rangeSliderSkin.getMaxCursor(), (double) newValue);
				// System.out.println("Value 2 : " + newValue);
			}
		});
		
		valueMid = new SimpleDoubleProperty((value2 + value1) / 2);
		valueMid.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				rangeSliderSkin.updateCursorXPos(rangeSliderSkin.getMidCursor(), (double) newValue);
				// System.out.println("Value Mid : " + newValue);
			}
		});
		
		range = new SimpleDoubleProperty(value2 - value1);
		range.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Range : " + newValue);
			}
		});
		sceneProperty().addListener(new ChangeListener<Scene>() {
			@Override
			public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
				newValue.getStylesheets().add("style/default.css");
			}
		});
	}
	
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	public double getMinValue() {
		return minValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	public double getMaxValue() {
		return maxValue;
	}

	public SimpleDoubleProperty value1Property() {
		return value1;
	}

	public void setValue1(double value1) {
		this.value1.set(value1);
	}

	public double getValue1() {
		return value1.get();
	}

	public SimpleDoubleProperty value2Property() {
		return value2;
	}

	public void setValue2(double value2) {
		this.value2.set(value2);
	}

	public double getValue2() {
		return value2.get();
	}

	public SimpleDoubleProperty valueMidProperty() {
		return valueMid;
	}

	public void setValueMid(double valueMid) {
		this.valueMid.set(valueMid);
	}

	public double getValueMid() {
		return valueMid.get();
	}
	
	public SimpleDoubleProperty rangeWidthProperty() {
		return range;
	}
	
	public void setRange(double range) {
		this.range.set(range);
	}
	
	public double getRange() {
		return range.get();
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		rangeSliderSkin = new RangeSliderSkin(this);
		return rangeSliderSkin;
	}

	public RangeSliderSkin getRangeSliderSkin() {
		return rangeSliderSkin;
	}
}
