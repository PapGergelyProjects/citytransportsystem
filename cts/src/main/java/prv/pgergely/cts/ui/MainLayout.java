package prv.pgergely.cts.ui;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.ListItem;
import com.vaadin.flow.component.html.Nav;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.html.UnorderedList;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;

import prv.pgergely.cts.ui.utils.LineIcon;
import prv.pgergely.cts.ui.views.AboutView;
import prv.pgergely.cts.ui.views.LandingPage;
import prv.pgergely.cts.ui.views.ServiceConfigPage;

@UIScope
@SpringComponent
@PageTitle("Main")
@PWA(name = "CTS", shortName = "CTS", enableInstallPrompt = false)
@Theme(themeFolder = "cts")
public class MainLayout extends AppLayout {
	
	private static final long serialVersionUID = 7069960176916400022L;

	public static class MenuItemInfo extends ListItem {

        private final Class<? extends Component> view;

        public MenuItemInfo(String menuTitle, String iconClass, Class<? extends Component> view) {
            this.view = view;
            RouterLink link = new RouterLink();
            // Use Lumo classnames for various styling
            link.addClassNames("flex", "mx-s", "p-s", "relative", "text-secondary");
            link.setRoute(view);

            Span text = new Span(menuTitle);
            // Use Lumo classnames for various styling
            text.addClassNames("font-medium", "text-s");

            link.add(new LineAwesomeIcon(iconClass), text);
            add(link);
        }

        public Class<?> getView() {
            return view;
        }

        /**
         * Simple wrapper to create icons using LineAwesome iconset. See
         * https://icons8.com/line-awesome
         */
        @NpmPackage(value = "line-awesome", version = "1.3.0")
        public static class LineAwesomeIcon extends Span {
            public LineAwesomeIcon(String lineawesomeClassnames) {
                // Use Lumo classnames for suitable font size and margin
                addClassNames("me-s", "text-l");
                if (!lineawesomeClassnames.isEmpty()) {
                    addClassNames(lineawesomeClassnames);
                }
            }
        }

    }

    private H1 viewTitle;
    private Button slideBtn;

    public MainLayout() {
        setPrimarySection(Section.NAVBAR);
        addToNavbar(true, createHeaderContent());
        addToDrawer(createDrawerContent());
    }

    private Component createHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.addClassName("text-secondary");
        toggle.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames("m-0", "text-l");
        HorizontalLayout lay1 = new HorizontalLayout(toggle, viewTitle);
        lay1.setWidth("100%");
        lay1.setAlignItems(Alignment.CENTER);
        slideBtn = new Button(VaadinIcon.ARROW_DOWN.create());
        HorizontalLayout lay2 = new HorizontalLayout(slideBtn);
        lay2.setWidth("100%");
        lay2.setJustifyContentMode(JustifyContentMode.END);

        Header header = new Header(lay1, lay2);
        header.addClassNames("bg-base", "border-b", "border-contrast-10", "box-border", "flex", "h-xl", "items-center", "w-full");
        return header;
    }

    private Component createDrawerContent() {
        H2 appName = new H2("Options");
        appName.addClassNames("flex", "items-center", "h-xl", "m-0", "px-m", "text-m");

        com.vaadin.flow.component.html.Section section = new com.vaadin.flow.component.html.Section(appName,
                createNavigation(), createFooter());
        section.addClassNames("flex", "flex-col", "items-stretch", "max-h-full", "min-h-full");
        return section;
    }

    private Nav createNavigation() {
        Nav nav = new Nav();
        nav.addClassNames("border-b", "border-contrast-10", "flex-grow", "overflow-auto");
        nav.getElement().setAttribute("aria-labelledby", "views");

        // Wrap the links in a list; improves accessibility
        UnorderedList list = new UnorderedList();
        list.addClassNames("list-none", "m-0", "p-0");
        nav.add(list);

        for (MenuItemInfo menuItem : createMenuItems()) {
            list.add(menuItem);

        }
        return nav;
    }

    private MenuItemInfo[] createMenuItems() {
        return new MenuItemInfo[]{
            new MenuItemInfo("Transport map", LineIcon.GLOBE.getIcon(), LandingPage.class),
            new MenuItemInfo("Service configuration", LineIcon.WRENCH.getIcon(), ServiceConfigPage.class),
            new MenuItemInfo("About", LineIcon.FILE.getIcon(), AboutView.class),
        };
    }

    private Footer createFooter() {
        Footer layout = new Footer();
        layout.addClassNames("flex", "items-center", "my-s", "px-m", "py-xs");

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
    
    public static MainLayout getMainInstance() {
    	MainLayout mainLay = (MainLayout)UI.getCurrent().getChildren().filter(p -> p.getClass() == MainLayout.class).findFirst().orElse(null);
    	return mainLay;
    }

	public Button getSlideBtn() {
		slideBtn = new Button(VaadinIcon.ARROW_DOWN.create());
		return slideBtn;
	}
    
}
