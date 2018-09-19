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
				rangeSlider. getRangeSliderSkin().setReinitializeDrag(true);
			}
		} else if (value > rangeSlider.getMaxValue()) {
			switch(rangeSlider.getMode()) {
			case LINEAR:
				value = rangeSlider.getMaxValue();
				break;
			case CYCLIC:
				value = rangeSlider.getMinValue();
				rangeSlider. getRangeSliderSkin().setReinitializeDrag(true);
			}
		}

		switch (getCursorType()) {
		case LEFT:
			rangeSlider.setValue1(value);
			break;
		case RIGHT:
			rangeSlider.setValue2(value);
			break;
		case MIDDLE:
			rangeSlider.setValueMid(value);
		}
	}

	public CursorType getCursorType() {
		return cursorType;
	}

}
