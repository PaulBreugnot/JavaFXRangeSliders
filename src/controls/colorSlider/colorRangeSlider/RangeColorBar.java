package controls.colorSlider.colorRangeSlider;

import controls.colorSlider.ColorBar;
import controls.rangeSlider.RangeBar;
import controls.rangeSlider.RangeSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;

public class RangeColorBar extends RangeBar implements ColorBar {

	public RangeColorBar(RangeSlider rangeSlider) {
		super(rangeSlider);
	}

	@Override
	public void setColors(double value1, double value2) {
		internSelected.set(value1 <= value2);
		if (internSelected.get()) {
			bar1.setFill(subLinearGradient(0, value1));

			barMid.setFill(subLinearGradient(value1, value2));

			bar2.setFill(subLinearGradient(value2, 360));

		} else {
			bar1.setFill(subLinearGradient(0, value2));
			((RangeSlider) slider).getRangeSliderSkin().getMinCursor().setFill(Color.hsb(value1, 1, 1));

			barMid.setFill(subLinearGradient(value2, value1));
			((RangeSlider) slider).getRangeSliderSkin().getMidCursor()
					.setFill(Color.hsb(((RangeSlider) slider).getValueMid(), 1, 1));

			bar2.setFill(subLinearGradient(value1, 360));
			((RangeSlider) slider).getRangeSliderSkin().getMaxCursor().setFill(Color.hsb(value2, 1, 1));

		}
	}

	@Override
	public void linkColorListeners() {
		((RangeSlider) slider).value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors((double) newValue, ((RangeSlider) slider).getValue2());
				((RangeColorSliderSkin) ((RangeSlider) slider).getRangeSliderSkin()).setCursorsColors((double) newValue, ((RangeSlider) slider).getValue2());
			}
		});

		((RangeSlider) slider).value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors(((RangeSlider) slider).getValue1(), (double) newValue);
				((RangeColorSliderSkin) ((RangeSlider) slider).getRangeSliderSkin()).setCursorsColors(((RangeSlider) slider).getValue1(), (double) newValue);
			}
		});
	}

}
