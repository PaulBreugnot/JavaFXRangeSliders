package controls.RangeSlider;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class RangeSliderSkin extends SkinBase<RangeSlider> {

	// Component layout parameters
	public static final double horizontalPadding = 5;
	public static final double verticalPadding = 0;

	// Main Component
	private RangeSlider rangeSlider;

	// Graphic Items
	// private Rectangle bar;
	private Cursor minCursor;
	private Cursor maxCursor;
	private Cursor midCursor;
	private Bar bar;

	// Cursor moves
	private double cursorDragOrigin;
	private boolean reinitializeDrag;

	protected RangeSliderSkin(RangeSlider rangeSlider) {
		super(rangeSlider);
		this.rangeSlider = rangeSlider;
		initGraphics();
	}

	public Cursor getMinCursor() {
		return minCursor;
	}

	public Cursor getMidCursor() {
		return midCursor;
	}

	public Cursor getMaxCursor() {
		return maxCursor;
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
		rangeSlider.getScene().heightProperty().addListener(new ChangeListener<Number>() {
			// If the scene changes, cursors positions are adjusted (bug otherwise...)
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateCursorXPos(minCursor, rangeSlider.getValue1());
				updateCursorXPos(maxCursor, rangeSlider.getValue2());
				updateCursorXPos(midCursor, rangeSlider.getValueMid());
			}
		});
		;
		initBar();
		initMinCursor();
		initMaxCursor();
		initMidCursor();
	}

	private void initBar() {
		bar = new Bar(rangeSlider);
		Stop[] stops = new Stop[360];
		for (int i = 0; i < 360; i++) {
			stops[i] = new Stop(i / 360., Color.hsb(i, 1, 1));
		}
		// LinearGradient color = new LinearGradient(0, 0, 1, 0, true,
		// CycleMethod.NO_CYCLE, stops);

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
				// System.out.println("new bar width : " + (double) newValue);
				updateCursorXPos(minCursor, rangeSlider.getValue1());
				updateCursorXPos(maxCursor, rangeSlider.getValue2());
				updateCursorXPos(midCursor, rangeSlider.getValueMid());
			}
		});

		getChildren().add(bar.getBar1());
		getChildren().add(bar.getBarMid());
		getChildren().add(bar.getBar2());

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
				// System.out.println("Value 1 changed : " + (double) newValue);
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
				// System.out.println("Value 2 changed : " + (double) newValue);
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
				// System.out.println("Value mid changed : " + (double) newValue);
				updateCursorXPos(midCursor, (double) newValue);
			}
		});
		
		rangeSlider.rangeWidthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Range width : " + (double) newValue);
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
				double newPos = cursor.getDragOriginLayout() + event.getSceneX() - cursorDragOrigin;
				
				if (rangeSlider.getMode() == RangeSlider.Mode.LINEAR) {
					switch (cursor.getCursorType()) {
					case LEFT:
						if (newPos + Cursor.cursorSize + 1 <= maxCursor.getLayoutX()) {
							// Normal layout.
							// + 1 to avoid mid cursor jumps dues to imprecisions
							cursor.updateValue(newPos, bar.getWidth());
						} else {
							// Force equality when we reach limit case
							rangeSlider.setValue1(rangeSlider.getValue2());
						}
						break;
					case RIGHT:
						if (newPos >= minCursor.getLayoutX() + Cursor.cursorSize) {
							// Normal layout.
							cursor.updateValue(newPos, bar.getWidth());
						} else {
							// Force equality when we reach limit case
							rangeSlider.setValue2(rangeSlider.getValue1());
						}
						break;
					default:
					}
				} else {
					// Cyclic mode, cursors can move freely
					cursor.updateValue(newPos, bar.getWidth());
				}
				if (cursor.getCursorType() == Cursor.CursorType.MIDDLE) {
					rangeSlider.setListenValueChanges(false); // Avoid events loops
					double delta = event.getSceneX() - cursorDragOrigin;
					// update min and max values to follow mid cursor
					minCursor.updateValue(minCursor.getDragOriginLayout() + delta, bar.getWidth());
					maxCursor.updateValue(maxCursor.getDragOriginLayout() + delta, bar.getWidth());
					rangeSlider.setListenValueChanges(true);
				}
				// Adjust mid value.
				// Not needed theoretically, but mid cursor layout errors if not there.
				rangeSlider.computeMidValue(rangeSlider.getValue1(), rangeSlider.getValue2());
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
		// return bar.getY() + (Bar.barHeight - Cursor.cursorSize) / 2;
		return bar.getY() + (Bar.barHeight - Cursor.cursorSize) / 2 - 1;
		// return bar.getY();
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {

		bar.setWidth(contentWidth - 2 * horizontalPadding - 2 * Cursor.cursorSize);
		layoutInArea(bar.getBar1(), bar.getBar1().getLayoutX(), verticalPadding + Bar.barHeight,
				bar.getBar1().getWidth(), Bar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
		layoutInArea(bar.getBarMid(), bar.getBarMid().getLayoutX(), verticalPadding + Bar.barHeight,
				bar.getBarMid().getWidth(), Bar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
		layoutInArea(bar.getBar2(), bar.getBar2().getLayoutX(), verticalPadding + Bar.barHeight,
				bar.getBar2().getWidth(), Bar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);

//		System.out.println("Value 1 : " + rangeSlider.getValue1());
//		System.out.println("Value 2 : " + rangeSlider.getValue2());
//		System.out.println("LayoutX : " + maxCursor.getLayoutX());
//		System.out.println("LayoutY : " + minCursor.getLayoutY());

		layoutInArea(minCursor, minCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);

		layoutInArea(maxCursor, maxCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);

		layoutInArea(midCursor, midCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);
	}

}
