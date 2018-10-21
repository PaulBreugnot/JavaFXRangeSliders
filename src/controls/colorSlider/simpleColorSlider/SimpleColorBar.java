package controls.colorSlider.simpleColorSlider;

import controls.Slider;
import controls.colorSlider.ColorBar;
import controls.simpleSlider.SimpleBar;
import controls.simpleSlider.SimpleSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class SimpleColorBar extends SimpleBar implements ColorBar {

	public SimpleColorBar(Slider slider) {
		super(slider);
	}

	@Override
	public void setColors(double value1, double value2) {
		// bar1.setFill(Color.RED);
		bar1.setFill(subLinearGradient(value1, value2));
	}

	@Override
	public void linkColorListeners() {
		((SimpleSlider) slider).valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				((SimpleSlider) slider).getSliderSkin().getMidCursor().setFill(Color.hsb((double) newValue, 1, 1));
			}
		});
		
	}

}
