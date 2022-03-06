package prv.pgergely.cts.ui.views;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.domain.TransitFeedView;
import prv.pgergely.cts.ui.MainLayout;

@UIScope
@SpringComponent
@PageTitle("CTS - Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	private Grid<TransitFeedView> transitFeedGrid;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		transitFeedGrid = new Grid<>(TransitFeedView.class);
		transitFeedGrid.setColumns("title", "feedTitle", "latest", "enabled");
		this.add(transitFeedGrid);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		transitFeedGrid.setDataProvider(new ListDataProvider<>(createDummy()));
	}
	
	private List<TransitFeedView> createDummy() {
		TransitFeedView v1 = new TransitFeedView();
		v1.setId(1L);
		v1.setTitle("Transit1");
		v1.setEnabled(true);
		v1.setFeedTitle("Feed1");
		v1.setLatest(LocalDate.now());
		TransitFeedView v2 = new TransitFeedView();
		v2.setId(2L);
		v2.setTitle("Transit2");
		v2.setEnabled(false);
		v2.setFeedTitle("Feed2");
		v2.setLatest(LocalDate.now());
		
		return Arrays.asList(v1, v2);
	}
	
}
