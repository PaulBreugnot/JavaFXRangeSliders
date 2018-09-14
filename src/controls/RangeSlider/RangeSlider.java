package controls.RangeSlider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public class RangeSlider extends Control {
	private double minValue;
	private double maxValue;
	private SimpleDoubleProperty value1;
	
	public RangeSlider(double minValue, double maxValue, double value1, double value2) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.value1 = new SimpleDoubleProperty(value1);
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

	@Override
	protected Skin<?> createDefaultSkin() {
		return new RangeSliderSkin(this);
	}
}
