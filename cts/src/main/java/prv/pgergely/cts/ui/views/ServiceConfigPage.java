package prv.pgergely.cts.ui.views;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.domain.TransitFeedView;
import prv.pgergely.cts.service.TransitFeedSource;
import prv.pgergely.cts.ui.MainLayout;
import prv.pgergely.cts.ui.utils.FlexSearchLayout;

@UIScope
@SpringComponent
@PageTitle("CTS - Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	@Autowired
	private TransitFeedSource feedSource;
	
	private TextField search;
	private DatePicker date;
	private Checkbox isRegistered;
	private Grid<TransitFeedView> transitFeedGrid;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setSpacing(false);
		transitFeedGrid = initGrid();
		transitFeedGrid.setDataProvider(new ListDataProvider<>(feedSource.getTransitFeeds()));
		this.add(initSearchBar());
		this.add(transitFeedGrid);
	}
	
	private FlexSearchLayout initSearchBar() {
		search = new TextField("Search by Name");
		search.addValueChangeListener(event -> {
			if(event.getValue().length() >= 3 || event.getValue().isEmpty()) {
				filterGrid();
			}
		});
		date = new DatePicker("Search by Date");
		date.addValueChangeListener(event -> {
			filterGrid();
		});
		isRegistered = new Checkbox("Registered");
		isRegistered.addValueChangeListener(event -> {
			filterGrid();
		});
		FlexSearchLayout searchLay = new FlexSearchLayout(search, date, isRegistered).initStyle();
		searchLay.setGap("10px");
		
		return searchLay;
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
	
	private void filterGrid() {
		ListDataProvider<TransitFeedView> listDataProvider = (ListDataProvider<TransitFeedView>)transitFeedGrid.getDataProvider();
		listDataProvider.setFilter(getFilter());
	}
	
	private SerializablePredicate<TransitFeedView> getFilter(){
		return item -> {
			boolean match = true;
			String sVal = search.getValue();
			LocalDate sDate = date.getValue();
			if(sVal != null) {
				match = item.getTitle().contains(sVal) || sVal.isEmpty();
				if(!match) {
					return match;
				}
			}
			if(sDate!=null) {
				match = sDate.equals(item.getLatest());
				if(!match) {
					return match;
				}
			}
			match = isRegistered.getValue().equals(item.isEnabled());
			if(!match) {
				return match;
			}
			
			return match;
		};
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		//transitFeedGrid.setDataProvider(new ListDataProvider<>(feedSource.getTransitFeeds()));
	}
	
}
