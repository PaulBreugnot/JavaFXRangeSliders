package controls.RangeSlider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class RangeSliderSkin extends SkinBase<RangeSlider> {

	// Component layout parameters
	private static final double barHeight = 10;
	private static final double horizontalPadding = 5;
	private static final double verticalPadding = 5;

	// Main Component
	private RangeSlider rangeSlider;

	// Graphic Items
	private Rectangle bar;
	private Cursor minCursor;

	// Cursor moves
	private double cursorDragOrigin;
	private double cursorOriginLayout;

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
				// event.getX() = coordinate in the bar coordinate system
				updateCursor(event.getX());
			}
		});

		bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// event.getX() = coordinate in the bar coordinate system
				updateCursor(event.getX());
			}
		});

		bar.widthProperty().addListener(new ChangeListener<Number>() {
			// If the bar width changes, cursors positions are adjusted
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				minCursor.setLayoutX(cursorXPos(rangeSlider.getValue1()));
			}
		});
		getChildren().add(bar);
	}

	private void initMinCursor() {
		minCursor = new Cursor();
		getChildren().add(minCursor);
		minCursor.setLayoutX(cursorXPos(rangeSlider.getValue1()));
		minCursor.setLayoutY(cursorYPos());

		minCursor.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// when the cursor is dragged, event.getX() is given in the cursor coordinate
				// system, that we need to adjust in the same time, so we need to use absolutes
				// coordinates (a.k.a event.getSceneX()) to make diff with the original position
				// on drag.
				System.out.println("Cursor Pressed : " + event.getSceneX());
				cursorDragOrigin = event.getSceneX();
				cursorOriginLayout = minCursor.getLayoutX();
			}
		});

		minCursor.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				// Diff with the drag origin coordinate
				updateCursor(cursorOriginLayout + event.getSceneX() - cursorDragOrigin);
			}
		});
	}

	private void updateCursor(Double newPos) {
		// Compute the range slider value according to newPos, that represents the
		// cursor X coordinate in the bar coordinate system.
		double value = rangeSlider.getMinValue()
				+ newPos * (rangeSlider.getMaxValue() - rangeSlider.getMinValue()) / bar.getWidth();
		if (value >= rangeSlider.getMinValue() && value <= rangeSlider.getMaxValue()) {
			rangeSlider.setValue1(value);
		} else if (value < rangeSlider.getMinValue()) {
			rangeSlider.setValue1(rangeSlider.getMinValue());
		} else if (value > rangeSlider.getMinValue()) {
			rangeSlider.setValue1(rangeSlider.getMaxValue());
		}
		minCursor.setLayoutX(cursorXPos(rangeSlider.getValue1()));
	}

	private double cursorXPos(double value) {
		// Compute the absolute cursor coordinate according to the rangeSlider value.
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

//		System.out.println("Value : " + rangeSlider.getValue1());
//		System.out.println("LayoutX : " + minCursor.getLayoutX());
//		System.out.println("LayoutY : " + minCursor.getLayoutY());

		layoutInArea(minCursor, minCursor.getLayoutX() + horizontalPadding + Cursor.cursorSize / 2,
				cursorYPos() + verticalPadding, Cursor.cursorSize, Cursor.cursorSize, 0, HPos.CENTER, VPos.TOP);
	}

}
