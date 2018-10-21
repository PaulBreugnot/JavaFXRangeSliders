package sliders.colorSlider.colorRangeSlider;

import sliders.rangeSlider.RangeBar;
import sliders.rangeSlider.RangeSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import sliders.colorSlider.ColorBar;
import sliders.colorSlider.ColorSlider;

public class RangeColorBar extends RangeBar implements ColorBar {

	public RangeColorBar(RangeSlider rangeSlider) {
		super(rangeSlider);
	}

	@Override
	public void setColors(double value1, double value2) {
		double maxColor;
		switch (((ColorSlider) slider).getColorSelectionMode()) {
		case HUE:
			maxColor = 360;
			break;
		case SATURATION:
			maxColor = 1;
			break;
		case BRIGHTNESS:
			maxColor = 1;
			break;
		default :
			maxColor = 255;
		}
		internSelected.set(value1 <= value2);
		if (internSelected.get()) {
			bar1.setFill(ColorBar.subLinearGradient(0, value1, (ColorSlider) slider));

			barMid.setFill(ColorBar.subLinearGradient(value1, value2, (ColorSlider) slider));

			bar2.setFill(ColorBar.subLinearGradient(value2, maxColor, (ColorSlider) slider));

		} else {
			bar1.setFill(ColorBar.subLinearGradient(0, value2, (ColorSlider) slider));
			// ((RangeSlider) slider).getRangeSliderSkin().getMinCursor().setFill(Color.hsb(value1, 1, 1));

			barMid.setFill(ColorBar.subLinearGradient(value2, value1, (ColorSlider) slider));
//			((RangeSlider) slider).getRangeSliderSkin().getMidCursor()
//					.setFill(Color.hsb(((RangeSlider) slider).getValueMid(), 1, 1));

			bar2.setFill(ColorBar.subLinearGradient(value1, maxColor, (ColorSlider) slider));
			// ((RangeSlider) slider).getRangeSliderSkin().getMaxCursor().setFill(Color.hsb(value2, 1, 1));

		}
	}

	@Override
	public void linkColorListeners() {
		((RangeSlider) slider).value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors((double) newValue, ((RangeSlider) slider).getValue2());
				((RangeColorSliderSkin) ((RangeSlider) slider).getRangeSliderSkin())
				.setCursorsColors(
						(double) newValue,
						((RangeSlider) slider).getValue2(),
						((ColorSlider) slider).getColorConfig(),
						((ColorSlider) slider).getMinColorConfig(),
						((ColorSlider) slider).getMaxColorConfig());
			}
		});

		((RangeSlider) slider).value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				setColors(((RangeSlider) slider).getValue1(), (double) newValue);
				((RangeColorSliderSkin) ((RangeSlider) slider).getRangeSliderSkin()).setCursorsColors(
						((RangeSlider) slider).getValue1(),
						(double) newValue,
						((ColorSlider) slider).getColorConfig(),
						((ColorSlider) slider).getMinColorConfig(),
						((ColorSlider) slider).getMaxColorConfig());
			}
		});
	}

}
