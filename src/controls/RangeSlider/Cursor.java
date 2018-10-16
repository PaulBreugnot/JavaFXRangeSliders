package controls.RangeSlider;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cursor extends Polygon {
	
	public static enum CursorType {LEFT, RIGHT, MIDDLE}
	
	public static final double cursorSize = 16;
	private Color cursorColor = Color.WHITE;
	private CursorType cursorType;
	
	private double dragOriginLayout;
	private RangeSlider rangeSlider;
	
	public Cursor(CursorType cursorType, RangeSlider rangeSlider) {
		super();
		this.cursorType = cursorType;
		this.rangeSlider = rangeSlider;
		Double[] points;
		switch (cursorType){
		case LEFT:
			points = new Double[] {
					0.0, 0.0,
				    0.0, cursorSize,
				    cursorSize / 2, cursorSize,
				    cursorSize, cursorSize / 2,
				    cursorSize / 2, 0.0
			};
			break;
		case RIGHT:
			points = new Double[] {
					0.0, cursorSize / 2,
					cursorSize / 2, cursorSize,
				    cursorSize, cursorSize,
				    cursorSize, 0.0,
				    cursorSize / 2, 0.0
			};
			break;
		case MIDDLE:
			points = new Double[] {
			0.0, 0.0,
		    cursorSize / 2, cursorSize / 2,
		    cursorSize , 0.0,
		    0.0, 0.0,
		    cursorSize / 2, cursorSize / 2,
		    0.0, cursorSize,
		    cursorSize, cursorSize,
		    cursorSize / 2, cursorSize / 2
	};
			break;
		default :
			points = new Double[]{};
		}
		getPoints().addAll(points);
		setId("cursor");
	}
	
	public double getOffset() {
		switch (cursorType) {
		case RIGHT:
			return cursorSize;
		case MIDDLE:
			return cursorSize / 2;
		default:
			return 0;
		}
	}

	public void setDragOriginLayout(double dragOriginLayout) {
		this.dragOriginLayout = dragOriginLayout;
	}
	
	public double getDragOriginLayout() {
		return dragOriginLayout;
	}

	public void drag(double delta, double barWidth) {
		updateValue(dragOriginLayout + delta, barWidth);
	}
	
	public void updateValue(Double newPos, Double barWidth) {
		// Compute the range slider value according to newPos, that represents the
		// cursor X coordinate in the bar coordinate system.
		double value = rangeSlider.getMinValue() + (newPos - (RangeSliderSkin.horizontalPadding + getOffset()))
				* (rangeSlider.getMaxValue() - rangeSlider.getMinValue()) / barWidth;

		if (value < rangeSlider.getMinValue()) {
			switch(rangeSlider.getMode()) {
			case LINEAR:
				value = rangeSlider.getMinValue();
				break;
			case CYCLIC:
				value = rangeSlider.getMaxValue();
				rangeSlider.getRangeSliderSkin().setReinitializeDrag(true);
			}
		} else if (value > rangeSlider.getMaxValue()) {
			switch(rangeSlider.getMode()) {
			case LINEAR:
				value = rangeSlider.getMaxValue();
				break;
			case CYCLIC:
				value = rangeSlider.getMinValue();
				rangeSlider.getRangeSliderSkin().setReinitializeDrag(true);
			}
		}

		switch (getCursorType()) {
		case LEFT:
			rangeSlider.setValue1(value);
			rangeSlider.setValueMid(computeMidValue(value, rangeSlider.getValue2()));
			rangeSlider.setRange(computeRange(value, rangeSlider.getValue2()));
			break;
		case RIGHT:
			rangeSlider.setValue2(value);
			rangeSlider.setValueMid(computeMidValue(rangeSlider.getValue1(), value));
			rangeSlider.setRange(computeRange(rangeSlider.getValue1(), value));
			break;
		case MIDDLE:
			double newValue1 = value - rangeSlider.getRange() / 2;
			double newValue2 = value + rangeSlider.getRange() / 2;
				if (newValue1 < rangeSlider.getMinValue()) {
					switch(rangeSlider.getMode()) {
					case LINEAR:
						newValue1 = rangeSlider.getMinValue();
						newValue2 = newValue1 + rangeSlider.getRange();
						value = (newValue1 + newValue2) / 2;
						break;
					case CYCLIC:
						newValue1 = rangeSlider.getMaxValue() + newValue1 - rangeSlider.getMinValue();
					}
				}
				else if (newValue2 > rangeSlider.getMaxValue()) {
					switch(rangeSlider.getMode()) {
					case LINEAR:
						newValue2 = rangeSlider.getMaxValue();
						newValue1 = newValue2 - rangeSlider.getRange();
						value = (newValue1 + newValue2) / 2;
						break;
					case CYCLIC:
						newValue2 = rangeSlider.getMinValue() + newValue2 - rangeSlider.getMaxValue();
					}
				}
				rangeSlider.setValue1(newValue1);
				rangeSlider.setValueMid(value);
				rangeSlider.setValue2(newValue2);	
		}
	}
	
	private double computeMidValue(double v1, double v2) {
		if (v1 <= v2) {
			// System.out.println("Normal : " + ((v2 + v1) / 2));
			return (v2 + v1) / 2;
		} else {
			double midValue;
			double minValue = rangeSlider.getMinValue();
			double maxValue = rangeSlider.getMaxValue();
			midValue = (v1 - (maxValue - minValue) + v2) / 2;
			// System.out.println("Inverted : " + midValue);
			if (midValue >= minValue && midValue <= maxValue) {
				return midValue;
			}
			else {
				return midValue + maxValue - minValue;
			}
		}
	}
	
	private double computeRange(double v1, double v2) {
		if (v1 <= v2) {
			return v2 - v1;
		}
		return v2 - rangeSlider.getMinValue() + rangeSlider.getMaxValue() - v1;
	}

	public CursorType getCursorType() {
		return cursorType;
	}

}
