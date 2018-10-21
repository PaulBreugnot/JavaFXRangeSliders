package controls.colorSlider.simpleColorSlider;

import controls.colorSlider.ColorBar;
import controls.colorSlider.ColorSelectionMode;
import controls.colorSlider.ColorSlider;
import controls.simpleSlider.SimpleSlider;
import controls.simpleSlider.SimpleSliderSkin;
import javafx.scene.paint.Color;

public class SimpleColorSliderSkin extends SimpleSliderSkin {
	
	protected SimpleColorSliderSkin(SimpleSlider slider) {
		super(slider);
		((ColorBar) bar).linkColorListeners();
		
//		midCursor.setFill(Color.hsb(slider.getValue(), 1, 1));
//		midCursor.setFill(SimpleColorBar.resolveCursorColor(
//				slider.getValue(),
//				((ColorSlider) slider).getColorSelectionMode(),
//				((ColorSlider) slider).getColorConfig()));
		midCursor.setFill(((ColorSlider) slider).getColorConfig().getSelectedColor());
	}
	
	@Override
	protected void initBar() {
		bar = new SimpleColorBar(slider);

		bar.widthProperty().addListener(barWidthListener());
		// midCursor.setFill(Color.hsb(slider.getValue(), 1, 1));

		getChildren().add(bar.getBar1());
		switch(((ColorSlider) slider).getColorSelectionMode()) {
		case HUE:
			((SimpleColorBar) bar).setColors(0, 360);
			break;
		case SATURATION :
			((SimpleColorBar) bar).setColors(0, 1);
			break;
		case BRIGHTNESS:
			((SimpleColorBar) bar).setColors(0, 1);
			break;
		default:
			// rgb mode
			((SimpleColorBar) bar).setColors(0, 1);
		}
		
	}
}
