package controls.colorSlider.simpleColorSlider;

import controls.colorSlider.ColorBar;
import controls.simpleSlider.SimpleSlider;
import controls.simpleSlider.SimpleSliderSkin;
import javafx.scene.paint.Color;

public class SimpleColorSliderSkin extends SimpleSliderSkin {

	protected SimpleColorSliderSkin(SimpleSlider slider) {
		super(slider);
		((ColorBar) bar).linkColorListeners();
		midCursor.setFill(Color.hsb(slider.getValue(), 1, 1));
	}
	
	@Override
	protected void initBar() {
		bar = new SimpleColorBar(slider);

		bar.widthProperty().addListener(barWidthListener());
		// midCursor.setFill(Color.hsb(slider.getValue(), 1, 1));

		getChildren().add(bar.getBar1());
		((SimpleColorBar) bar).setColors(0, 360);
	}
}
