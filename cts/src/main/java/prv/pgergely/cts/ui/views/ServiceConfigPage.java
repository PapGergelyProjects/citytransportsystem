package prv.pgergely.cts.ui.views;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import prv.pgergely.cts.domain.ResponseData;
import prv.pgergely.cts.domain.SelectedFeed;
import prv.pgergely.cts.domain.TransitFeedView;
import prv.pgergely.cts.service.FeedOperationService;
import prv.pgergely.cts.service.TransitFeedSource;
import prv.pgergely.cts.ui.MainLayout;
import prv.pgergely.cts.ui.utils.FlexSearchLayout;

@UIScope
@SpringComponent
@PageTitle("CTS - Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	private static final long serialVersionUID = 2300113904760437763L;

	@Autowired
	private TransitFeedSource feedSource;
	
	@Autowired
	private FeedOperationService operationSrvc;
	
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
		date.addValueChangeListener(event -> filterGrid());
		isRegistered = new Checkbox("Only Registered");
		isRegistered.addValueChangeListener(event -> filterGrid());
		FlexSearchLayout searchLay = new FlexSearchLayout(search, date, isRegistered);
		searchLay.setGap("10px");
		
		return searchLay;
	}
	
	private Grid<TransitFeedView> initGrid(){
		Grid<TransitFeedView> grid = new Grid<>(TransitFeedView.class, false);
		grid.addComponentColumn(r -> {
			if(r.isEnabled()) {
				Button btn = new Button(VaadinIcon.FOLDER_REMOVE.create());
				btn.addClickListener(event -> {
					operationSrvc.deleteFeed(r.getId());
					Notification.show(r.getFeedTitle()+" deleted.", 5000, Position.TOP_CENTER);
					refresh();
				});
				return btn;
			}
			Button newBtn = new Button(VaadinIcon.FOLDER_ADD.create());
			newBtn.addClickListener(event -> {
				ResponseData resp = operationSrvc.registerFeed(new SelectedFeed(r.getId(), r.getTitle(), r.getFeedTitle(), r.getLatest()));
				Notification.show(resp.getTitle()+" registered.", 5000, Position.TOP_CENTER);
				refresh();
			});
			return newBtn;
		}).setWidth("100px").setFlexGrow(0);
		grid.addColumn("title").setResizable(true).setHeader("Location Name");
		grid.addColumn("feedTitle").setResizable(true).setHeader("Feed Name");
		grid.addColumn("latest").setResizable(true).setWidth("150px").setFlexGrow(0).setHeader("Latest Update");
		grid.addComponentColumn(r -> {
			if(r.isEnabled()) {
				Button btn = new Button("registered");
				btn.getStyle().set("background", "#009900");
				btn.getStyle().set("color", "white");
				
				return btn;
			}
			Button btn = new Button("unregistered");
			btn.getStyle().set("background", "#a6a6a6");
			btn.getStyle().set("color", "white");
			
			return btn;
		}).setWidth("200px").setFlexGrow(0).setHeader("Status");
		grid.addColumn("enabled").setResizable(true).setHeader("Registered");
		
		return grid;
	}
	
	private void refresh() {
		transitFeedGrid.setDataProvider(new ListDataProvider<>(feedSource.getTransitFeeds()));
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
			if(sDate != null) {
				match = sDate.equals(item.getLatest());
				if(!match) {
					return match;
				}
			}
			if(!isRegistered.getValue()) {
				match = true;
			} else {
				match = isRegistered.getValue().equals(item.isEnabled());
				if(!match) {
					return match;
				}
			}
			
			return match;
		};
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
	}
	
}
