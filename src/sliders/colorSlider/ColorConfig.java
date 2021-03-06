package sliders.colorSlider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

public class ColorConfig {

	protected SimpleDoubleProperty hueProperty;
	protected SimpleDoubleProperty saturationProperty;
	protected SimpleDoubleProperty brightnessProperty;
	protected SimpleDoubleProperty redProperty;
	protected SimpleDoubleProperty greenProperty;
	protected SimpleDoubleProperty blueProperty;

	protected Color selectedColor;

	public ColorConfig() {
		hueProperty = new SimpleDoubleProperty();
		hueProperty.addListener((observable, oldValue,
				newValue) -> selectedColor = Color.hsb((double) newValue, getSaturation(), getBrightness()));
		saturationProperty = new SimpleDoubleProperty();
		saturationProperty.addListener((observable, oldValue,
				newValue) -> selectedColor = Color.hsb(getHue(), (double) newValue, getBrightness()));
		brightnessProperty = new SimpleDoubleProperty();
		brightnessProperty.addListener((observable, oldValue,
				newValue) -> selectedColor = Color.hsb(getHue(), getSaturation(), (double) newValue));
		redProperty = new SimpleDoubleProperty();
//		redProperty.addListener((observable, oldValue,
//				newValue) -> selectedColor = Color.hsb((double) newValue, getGreen(), (double) newValue));
		greenProperty = new SimpleDoubleProperty();
		blueProperty = new SimpleDoubleProperty();
	}
	
	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setHue(double hue) {
		hueProperty.set(hue);
	}

	public double getHue() {
		return hueProperty.get();
	}

	public SimpleDoubleProperty hueProperty() {
		return hueProperty;
	}

	public void setSaturation(double saturation) {
		saturationProperty.set(saturation);
	}

	public double getSaturation() {
		return saturationProperty.get();
	}

	public SimpleDoubleProperty saturationProperty() {
		return saturationProperty;
	}

	public void setBrightness(double brightness) {
		brightnessProperty.set(brightness);
	}

	public double getBrightness() {
		return brightnessProperty.get();
	}

	public SimpleDoubleProperty brightnessProperty() {
		return brightnessProperty;
	}
}
