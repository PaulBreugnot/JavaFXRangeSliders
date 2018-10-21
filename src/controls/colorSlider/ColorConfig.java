package controls.colorSlider;

import javafx.beans.property.SimpleDoubleProperty;

public class ColorConfig {

	protected SimpleDoubleProperty hueProperty;
	protected SimpleDoubleProperty saturationProperty;
	protected SimpleDoubleProperty brightnessProperty;
	protected SimpleDoubleProperty redProperty;
	protected SimpleDoubleProperty greenProperty;
	protected SimpleDoubleProperty blueProperty;
	
	public ColorConfig() {
		hueProperty = new SimpleDoubleProperty();
		saturationProperty = new SimpleDoubleProperty();
		brightnessProperty = new SimpleDoubleProperty();
		redProperty = new SimpleDoubleProperty();
		greenProperty = new SimpleDoubleProperty();
		blueProperty = new SimpleDoubleProperty();
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
	
	public double getbrightness() {
		return brightnessProperty.get();
	}
	
	public SimpleDoubleProperty brightnessProperty() {
		return brightnessProperty;
	}
}
