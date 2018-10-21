package sliders.colorSlider;

public interface ColorSlider {
	
	public ColorConfig getColorConfig();
	
	public ColorSelectionMode getColorSelectionMode();
	
	public default ColorConfig getMinColorConfig() {
		return getColorConfig();
	}
	
	public default ColorConfig getMaxColorConfig() {
		return getColorConfig();
	}

}
