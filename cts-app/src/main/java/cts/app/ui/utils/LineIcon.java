package cts.app.ui.utils;

public enum LineIcon {
	
	GLOBE("la la-globe"),
	FILE("la la-file"),
	WRENCH("la la-wrench");
	
	private String icon;
	
	private LineIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}
	
}
