package prv.pgergely.cts.ui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class FlexSearchLayout extends FlexLayout {
	
	private Component[] children;
	
	public FlexSearchLayout(Component... children) {
		super(children);
		this.children = children;
		this.setFlexDirection(FlexDirection.ROW);
		this.setFlexWrap(FlexWrap.WRAP);
		this.setJustifyContentMode(JustifyContentMode.START);
		this.setAlignItems(Alignment.BASELINE);
	}
	
	public void setGap(String px) {
		for(Component child : children) {
			child.getStyle().set("padding-right", px);
		}
	}
	
}
