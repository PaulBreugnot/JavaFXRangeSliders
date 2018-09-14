package controls.RangeSlider;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class RangeSliderSkin extends SkinBase<RangeSlider> {
	private static final double barHeight = 10;

	private RangeSlider rangeSlider;
	private static final double horizontalPadding = 5;
	private static final double verticalPadding = 5;
	private Rectangle bar;
	private Rectangle minCursor;
	private boolean drag = false;

	protected RangeSliderSkin(RangeSlider rangeSlider) {
		super(rangeSlider);
		this.rangeSlider = rangeSlider;
		initGraphics();
	}

	private void initGraphics() {
		initBar();
		initMinCursor();
	}

	private void initBar() {
		bar = new Rectangle();
		bar.setHeight(barHeight);
		Stop[] stops = new Stop[360];
		for (int i = 0; i < 360; i++) {
			stops[i] = new Stop(i / 360., Color.hsb(i, 1, 1));
		}
		LinearGradient color = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
		bar.setFill(color);
		bar.setStroke(Color.BLACK);
		bar.setStrokeWidth(1);

		bar.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateCursor(event);
			}
		});

		bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateCursor(event);
			}
		});

		bar.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				minCursor.setX(cursorXPos(rangeSlider.getValue1()));
			}
		});
		getChildren().add(bar);
	}

	private void initMinCursor() {
		minCursor = new Cursor();
		getChildren().add(minCursor);
		minCursor.setX(cursorXPos(rangeSlider.getValue1()));
		minCursor.setY(cursorYPos());
		
		minCursor.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				updateCursor(event);
			}
		});
	}

	private void updateCursor(MouseEvent event) {
		double value = rangeSlider.getMinValue()
				+ event.getX() * (rangeSlider.getMaxValue() - rangeSlider.getMinValue()) / bar.getWidth();
		if (value >= rangeSlider.getMinValue() && value <= rangeSlider.getMaxValue()) {
			rangeSlider.setValue1(value);
			minCursor.setX(cursorXPos(rangeSlider.getValue1()));
		}
	}

	private double cursorXPos(double value) {
		System.out.println(bar.getWidth());
		return bar.getX() + (value - rangeSlider.getMinValue()) * bar.getWidth()
				/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue()) - Cursor.cursorSize / 2;
	}

	private double cursorYPos() {
		return bar.getY() + (barHeight - Cursor.cursorSize) / 2;
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {

		bar.setWidth(contentWidth - 2 * horizontalPadding - Cursor.cursorSize);
		layoutInArea(bar, horizontalPadding + Cursor.cursorSize / 2, verticalPadding, bar.getWidth(),
				barHeight + 2 * verticalPadding, 0, HPos.CENTER, VPos.TOP);

		System.out.println(rangeSlider.getValue1());

		layoutInArea(minCursor, minCursor.getX() + horizontalPadding + Cursor.cursorSize / 2,
				minCursor.getY() + verticalPadding, Cursor.cursorSize, Cursor.cursorSize, 0, HPos.CENTER, VPos.TOP);
	}

}
