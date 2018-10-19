package controls.simpleSlider;

import controls.Slider;
import javafx.scene.control.Skin;

public class SimpleSlider extends Slider {

	public SimpleSlider(double minValue, double maxValue, double value) {
		super(minValue, maxValue, value);
	}
	
	public SimpleSlider(double minValue, double maxValue, double value, Mode mode) {
		this(minValue, maxValue, value);
		this.mode = mode;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		sliderSkin = new SliderSkin(this);
		return sliderSkin;
	}
}
