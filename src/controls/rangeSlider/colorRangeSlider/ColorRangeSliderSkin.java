package controls.rangeSlider.colorRangeSlider;

import controls.rangeSlider.Bar;
import controls.rangeSlider.RangeSliderSkin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ColorRangeSliderSkin extends RangeSliderSkin {

	protected ColorRangeSliderSkin(ColorRangeSlider colorRangeSlider) {
		super(colorRangeSlider);
		bar.linkColorListeners();
	}
	
	@Override
	protected ChangeListener<Number> barWidthListener() {
		// To add bar colors initialization
		return new ChangeListener<Number>() {
			// If the bar width changes, cursors positions are adjusted
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// System.out.println("new bar width : " + (double) newValue);
				updateCursorXPos(minCursor, rangeSlider.getValue1());
				updateCursorXPos(maxCursor, rangeSlider.getValue2());
				updateCursorXPos(midCursor, rangeSlider.getValueMid());
				bar.setColors(rangeSlider.getValue1(), rangeSlider.getValue2());
			}
		};
	}
}
