package controls;

import controls.simpleSlider.SimpleBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SkinBase;
import javafx.scene.input.MouseEvent;

public abstract class SliderSkin extends SkinBase<Slider> {
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

	public void updateCursorXPos(Cursor cursor, double value) {
		// Compute the absolute cursor coordinate according to the rangeSlider value.
		double x = bar.getX() + (value - slider.getMinValue()) * bar.getWidth()
				/ (slider.getMaxValue() - slider.getMinValue());
		x += cursor.getOffset();
		cursor.setLayoutX(x + horizontalPadding);
	}
	
	protected double cursorYPos() {
		// return bar.getY() + (Bar.barHeight - Cursor.cursorSize) / 2;
		return bar.getY() + (SimpleBar.barHeight - Cursor.cursorSize) / 2 - 1;
		// return bar.getY();
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

	/*****************************************************************/
	/* The following functions need to be implemented according
	 * to the desired slider behavior.
	 */
	protected abstract void initBar();
	
	protected abstract void initCursors();
	
	protected abstract void updateCursorsPositions();
	
	protected abstract void initCursorListeners(Cursor cursor);
	
	protected abstract void initDrag(Cursor cursor, MouseEvent event);
	
	protected abstract void layoutBars();
	
	protected abstract void layoutCursors();
	/*****************************************************************/

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
	
}
