package sliders.rangeSlider;

import sliders.simpleSlider.SimpleBar;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import sliders.Cursor;
import sliders.SliderSkin;

public class RangeSliderSkin extends SliderSkin {

	// Graphic Items
	// private Rectangle bar;
	protected Cursor minCursor;
	protected Cursor maxCursor;

	protected RangeSliderSkin(RangeSlider rangeSlider) {
		super(rangeSlider);
	}

	public Cursor getMinCursor() {
		return minCursor;
	}

	public Cursor getMaxCursor() {
		return maxCursor;
	}

	@Override
	protected void initCursors() {
		initMinCursor();
		initMaxCursor();
		initMidCursor();
	}

	@Override
	protected void updateCursorsPositions() {
		updateCursorXPos(minCursor, ((RangeSlider) slider).getValue1());
		updateCursorXPos(maxCursor, ((RangeSlider) slider).getValue2());
		updateCursorXPos(midCursor, ((RangeSlider) slider).getValueMid());
	}

	@Override
	protected void initBar() {
		bar = new RangeBar((RangeSlider) slider);
		bar.widthProperty().addListener(barWidthListener());

		getChildren().add(((RangeBar) bar).getBar1());
		getChildren().add(((RangeBar) bar).getBarMid());
		getChildren().add(((RangeBar) bar).getBar2());

	}

	private void initMinCursor() {
		minCursor = new Cursor(Cursor.CursorType.LEFT, ((RangeSlider) slider), bar.getStrokeWidth());
		getChildren().add(minCursor);
		updateCursorXPos(minCursor, ((RangeSlider) slider).getValue1());
		minCursor.setLayoutY(cursorYPos());
		initCursorListeners(minCursor);
	}

	private void initMaxCursor() {
		maxCursor = new Cursor(Cursor.CursorType.RIGHT, ((RangeSlider) slider), bar.getStrokeWidth());
		getChildren().add(maxCursor);
		updateCursorXPos(maxCursor, ((RangeSlider) slider).getValue2());
		maxCursor.setLayoutY(cursorYPos());
		initCursorListeners(maxCursor);
	}

	private void initMidCursor() {
		midCursor = new Cursor(Cursor.CursorType.MIDDLE, ((RangeSlider) slider), bar.getStrokeWidth());
		getChildren().add(midCursor);
		updateCursorXPos(midCursor, ((RangeSlider) slider).getValueMid());
		midCursor.setLayoutY(cursorYPos());
		initCursorListeners(midCursor);
	}

	@Override
	protected void initDrag(Cursor cursor, MouseEvent event) {
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

	@Override
	protected void initCursorListeners(Cursor cursor) {
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

				if (slider.getMode() == RangeSlider.Mode.LINEAR) {
					switch (cursor.getCursorType()) {
					case LEFT:
						if (newPos + Cursor.cursorSize + 1 <= maxCursor.getLayoutX()) {
							// Normal layout.
							// + 1 to avoid mid cursor jumps dues to imprecisions
							cursor.updateValue(newPos, bar.getWidth());
						} else {
							// Force equality when we reach limit case
							((RangeSlider) slider).setValue1(((RangeSlider) slider).getValue2());
							((RangeSlider) slider).setRange(0);
						}
						break;
					case RIGHT:
						if (newPos >= minCursor.getLayoutX() + Cursor.cursorSize) {
							// Normal layout.
							cursor.updateValue(newPos, bar.getWidth());
						} else {
							// Force equality when we reach limit case
							((RangeSlider) slider).setValue2(((RangeSlider) slider).getValue1());
							((RangeSlider) slider).setRange(0);
						}
						break;
					default:
						cursor.updateValue(newPos, bar.getWidth());
					}
				} else {
					// Cyclic mode, cursors can move freely
					cursor.updateValue(newPos, bar.getWidth());
				}
			}
		});
	}

	@Override
	protected void layoutBars() {
		layoutInArea(((RangeBar) bar).getBar1(), ((RangeBar) bar).getBar1().getLayoutX(),
				verticalPadding + SimpleBar.barHeight, ((RangeBar) bar).getBar1().getWidth(),
				SimpleBar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
		layoutInArea(((RangeBar) bar).getBarMid(), ((RangeBar) bar).getBarMid().getLayoutX(),
				verticalPadding + SimpleBar.barHeight, ((RangeBar) bar).getBarMid().getWidth(),
				SimpleBar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
		layoutInArea(((RangeBar) bar).getBar2(), ((RangeBar) bar).getBar2().getLayoutX(),
				verticalPadding + SimpleBar.barHeight, ((RangeBar) bar).getBar2().getWidth(),
				SimpleBar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
	}

	@Override
	protected void layoutCursors() {
		layoutInArea(minCursor, minCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);

		layoutInArea(maxCursor, maxCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);

		layoutInArea(midCursor, midCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);
	}

}
