package sliders.colorSlider.simpleColorSlider;

import sliders.simpleSlider.SimpleBar;
import sliders.simpleSlider.SimpleSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import sliders.Slider;
import sliders.colorSlider.ColorBar;
import sliders.colorSlider.ColorConfig;
import sliders.colorSlider.ColorSelectionMode;
import sliders.colorSlider.ColorSlider;

public class SimpleColorBar extends SimpleBar implements ColorBar {
	
	public SimpleColorBar(Slider slider) {
		super(slider);
	}

	@Override
	public void setColors(double value1, double value2) {
		// bar1.setFill(Color.RED);
		bar1.setFill(ColorBar.subLinearGradient(value1, value2, (ColorSlider) slider));
	}

	@Override
	public void linkColorListeners() {
		((SimpleSlider) slider).valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				((SimpleSlider) slider).getSliderSkin().getMidCursor().setFill(((ColorSlider) slider).getColorConfig().getSelectedColor());
//						resolveCursorColor(
//								(double) newValue,
//								((ColorSlider) slider).getColorSelectionMode(),
//								((ColorSlider) slider).getColorConfig()
//								));
			}
		});
		
	}
	
//	public static Color resolveCursorColor(double value, ColorSelectionMode colorSelectionMode, ColorConfig colorConfig) {
//		switch (colorSelectionMode) {
//		case HUE:
//			return Color.hsb(value, colorConfig.getSaturation(), colorConfig.getbrightness());
//		case SATURATION:
//			return Color.hsb(colorConfig.getHue(), value, colorConfig.getbrightness());
//		case BRIGHTNESS:
//			return Color.hsb(colorConfig.getHue(), colorConfig.getSaturation(), value);
//		default:
//			return null;
//		}
//	}

}
