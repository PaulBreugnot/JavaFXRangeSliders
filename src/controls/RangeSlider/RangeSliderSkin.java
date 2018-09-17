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
	public static final double barHeight = 10;
	public static final double horizontalPadding = 5;
	private static final double verticalPadding = 5;

	// Main Component
	private RangeSlider rangeSlider;

	// Graphic Items
	private Rectangle bar;
	private Cursor minCursor;
	private Cursor maxCursor;
	private Cursor midCursor;

	// Cursor moves
	private double cursorDragOrigin;
	private boolean reinitializeDrag;

	protected RangeSliderSkin(RangeSlider rangeSlider) {
		super(rangeSlider);
		this.rangeSlider = rangeSlider;
		initGraphics();
	}

	public double getCursorDragOrigin() {
		return cursorDragOrigin;
	}

	public void setCursorDragOrigin(double cursorDragOrigin) {
		this.cursorDragOrigin = cursorDragOrigin;
	}
	
	public void setReinitializeDrag(boolean reinitializeDrag) {
		this.reinitializeDrag = reinitializeDrag;
	}

	private void initGraphics() {
		initBar();
		initMinCursor();
		initMaxCursor();
		initMidCursor();
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

//		bar.setOnMousePressed(new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent event) {
//				// event.getX() = coordinate in the bar coordinate system
//				updateValue(midCursor, event.getX());
//			}
//		});
//
//		bar.setOnMouseDragged(new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent event) {
//				// event.getX() = coordinate in the bar coordinate system
//				updateValue(midCursor, event.getX());
//			}
//		});
//
		bar.widthProperty().addListener(new ChangeListener<Number>() {
			// If the bar width changes, cursors positions are adjusted
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateCursorXPos(minCursor, rangeSlider.getValue1());
				updateCursorXPos(maxCursor, rangeSlider.getValue2());
				updateCursorXPos(midCursor, rangeSlider.getValueMid());
			}
		});

		getChildren().add(bar);

	}

	private void initMinCursor() {
		minCursor = new Cursor(Cursor.CursorType.LEFT, rangeSlider);
		getChildren().add(minCursor);
		updateCursorXPos(minCursor, rangeSlider.getValue1());
		minCursor.setLayoutY(cursorYPos());
		initCursorListeners(minCursor);

		rangeSlider.value1Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Value 1 changed : " + (double) newValue);
				updateCursorXPos(minCursor, (double) newValue);
			}
		});
	}

	private void initMaxCursor() {
		maxCursor = new Cursor(Cursor.CursorType.RIGHT, rangeSlider);
		getChildren().add(maxCursor);
		updateCursorXPos(maxCursor, rangeSlider.getValue2());
		maxCursor.setLayoutY(cursorYPos());
		initCursorListeners(maxCursor);

		rangeSlider.value2Property().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Value 2 changed : " + (double) newValue);
				updateCursorXPos(maxCursor, (double) newValue);
			}
		});
	}

	private void initMidCursor() {
		midCursor = new Cursor(Cursor.CursorType.MIDDLE, rangeSlider);
		getChildren().add(midCursor);
		updateCursorXPos(midCursor, rangeSlider.getValueMid());
		midCursor.setLayoutY(cursorYPos());
		initCursorListeners(midCursor);

		rangeSlider.valueMidProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Value mid : " + (double) newValue);
				updateCursorXPos(midCursor, (double) newValue);
			}
		});
	}

	private void initCursorListeners(Cursor cursor) {
		cursor.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				initDrag(cursor, event);
			}
		});

		cursor.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				if (reinitializeDrag) {
					initDrag(cursor, event);
					reinitializeDrag = false;
				}
				// Diff with the drag origin coordinate
				cursor.updateValue(cursor.getDragOriginLayout() + event.getSceneX() - cursorDragOrigin, bar.getWidth());
				if (cursor.getCursorType() == Cursor.CursorType.MIDDLE) {
					rangeSlider.setListenValueChanges(false);
					double delta = event.getSceneX() - cursorDragOrigin;
					minCursor.updateValue(minCursor.getDragOriginLayout() + delta, bar.getWidth());
					maxCursor.updateValue(maxCursor.getDragOriginLayout() + delta, bar.getWidth());
					rangeSlider.setListenValueChanges(true);
				}
			}
		});
	}
	
	private void initDrag(Cursor cursor, MouseEvent event) {
		// when the cursor is dragged, event.getX() is given in the cursor coordinate
		// system, that we need to adjust in the same time, so we need to use absolutes
		// coordinates (a.k.a event.getSceneX()) to make diff with the original position
		// on drag.
		cursorDragOrigin = event.getSceneX();
		cursor.setDragOriginLayout(cursor.getLayoutX());
		if (cursor.getCursorType() == Cursor.CursorType.MIDDLE) {
			minCursor.setDragOriginLayout(minCursor.getLayoutX());
			maxCursor.setDragOriginLayout(maxCursor.getLayoutX());
		}
	}

	private void updateCursorXPos(Cursor cursor, double value) {
		// Compute the absolute cursor coordinate according to the rangeSlider value.
		double x = bar.getX() + (value - rangeSlider.getMinValue()) * bar.getWidth()
				/ (rangeSlider.getMaxValue() - rangeSlider.getMinValue());
		x += cursor.getOffset();
		// cursor.setLayoutX(x + horizontalPadding + Cursor.cursorSize / 2);
		cursor.setLayoutX(x + horizontalPadding);
	}

	private double cursorYPos() {
		return bar.getY() + (barHeight - Cursor.cursorSize) / 2;
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {

		bar.setWidth(contentWidth - 2 * horizontalPadding - 2 * Cursor.cursorSize);
		layoutInArea(bar, horizontalPadding + Cursor.cursorSize, verticalPadding, bar.getWidth(),
				barHeight + 2 * verticalPadding, 0, HPos.CENTER, VPos.TOP);

//		System.out.println("Value 1 : " + rangeSlider.getValue1());
//		System.out.println("Value 2 : " + rangeSlider.getValue2());
//		System.out.println("LayoutX : " + maxCursor.getLayoutX());
//		System.out.println("LayoutY : " + minCursor.getLayoutY());

		layoutInArea(minCursor, minCursor.getLayoutX(), cursorYPos() + verticalPadding, Cursor.cursorSize,
				Cursor.cursorSize, 0, HPos.CENTER, VPos.TOP);

		layoutInArea(maxCursor, maxCursor.getLayoutX(), cursorYPos() + verticalPadding, Cursor.cursorSize,
				Cursor.cursorSize, 0, HPos.CENTER, VPos.TOP);

		layoutInArea(midCursor, midCursor.getLayoutX(), cursorYPos() + verticalPadding, Cursor.cursorSize,
				Cursor.cursorSize, 0, HPos.CENTER, VPos.TOP);
	}

}
