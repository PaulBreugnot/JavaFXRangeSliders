package sliders.simpleSlider;

import javafx.scene.control.Skin;
import sliders.Slider;

public class SimpleSlider extends Slider {
	
	public SimpleSlider(double minValue, double maxValue, double value) {
		super(minValue, maxValue, value);
		getStylesheets().add("style/default-slider.css");
	}
	
	public SimpleSlider(double minValue, double maxValue, double value, Mode mode) {
		this(minValue, maxValue, value);
		this.mode = mode;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		sliderSkin = new SimpleSliderSkin(this);
		return sliderSkin;
	}
}
