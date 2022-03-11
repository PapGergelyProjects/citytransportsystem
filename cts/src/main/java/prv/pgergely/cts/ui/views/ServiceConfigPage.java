package prv.pgergely.cts.ui.views;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.domain.TransitFeedView;
import prv.pgergely.cts.service.TransitFeedSource;
import prv.pgergely.cts.ui.MainLayout;

@UIScope
@SpringComponent
@PageTitle("CTS - Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	@Autowired
	private TransitFeedSource feedSource;
	
	private Grid<TransitFeedView> transitFeedGrid;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		transitFeedGrid = initGrid();
		transitFeedGrid.setDataProvider(new ListDataProvider<>(feedSource.getTransitFeeds()));
		this.add(transitFeedGrid);
	}
	
	private Grid<TransitFeedView> initGrid(){
		Grid<TransitFeedView> grid = new Grid<>(TransitFeedView.class, false);
		grid.addColumn("title").setResizable(true).setHeader("Location Name");
		grid.addColumn("feedTitle").setResizable(true).setHeader("Feed Name");
		grid.addColumn("latest").setResizable(true).setHeader("Latest Update");
		grid.addComponentColumn(r -> {
			Button btn = new Button("unregistered");
			btn.getStyle().set("background", "#a6a6a6");
			btn.getStyle().set("color", "white");
			return btn;
		}).setHeader("Status");
		grid.addColumn("enabled").setResizable(true).setHeader("Registered");
		
		return grid;
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		//transitFeedGrid.setDataProvider(new ListDataProvider<>(feedSource.getTransitFeeds()));
	}
	
}
