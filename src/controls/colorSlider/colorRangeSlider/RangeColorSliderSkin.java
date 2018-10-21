package controls.colorSlider.colorRangeSlider;

import controls.colorSlider.ColorBar;
import controls.rangeSlider.RangeBar;
import controls.rangeSlider.RangeSlider;
import controls.rangeSlider.RangeSliderSkin;
import javafx.scene.paint.Color;

public class RangeColorSliderSkin extends RangeSliderSkin {

	protected RangeColorSliderSkin(RangeSlider rangeSlider) {
		super(rangeSlider);
		((ColorBar) bar).linkColorListeners();
		setCursorsColors(((RangeSlider) slider).getValue1(), ((RangeSlider) slider).getValue2());
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
	
	public void setCursorsColors(double value1, double value2) {
		((RangeBar) bar).isInternSelected();
		if (((RangeBar) bar).isInternSelected()) {
			minCursor.setFill(Color.hsb(value1, 1, 1));
			midCursor.setFill(Color.hsb((value2 + value1) / 2, 1, 1));
			maxCursor.setFill(Color.hsb(value2, 1, 1));

		} else {
			minCursor.setFill(Color.hsb(value1, 1, 1));
			midCursor.setFill(Color.hsb(((RangeSlider) slider).getValueMid(), 1, 1));
			maxCursor.setFill(Color.hsb(value2, 1, 1));

		}
	}
}
