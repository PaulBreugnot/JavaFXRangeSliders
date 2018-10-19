package controls.rangeSlider;

import controls.simpleSlider.Slider;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Skin;

public class RangeSlider extends Slider {

	private SimpleDoubleProperty value1;
	private SimpleDoubleProperty value2;
	private SimpleDoubleProperty range;

	public RangeSlider(double minValue, double maxValue, double value1, double value2, Mode mode) {
		this(minValue, maxValue, value1, value2);
		this.mode = mode;
	}

	public RangeSlider(double minValue, double maxValue, double value1, double value2) {
		super(minValue, maxValue, (value2 + value1) / 2);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value1 = new SimpleDoubleProperty(value1);
		this.value1.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sliderSkin.updateCursorXPos(((RangeSliderSkin) sliderSkin).getMinCursor(), (double) newValue);
				// System.out.println("Value 1 : " + newValue);
			}
		});
		this.value2 = new SimpleDoubleProperty(value2);
		this.value2.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Value 2 : " + newValue);
				sliderSkin.updateCursorXPos(((RangeSliderSkin) sliderSkin).getMaxCursor(), (double) newValue);
			}
		});
		
		range = new SimpleDoubleProperty(value2 - value1);
		range.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// System.out.println("Range : " + newValue);
			}
		});
	}

	public SimpleDoubleProperty value1Property() {
		return value1;
	}

	public void setValue1(double value1) {
		this.valueMid.set(computeMidValue(value1, getValue2()));
		this.range.set(computeRange(value1, getValue2()));
		this.value1.set(value1);
	}

	public double getValue1() {
		return value1.get();
	}

	public SimpleDoubleProperty value2Property() {
		return value2;
	}

	public void setValue2(double value2) {
		this.valueMid.set(computeMidValue(getValue1(), value2));
		this.range.set(computeRange(getValue1(), value2));
		this.value2.set(value2);
	}

	public double getValue2() {
		return value2.get();
	}

	public SimpleDoubleProperty valueMidProperty() {
		return valueProperty();
	}

	public void setValueMid(double valueMid) {
		setValue(valueMid);
	}

	public double getValueMid() {
		return getValue();
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

	private double computeMidValue(double v1, double v2) {
		if (v1 <= v2) {
			// System.out.println("Normal : " + ((v2 + v1) / 2));
			return (v2 + v1) / 2;
		} else {
			double midValue;
			double minValue = getMinValue();
			double maxValue = getMaxValue();
			midValue = (v1 - (maxValue - minValue) + v2) / 2;
			// System.out.println("Inverted : " + midValue);
			if (midValue >= minValue && midValue <= maxValue) {
				return midValue;
			} else {
				return midValue + maxValue - minValue;
			}
		}
	}
	
	private double computeRange(double v1, double v2) {
		if (v1 <= v2) {
			return v2 - v1;
		}
		return v2 - getMinValue() + getMaxValue() - v1;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		sliderSkin = new RangeSliderSkin(this);
		return sliderSkin;
	}
	
	public RangeSliderSkin getRangeSliderSkin() {
		return (RangeSliderSkin) sliderSkin;
	}
}
