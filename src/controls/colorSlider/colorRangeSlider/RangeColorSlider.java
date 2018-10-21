package controls.colorSlider.colorRangeSlider;

import controls.Slider;
import controls.colorSlider.ColorConfig;
import controls.colorSlider.ColorSelectionMode;
import controls.colorSlider.ColorSlider;
import controls.colorSlider.simpleColorSlider.SimpleColorSlider;
import controls.rangeSlider.RangeSlider;
import javafx.scene.control.Skin;

public class RangeColorSlider extends RangeSlider implements ColorSlider {
	
	protected ColorSelectionMode colorSelectionMode;
	
	protected ColorConfig colorConfig;
	protected ColorConfig minColorConfig;
	protected ColorConfig maxColorConfig;
	
	public RangeColorSlider(double minValue, double maxValue, double value1, double value2, Slider.Mode mode, ColorSelectionMode colorSelectionMode) {
		super(minValue, maxValue, value1, value2, mode);
		this.colorSelectionMode = colorSelectionMode;
		getStylesheets().remove("style/default-slider.css");
		getStylesheets().add("style/color-slider-style.css");
		colorConfig = new ColorConfig();
		minColorConfig = new ColorConfig();
		maxColorConfig = new ColorConfig();
	}
	
	public static RangeColorSlider RangeHueSlider(double value1, double value2, double saturation, double brightness) {
		RangeColorSlider rangeColorSlider = new RangeColorSlider(0, 360, value1, value2, Slider.Mode.CYCLIC, ColorSelectionMode.HUE);
		// mid cursor
		ColorConfig colorConfig = rangeColorSlider.getColorConfig();
		colorConfig.setSaturation(saturation);
		colorConfig.setBrightness(brightness);
		colorConfig.hueProperty().bind(rangeColorSlider.valueMidProperty());
		
		// min cursor
		ColorConfig minColorConfig = rangeColorSlider.getMinColorConfig();
		minColorConfig.setSaturation(saturation);
		minColorConfig.setBrightness(brightness);
		minColorConfig.hueProperty().bind(rangeColorSlider.value1Property());
		
		// max cursor
		ColorConfig maxColorConfig = rangeColorSlider.getMaxColorConfig();
		maxColorConfig.setSaturation(saturation);
		maxColorConfig.setBrightness(brightness);
		maxColorConfig.hueProperty().bind(rangeColorSlider.value2Property());
		
		return rangeColorSlider;
	}
	
	public static RangeColorSlider RangeSaturationSlider(double value1, double value2, double hue, double brightness) {
		RangeColorSlider rangeColorSlider = new RangeColorSlider(0, 1, value1, value2, Slider.Mode.LINEAR, ColorSelectionMode.SATURATION);
		// mid cursor
		ColorConfig colorConfig = rangeColorSlider.getColorConfig();
		colorConfig.setHue(hue);
		colorConfig.setBrightness(brightness);
		colorConfig.saturationProperty().bind(rangeColorSlider.valueMidProperty());
		
		// min cursor
		ColorConfig minColorConfig = rangeColorSlider.getMinColorConfig();
		minColorConfig.setHue(hue);
		minColorConfig.setBrightness(brightness);
		minColorConfig.saturationProperty().bind(rangeColorSlider.value1Property());
		
		// max cursor
		ColorConfig maxColorConfig = rangeColorSlider.getMaxColorConfig();
		maxColorConfig.setHue(hue);
		maxColorConfig.setBrightness(brightness);
		maxColorConfig.saturationProperty().bind(rangeColorSlider.value2Property());
		
		return rangeColorSlider;
	}
	
	public static RangeColorSlider RangeBrightnessSlider(double value1, double value2, double hue, double saturation) {
		RangeColorSlider rangeColorSlider = new RangeColorSlider(0, 1, value1, value2, Slider.Mode.LINEAR, ColorSelectionMode.BRIGHTNESS);
		// mid cursor
		ColorConfig colorConfig = rangeColorSlider.getColorConfig();
		colorConfig.setHue(hue);
		colorConfig.setSaturation(saturation);
		colorConfig.brightnessProperty().bind(rangeColorSlider.valueMidProperty());
		
		// min cursor
		ColorConfig minColorConfig = rangeColorSlider.getMinColorConfig();
		minColorConfig.setHue(hue);
		minColorConfig.setSaturation(saturation);
		minColorConfig.brightnessProperty().bind(rangeColorSlider.value1Property());
		
		// max cursor
		ColorConfig maxColorConfig = rangeColorSlider.getMaxColorConfig();
		maxColorConfig.setHue(hue);
		maxColorConfig.setSaturation(saturation);
		maxColorConfig.brightnessProperty().bind(rangeColorSlider.value2Property());
		
		return rangeColorSlider;
	}
	
	@Override
	protected Skin<?> createDefaultSkin() {
		sliderSkin = new RangeColorSliderSkin(this);
		return sliderSkin;
	}

	@Override
	public ColorConfig getColorConfig() {
		return colorConfig;
	}
	
	@Override
	public ColorConfig getMinColorConfig() {
		return minColorConfig;
	}
	
	@Override
	public ColorConfig getMaxColorConfig() {
		return maxColorConfig;
	}

	@Override
	public ColorSelectionMode getColorSelectionMode() {
		return colorSelectionMode;
	}

}
