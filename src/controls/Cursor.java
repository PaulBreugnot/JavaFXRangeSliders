package controls;

import controls.rangeSlider.RangeSlider;
import controls.simpleSlider.Slider;
import controls.simpleSlider.SliderSkin;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Cursor extends Polygon {

	public static enum CursorType {
		LEFT, RIGHT, MIDDLE, SIMPLE
	}

	public static final double cursorSize = 16;
	private double barStrokeWidth;
	private Color cursorColor = Color.WHITE;
	private CursorType cursorType;

	private double dragOriginLayout;
	private Slider slider;

	public Cursor(CursorType cursorType, Slider slider, double barStrokeWidth) {
		super();
		this.cursorType = cursorType;
		this.slider = slider;
		this.barStrokeWidth = barStrokeWidth;
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
		case SIMPLE:
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
						((RangeSlider) slider).setValue2(((RangeSlider) slider).getValue1());
						break;
					case RIGHT:
						((RangeSlider) slider).setValue1(((RangeSlider) slider).getValue2());
						break;
					case MIDDLE:
						double valueMid = ((RangeSlider) slider).getValueMid();
						((RangeSlider) slider).setValue1(valueMid);
						((RangeSlider) slider).setValue2(valueMid);
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
			return cursorSize + barStrokeWidth;
		case MIDDLE:
			return cursorSize / 2;
		case SIMPLE:
			return cursorSize / 2 - barStrokeWidth;
		default:
			return  0;
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
		double value = slider.getMinValue() + (newPos - (SliderSkin.horizontalPadding + getOffset()))
				* (slider.getMaxValue() - slider.getMinValue()) / barWidth;

		if (value < slider.getMinValue()) {
			switch (slider.getMode()) {
			case LINEAR:
				value = slider.getMinValue();
				break;
			case CYCLIC:
				value = slider.getMaxValue();
				slider.getSliderSkin().setReinitializeDrag(true);
			}
		} else if (value > slider.getMaxValue()) {
			switch (slider.getMode()) {
			case LINEAR:
				value = slider.getMaxValue();
				break;
			case CYCLIC:
				value = slider.getMinValue();
				slider.getSliderSkin().setReinitializeDrag(true);
			}
		}

		switch (getCursorType()) {
		case LEFT:
			((RangeSlider) slider).setValue1(value);
			// slider.setValueMid(computeMidValue(value, slider.getValue2(), slider));
			//slider.setRange(computeRange(value, slider.getValue2()));
			break;
		case RIGHT:
			((RangeSlider) slider).setValue2(value);
			// slider.setValueMid(computeMidValue(slider.getValue1(), value, slider));
			// slider.setRange(computeRange(slider.getValue1(), value));
			break;
		case MIDDLE:
			double newValue1 = value - ((RangeSlider) slider).getRange() / 2;
			double newValue2 = value + ((RangeSlider) slider).getRange() / 2;
			if (newValue1 < slider.getMinValue()) {
				switch (slider.getMode()) {
				case LINEAR:
					newValue1 = slider.getMinValue();
					newValue2 = newValue1 + ((RangeSlider) slider).getRange();
					value = (newValue1 + newValue2) / 2;
					break;
				case CYCLIC:
					newValue1 = slider.getMaxValue() + newValue1 - slider.getMinValue();
				}
			} else if (newValue2 > slider.getMaxValue()) {
				switch (slider.getMode()) {
				case LINEAR:
					newValue2 = slider.getMaxValue();
					newValue1 = newValue2 - ((RangeSlider) slider).getRange();
					value = (newValue1 + newValue2) / 2;
					break;
				case CYCLIC:
					newValue2 = slider.getMinValue() + newValue2 - slider.getMaxValue();
				}
			}
			((RangeSlider) slider).setValue1(newValue1);
			((RangeSlider) slider).setValue2(newValue2);
			((RangeSlider) slider).setValueMid(value);
			break;
		default:
			slider.setValue(value);
		}
	}

	public CursorType getCursorType() {
		return cursorType;
	}

}
