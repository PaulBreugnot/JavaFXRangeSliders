package controls.rangeSlider;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cursor extends Polygon {

	public static enum CursorType {
		LEFT, RIGHT, MIDDLE
	}

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
		switch (cursorType) {
		case LEFT:
			points = new Double[] { 0.0, 0.0, 0.0, cursorSize, cursorSize / 2, cursorSize, cursorSize, cursorSize / 2,
					cursorSize / 2, 0.0 };
			break;
		case RIGHT:
			points = new Double[] { 0.0, cursorSize / 2, cursorSize / 2, cursorSize, cursorSize, cursorSize, cursorSize,
					0.0, cursorSize / 2, 0.0 };
			break;
		case MIDDLE:
			points = new Double[] { 0.0, 0.0, cursorSize / 2, cursorSize / 2, cursorSize, 0.0, 0.0, 0.0, cursorSize / 2,
					cursorSize / 2, 0.0, cursorSize, cursorSize, cursorSize, cursorSize / 2, cursorSize / 2 };
			break;
		default:
			points = new Double[] {};
		}
		getPoints().addAll(points);
		setId("cursor");
		setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 2) {
					switch (cursorType) {
					case LEFT:
						rangeSlider.setValue2(rangeSlider.getValue1());
						break;
					case RIGHT:
						rangeSlider.setValue1(rangeSlider.getValue2());
						break;
					case MIDDLE:
						double valueMid = rangeSlider.getValueMid();
						rangeSlider.setValue1(valueMid);
						rangeSlider.setValue2(valueMid);
						break;
					default:
					}
				}
			}
		});
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
			switch (rangeSlider.getMode()) {
			case LINEAR:
				value = rangeSlider.getMinValue();
				break;
			case CYCLIC:
				value = rangeSlider.getMaxValue();
				rangeSlider.getRangeSliderSkin().setReinitializeDrag(true);
			}
		} else if (value > rangeSlider.getMaxValue()) {
			switch (rangeSlider.getMode()) {
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
			// rangeSlider.setValueMid(computeMidValue(value, rangeSlider.getValue2(), rangeSlider));
			//rangeSlider.setRange(computeRange(value, rangeSlider.getValue2()));
			break;
		case RIGHT:
			rangeSlider.setValue2(value);
			// rangeSlider.setValueMid(computeMidValue(rangeSlider.getValue1(), value, rangeSlider));
			// rangeSlider.setRange(computeRange(rangeSlider.getValue1(), value));
			break;
		case MIDDLE:
			double newValue1 = value - rangeSlider.getRange() / 2;
			double newValue2 = value + rangeSlider.getRange() / 2;
			if (newValue1 < rangeSlider.getMinValue()) {
				switch (rangeSlider.getMode()) {
				case LINEAR:
					newValue1 = rangeSlider.getMinValue();
					newValue2 = newValue1 + rangeSlider.getRange();
					value = (newValue1 + newValue2) / 2;
					break;
				case CYCLIC:
					newValue1 = rangeSlider.getMaxValue() + newValue1 - rangeSlider.getMinValue();
				}
			} else if (newValue2 > rangeSlider.getMaxValue()) {
				switch (rangeSlider.getMode()) {
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
			rangeSlider.setValue2(newValue2);
			rangeSlider.setValueMid(value);
		}
	}

	public CursorType getCursorType() {
		return cursorType;
	}

}
