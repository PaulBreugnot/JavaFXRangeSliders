package controls.colorSlider.colorRangeSlider;

import controls.colorSlider.ColorBar;
import controls.colorSlider.ColorConfig;
import controls.colorSlider.ColorSelectionMode;
import controls.colorSlider.ColorSlider;
import controls.rangeSlider.RangeBar;
import controls.rangeSlider.RangeSlider;
import controls.rangeSlider.RangeSliderSkin;
import javafx.scene.paint.Color;

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
