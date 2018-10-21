package sliders.colorSlider.colorRangeSlider;

import sliders.rangeSlider.RangeBar;
import sliders.rangeSlider.RangeSlider;
import sliders.rangeSlider.RangeSliderSkin;
import javafx.scene.paint.Color;
import sliders.colorSlider.ColorBar;
import sliders.colorSlider.ColorConfig;
import sliders.colorSlider.ColorSelectionMode;
import sliders.colorSlider.ColorSlider;

public class RangeColorSliderSkin extends RangeSliderSkin {

	protected RangeColorSliderSkin(RangeSlider rangeSlider) {
		super(rangeSlider);
		((ColorBar) bar).linkColorListeners();
		setCursorsColors(
				((RangeSlider) slider).getValue1(),
				((RangeSlider) slider).getValue2(),
				((ColorSlider) rangeSlider).getColorConfig(),
				((ColorSlider) rangeSlider).getMinColorConfig(),
				((ColorSlider) rangeSlider).getMaxColorConfig()
				);
	}

	@Override
	protected void initBar() {
		bar = new RangeColorBar((RangeSlider) slider);
		bar.widthProperty().addListener(barWidthListener());

		getChildren().add(((RangeBar) bar).getBar1());
		getChildren().add(((RangeBar) bar).getBarMid());
		getChildren().add(((RangeBar) bar).getBar2());

		((ColorBar) bar).setColors(((RangeSlider) slider).getValue1(), ((RangeSlider) slider).getValue2());
	}
	
	public void setCursorsColors(double value1, double value2, ColorConfig colorConfig, ColorConfig minColorConfig, ColorConfig maxColorConfig) {
		minCursor.setFill(minColorConfig.getSelectedColor());
		midCursor.setFill(colorConfig.getSelectedColor());
		maxCursor.setFill(maxColorConfig.getSelectedColor());
	}
}
