package controls.simpleSlider;

import controls.Cursor;
import controls.simpleSlider.Bar;
import controls.rangeSlider.RangeSlider;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;

public class SliderSkin extends SkinBase<Slider> {

	// Component layout parameters
	public static final double horizontalPadding = 5;
	public static final double verticalPadding = 0;

	// Main Component
	protected Slider slider;

	// Graphic Items
	// private Rectangle bar;
	protected Cursor midCursor;
	protected Bar bar;

	// Cursor moves
	protected double cursorDragOrigin;
	protected boolean reinitializeDrag;

	protected SliderSkin(Slider slider) {
		super(slider);
		this.slider = slider;
		initGraphics();
	}

	public Cursor getMidCursor() {
		return midCursor;
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
	
	public Bar getBar() {
		return bar;
	}

	private void initGraphics() {
		initBar();
		initCursors();
		slider.getScene().heightProperty().addListener(new ChangeListener<Number>() {
			// If the scene changes, cursors positions are adjusted (bug otherwise...)
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateCursorsPositions();
			}
		});
		
	}

	protected void initBar() {
		// Can be override to personalize bar
		bar = new Bar(slider);

		bar.widthProperty().addListener(barWidthListener());

		getChildren().add(bar.getBar1());

	}
	
	protected void initCursors() {
		// Can be override to personalize cursors
		initSimpleCursor();
	}
	
	protected ChangeListener<Number> barWidthListener() {
		return new ChangeListener<Number>() {
			// If the bar width changes, cursors positions are adjusted
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// System.out.println("new bar width : " + (double) newValue);
				updateCursorsPositions();
			}
		};
	}
	
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

	public void updateCursorXPos(Cursor cursor, double value) {
		// Compute the absolute cursor coordinate according to the rangeSlider value.
		double x = bar.getX() + (value - slider.getMinValue()) * bar.getWidth()
				/ (slider.getMaxValue() - slider.getMinValue());
		System.out.println("Value : " + value);
		System.out.println("width : " + bar.getWidth());
		System.out.println(x);
		x += cursor.getOffset();
		cursor.setLayoutX(x + horizontalPadding);
	}

	protected double cursorYPos() {
		// return bar.getY() + (Bar.barHeight - Cursor.cursorSize) / 2;
		return bar.getY() + (Bar.barHeight - Cursor.cursorSize) / 2 - 1;
		// return bar.getY();
	}

	@Override
	protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
		
		bar.setWidth(contentWidth - 2 * horizontalPadding - 2 * Cursor.cursorSize);

		layoutBars();

//		System.out.println("Value 1 : " + rangeSlider.getValue1());
//		System.out.println("Value 2 : " + rangeSlider.getValue2());
//		System.out.println("LayoutX : " + maxCursor.getLayoutX());
//		System.out.println("LayoutY : " + minCursor.getLayoutY());

		layoutCursors();

		
	}
	
	protected void layoutBars() {
		layoutInArea(bar.getBar1(), bar.getBar1().getLayoutX(), verticalPadding + Bar.barHeight,
				bar.getBar1().getWidth(), Bar.barHeight + 2 * verticalPadding, 0, HPos.LEFT, VPos.TOP);
	}
	
	protected void layoutCursors() {
		layoutInArea(midCursor, midCursor.getLayoutX(), cursorYPos(), Cursor.cursorSize, Cursor.cursorSize, 0,
				HPos.LEFT, VPos.TOP);
	}
}
