package prv.pgergely.cts.ui.utils;

import com.vaadin.flow.component.button.Button;

public enum StateButton {
	
	ACTIVE {
		@Override
		public Button get() {
			Button btn = new Button("active");
			btn.getStyle().set("background", "#009900");
			btn.getStyle().set("color", "white");
			btn.setWidth("120px");
			
			return btn;
		}
	},
	STOPPED {
		@Override
		public Button get() {
			Button btn = new Button("stopped");
			btn.getStyle().set("background", "#990000");
			btn.getStyle().set("color", "white");
			btn.setWidth("120px");
			
			return btn;
		}
	},
	PAUSED {
		@Override
		public Button get() {
			Button btn = new Button("paused");
			btn.getStyle().set("background", "#e68a00");
			btn.getStyle().set("color", "white");
			btn.setWidth("120px");
			
			return btn;
		}
	},
	UNREGISTERED {
		@Override
		public Button get() {
			Button btn = new Button("unregistered");
			btn.getStyle().set("background", "#a6a6a6");
			btn.getStyle().set("color", "white");
			btn.setWidth("120px");
			
			return btn;
		}
	};
	
	public abstract Button get();
}
