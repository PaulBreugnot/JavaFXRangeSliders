package controls.colorSlider;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

public interface ColorBar {

	public void setColors(double minValue, double maxValue);
	
	public void linkColorListeners();
	
	public static LinearGradient subLinearGradient(double begin, double end, ColorSlider colorSlider) {
		int pointsNumber = 50;
		Stop[] stops = new Stop[pointsNumber];
		for (int i = 0; i < pointsNumber; i++) {
			double index = (double) i / (pointsNumber - 1);
			stops[i] = new Stop(index, ComputeColor(index, begin, end, colorSlider.getColorSelectionMode(), colorSlider.getColorConfig()));
		}
		return new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
	}
	
	static Color ComputeColor(double index, double begin, double end, ColorSelectionMode colorSelectionMode, ColorConfig colorConfig) {
		switch(colorSelectionMode) {
			case HUE:
				return Color.hsb(begin + index * (end - begin), colorConfig.getSaturation(), colorConfig.getbrightness());
			case SATURATION:
				return Color.hsb(colorConfig.getHue(), begin + index * (end - begin), colorConfig.getbrightness());
			case BRIGHTNESS:
				return Color.hsb(colorConfig.getHue(), colorConfig.getSaturation(), begin + index * (end - begin));
			default:
				return null;
		}
	}
}
