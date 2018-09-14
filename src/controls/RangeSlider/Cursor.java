package controls.RangeSlider;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cursor extends Rectangle {
	
	public static final double cursorSize = 15;
	private Color cursorColor = Color.WHITE;
	
	public Cursor() {
		super(0, cursorSize / 2, cursorSize, cursorSize);
		setFill(cursorColor);
		setStroke(Color.BLACK);
		setStrokeWidth(1);
	}

}
