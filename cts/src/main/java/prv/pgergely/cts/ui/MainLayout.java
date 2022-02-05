package prv.pgergely.cts.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@PWA(name = "CTS", shortName = "CTS", enableInstallPrompt = false)
@Theme(themeFolder = "cts")
@PageTitle("Main")
public class MainLayout extends AppLayout {
	
    public static class MenuItemInfo {

        private String text;
        private String iconClass;
        private Class<? extends Component> view;

        public MenuItemInfo(String text, String iconClass, Class<? extends Component> view) {
            this.text = text;
            this.iconClass = iconClass;
            this.view = view;
        }

        public String getText() {
            return text;
        }

        public String getIconClass() {
            return iconClass;
        }

        public Class<? extends Component> getView() {
            return view;
        }

    }
	
	@Override
	protected void afterNavigation() {
		super.afterNavigation();
	}
	
}
