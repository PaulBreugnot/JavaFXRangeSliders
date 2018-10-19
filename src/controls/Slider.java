package controls;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

public abstract class Slider extends Control {
	protected SliderSkin sliderSkin;

	public static enum Mode {
		LINEAR, CYCLIC
	}

	protected Mode mode = Mode.LINEAR;

	protected double minValue;
	protected double maxValue;
	protected SimpleDoubleProperty valueMid;

	public Slider(double minValue, double maxValue, double value, Mode mode) {
		this(minValue, maxValue, value);
		this.mode = mode;
	}

	public Slider(double minValue, double maxValue, double value) {
		super();
		this.minValue = minValue;
		this.maxValue = maxValue;
		valueMid = new SimpleDoubleProperty(value);
		
		valueMid.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sliderSkin.updateCursorXPos(sliderSkin.getMidCursor(), (double) newValue);
				System.out.println("Value Mid : " + newValue);
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

	public SimpleDoubleProperty valueProperty() {
		return valueMid;
	}

	public void setValue(double value) {
		valueMid.set(value);
	}

	public double getValue() {
		return valueMid.get();
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public Mode getMode() {
		return mode;
	}
	

	@Override
	protected abstract Skin<?> createDefaultSkin();

	public SliderSkin getSliderSkin() {
		return sliderSkin;
	}
}
