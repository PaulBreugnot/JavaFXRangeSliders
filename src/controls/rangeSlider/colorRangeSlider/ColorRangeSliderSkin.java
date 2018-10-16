package controls.rangeSlider.colorRangeSlider;

import controls.rangeSlider.RangeSliderSkin;

public class ColorRangeSliderSkin extends RangeSliderSkin {

	protected ColorRangeSliderSkin(ColorRangeSlider colorRangeSlider) {
		super(colorRangeSlider);
		bar.linkColorListeners();
	}
}
