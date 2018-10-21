package controls.colorSlider.colorRangeSlider;

import controls.Slider;
import controls.rangeSlider.RangeSlider;
import javafx.scene.control.Skin;

public class RangeColorSlider extends RangeSlider {

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

}
