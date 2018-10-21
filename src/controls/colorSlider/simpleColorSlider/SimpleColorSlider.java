package controls.colorSlider.simpleColorSlider;

import controls.Slider;
import controls.simpleSlider.SimpleSlider;
import javafx.scene.control.Skin;

public class SimpleColorSlider extends SimpleSlider {

	public SimpleColorSlider(double value) {
		super(0, 360, value, Slider.Mode.CYCLIC);
		getStylesheets().remove("style/default-slider.css");
		getStylesheets().add("style/color-slider-style.css");
	}
	
	@Override
	protected Skin<?> createDefaultSkin() {
		sliderSkin = new SimpleColorSliderSkin(this);
		return sliderSkin;
	}

}
