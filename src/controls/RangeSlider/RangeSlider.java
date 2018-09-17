package controls.RangeSlider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

	private boolean listenValueChanges = true;

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
				if (listenValueChanges) {
//					if ((double) newValue <= getValue2()) {
//						System.out.println("Normal : " + ((getValue2() + (double) newValue) / 2));
//						valueMid.set((getValue2() + (double) newValue) / 2);
//					} else {
//						System.out.println("Inverted : " + ((- maxValue + (double) newValue + getValue2()) / 2));
//						valueMid.set((- maxValue + (double) newValue + getValue2()) / 2);
//					}
					computeMidValue((double) newValue, getValue2());
				}
			}
		});
		this.value2 = new SimpleDoubleProperty(value2);
		this.value2.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (listenValueChanges) {
//					if (getValue1() <= (double) newValue) {
//						System.out.println("Normal : " + (((double) newValue + getValue1()) / 2));
//						valueMid.set(((double) newValue + getValue1()) / 2);
//					} else {
//						System.out.println("Inverted : " + ((- maxValue + getValue1() + (double) newValue) / 2));
//						valueMid.set((- maxValue + getValue1() + (double) newValue) / 2);
//					}
					computeMidValue(getValue1(), (double) newValue);
				}
			}
		});
		valueMid = new SimpleDoubleProperty((value2 + value1) / 2);

	}

	public void computeMidValue(double v1, double v2) {
		if (v1 <= v2) {
			System.out.println("Normal : " + ((v2 + v1) / 2));
			valueMid.set((v2 + v1) / 2);
		} else {
			double midValue = (v1 - (maxValue - minValue) + v2) / 2;
			if (midValue >= minValue && midValue <= maxValue) {
				valueMid.set(midValue);
			}
			else {
				valueMid.set(midValue + maxValue - minValue);
			}
		}
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

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
	}

	public void setListenValueChanges(boolean listenValueChanges) {
		this.listenValueChanges = listenValueChanges;
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
