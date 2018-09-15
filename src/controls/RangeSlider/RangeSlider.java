package controls.RangeSlider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class RangeSlider extends Control {

	public static enum Mode {
		LINEAR, CYCLIC
	}

	private double minValue;
	private double maxValue;
	private SimpleDoubleProperty value1;
	private SimpleDoubleProperty valueMid;
	private SimpleDoubleProperty value2;

	private boolean listenValueChanges = true;

	public RangeSlider(double minValue, double maxValue, double value1, double value2) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value1 = new SimpleDoubleProperty(value1);
		this.value1.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (listenValueChanges) {
					valueMid.set((getValue2() + (double) newValue) / 2);
				}
			}
		});
		this.value2 = new SimpleDoubleProperty(value2);
		this.value2.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (listenValueChanges) {
					valueMid.set(((double) newValue + getValue1()) / 2);
				}
			}
		});
		valueMid = new SimpleDoubleProperty((value2 + value1) / 2);
//		this.valueMid.addListener(new ChangeListener<Number>() {
//			@Override
//			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//				setListenValueChanges(false);
//				double delta = (double) newValue - (double) oldValue;
//				setValue1(Math.max(getMinValue(), getValue1() + delta));
//				setValue2(Math.min(getMaxValue(), getValue2() + delta));
//				setListenValueChanges(true);
//			}
//		});

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

	public void setListenValueChanges(boolean listenValueChanges) {
		this.listenValueChanges = listenValueChanges;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new RangeSliderSkin(this);
	}
}
