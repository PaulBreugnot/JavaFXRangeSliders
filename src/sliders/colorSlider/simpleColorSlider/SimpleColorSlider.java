package sliders.colorSlider.simpleColorSlider;

import sliders.simpleSlider.SimpleSlider;
import javafx.scene.control.Skin;
import sliders.Slider;
import sliders.colorSlider.ColorConfig;
import sliders.colorSlider.ColorSelectionMode;
import sliders.colorSlider.ColorSlider;

public class SimpleColorSlider extends SimpleSlider implements ColorSlider {

	protected ColorSelectionMode colorSelectionMode;
	
	protected ColorConfig colorConfig;
	
	public SimpleColorSlider(double minValue, double maxValue, double value, Slider.Mode mode, ColorSelectionMode colorSelectionMode) {
		super(minValue, maxValue, value, mode);
		this.colorSelectionMode = colorSelectionMode;
		getStylesheets().remove("style/default-slider.css");
		getStylesheets().add("style/color-slider-style.css");
		colorConfig = new ColorConfig();
	}

	public ColorSelectionMode getColorSelectionMode() {
		return colorSelectionMode;
	}

	public void setColorSelectionMode(ColorSelectionMode colorSelectionMode) {
		this.colorSelectionMode = colorSelectionMode;
	}

	public ColorConfig getColorConfig() {
		return colorConfig;
	}

	public void setColorConfig(ColorConfig colorConfig) {
		this.colorConfig = colorConfig;
	}

	public static SimpleColorSlider SimpleHueColorSlider(double value, double saturation, double brightness) {
		SimpleColorSlider simpleColorSlider = new SimpleColorSlider(0, 360, value, Slider.Mode.CYCLIC, ColorSelectionMode.HUE);
		simpleColorSlider.getColorConfig().setSaturation(saturation);
		simpleColorSlider.getColorConfig().setBrightness(brightness);
		simpleColorSlider.getColorConfig().hueProperty().bind(simpleColorSlider.valueProperty());
		return simpleColorSlider;
	}
	
	public static SimpleColorSlider SimpleSaturationColorSlider(double value, double hue, double brightness) {
		SimpleColorSlider simpleColorSlider = new SimpleColorSlider(0, 1, value, Slider.Mode.LINEAR, ColorSelectionMode.SATURATION);
		simpleColorSlider.getColorConfig().setHue(hue);
		simpleColorSlider.getColorConfig().setBrightness(brightness);
		simpleColorSlider.getColorConfig().saturationProperty().bind(simpleColorSlider.valueProperty());
		return simpleColorSlider;
	}
	
	public static SimpleColorSlider SimpleBrightnessColorSlider(double value, double hue, double saturation) {
		SimpleColorSlider simpleColorSlider = new SimpleColorSlider(0, 1, value, Slider.Mode.LINEAR, ColorSelectionMode.BRIGHTNESS);
		simpleColorSlider.getColorConfig().setHue(hue);
		simpleColorSlider.getColorConfig().setSaturation(saturation);
		simpleColorSlider.getColorConfig().brightnessProperty().bind(simpleColorSlider.valueProperty());
		return simpleColorSlider;
	}
	
	@Override
	protected Skin<?> createDefaultSkin() {
		sliderSkin = new SimpleColorSliderSkin(this);
		return sliderSkin;
	}

}
