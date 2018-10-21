package controls.colorSlider.colorRangeSlider;

import controls.Slider;
import controls.colorSlider.ColorConfig;
import controls.colorSlider.ColorSelectionMode;
import controls.colorSlider.ColorSlider;
import controls.rangeSlider.RangeSlider;
import javafx.scene.control.Skin;

public class RangeColorSlider extends RangeSlider implements ColorSlider {
	
	protected ColorSelectionMode colorSelectionMode;
	
	protected ColorConfig colorConfig;
	
	public RangeColorSlider(double value1, double value2) {
		super(0, 360, value1, value2, Slider.Mode.CYCLIC);
		getStylesheets().remove("style/default-slider.css");
		getStylesheets().add("style/color-slider-style.css");
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
	public ColorSelectionMode getColorSelectionMode() {
		return colorSelectionMode;
	}

}
