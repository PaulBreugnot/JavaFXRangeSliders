package sliders.simpleSlider;

import sliders.simpleSlider.SimpleBar;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.input.MouseEvent;
import sliders.Cursor;
import sliders.SliderSkin;

public class SimpleSliderSkin extends SliderSkin {

	

	protected SimpleSliderSkin(SimpleSlider slider) {
		super(slider);
	}

	@Override
	protected void initBar() {
		bar = new SimpleBar(slider);

		bar.widthProperty().addListener(barWidthListener());

		getChildren().add(bar.getBar1());

	}
	
	@Override
	protected void initCursors() {
		initSimpleCursor();
	}
	
	@Override
	protected void updateCursorsPositions() {
		// To override according to used cursors
		updateCursorXPos(midCursor, slider.getValue());
	}

	private void initSimpleCursor() {
		midCursor = new Cursor(Cursor.CursorType.SIMPLE, slider, bar.getStrokeWidth());
		getChildren().add(midCursor);
		updateCursorXPos(midCursor, slider.getValue());
		midCursor.setLayoutY(cursorYPos());
		initCursorListeners(midCursor);
	}

	@Override
	protected void initCursorListeners(Cursor cursor) {
		// To override according to cursors behavior
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
				cursor.updateValue(newPos, bar.getWidth());
			}
		});
	}

	@Override
	protected void initDrag(Cursor cursor, MouseEvent event) {
		// To override according to cursor behavior
		//
		// when the cursor is dragged, event.getX() is given in the cursor coordinate
		// system, that we need to adjust in the same time, so we need to use absolutes
		// coordinates (a.k.a event.getSceneX()) to make diff with the original position
		// on drag.
		cursorDragOrigin = event.getSceneX();
		cursor.setDragOriginLayout(cursor.getLayoutX());
	}
	
	@Override
	protected void layoutBars() {
		layoutInArea(bar.getBar1(), bar.getBar1().getLayoutX(), verticalPadding + SimpleBar.barHeight,
				bar.getBar1().getWidth(), SimpleBar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
	}
	
	@Override
	protected void layoutCursors() {
		layoutInArea(midCursor, midCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);
	}
}
