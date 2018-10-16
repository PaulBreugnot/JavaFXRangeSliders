package controls.rangeSlider.colorRangeSlider;

import controls.rangeSlider.RangeSlider;
import javafx.scene.control.Skin;

public class ColorRangeSlider extends RangeSlider {

	public ColorRangeSlider(double value1, double value2) {
		super(0, 360, value1, value2, RangeSlider.Mode.CYCLIC);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected Skin<?> createDefaultSkin() {
		rangeSliderSkin = new ColorRangeSliderSkin(this);
		return rangeSliderSkin;
	}
}
