package controls.simpleSlider;

import controls.Bar;
import controls.Cursor;
import controls.Slider;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

public class SimpleBar extends Bar {

	public SimpleBar(Slider slider) {
		super(slider);
	}

	@Override
	protected void layoutBars(double width) {
		bar1.setWidth(width);
		bar1.setLayoutX(SimpleSliderSkin.horizontalPadding + Cursor.cursorSize);
	}

	@Override
	protected void initBars() {
		bar1 = new Rectangle();
		bar1.setHeight(barHeight);
		bar1.setId("bar");
		bar1.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				((SimpleSliderSkin) slider.getSliderSkin()).getMidCursor()
						.updateValue(event.getX() + SimpleSliderSkin.horizontalPadding + Cursor.cursorSize / 2, width.get());
			}
		});

		bar1.setHeight(barHeight);
	}

	@Override
	protected void initBarWidthProperty() {
		width.bind(bar1.widthProperty());
	}

	@Override
	public void setColors(double value1, double value2) {
		bar1.setFill(subLinearGradient(0, 360));
	}
}
