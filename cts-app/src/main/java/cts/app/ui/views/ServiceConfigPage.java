package cts.app.ui.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestClientException;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import cts.app.domain.ResponseData;
import cts.app.domain.SelectedFeed;
import cts.app.domain.TransitFeedView;
import cts.app.service.FeedService;
import cts.app.service.MessagingThread;
import cts.app.ui.MainLayout;
import cts.app.ui.utils.CtsNotification;
import cts.app.ui.utils.FlexSearchLayout;
import cts.app.ui.utils.StateButton;
import cts.app.utils.SourceStates;
import jakarta.annotation.PostConstruct;

@UIScope
@SpringComponent
//@PreserveOnRefresh
@PageTitle("CTS - Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	private static final long serialVersionUID = 2300113904760437763L;
	
	@Autowired
	private FeedService feedSource;
	
	@Autowired
	private MessagingThread msgTh;
	
	@Autowired
	private CtsNotification noti;
	
	private TextField search;
	private DatePicker date;
	private Checkbox isRegistered;
	private Grid<TransitFeedView> transitFeedGrid;
	private Thread refreshThread;
	
	@PostConstruct
	public void init() {
		this.setSizeFull();
		this.setSpacing(false);
		transitFeedGrid = initGrid();
		transitFeedGrid.setDataProvider(new ListDataProvider<>(getFeedViews()));
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
				if(r.isActive()) {
					Button btn = new Button(VaadinIcon.PAUSE.create());
					btn.addClickListener(event -> {
						feedSource.deleteFeed(r.getId());
						Notification.show(r.getFeedTitle()+" deleted.", 5000, Position.TOP_CENTER);
						refresh();
					});
					return btn;
				}
				Button btn = new Button(VaadinIcon.PLAY_CIRCLE.create());
				btn.addClickListener(event -> {
					feedSource.startFeed(new SelectedFeed(r.getId(), r.getTitle(), r.getFeedTitle(), r.getLatest()));
					Notification.show(r.getFeedTitle()+" started.", 5000, Position.TOP_CENTER);
					refresh();
				});
				return btn;
			}
			Button newBtn = new Button(VaadinIcon.DOWNLOAD.create());
			newBtn.addClickListener(event -> {
				ResponseData resp = feedSource.registerFeed(new SelectedFeed(r.getId(), r.getTitle(), r.getFeedTitle(), r.getLatest()));
				Notification.show(resp.getTitle()+" registered.", 5000, Position.TOP_CENTER);
				refresh();
			});
			return newBtn;
		}).setWidth("100px").setFlexGrow(0);
		grid.addColumn("title").setResizable(true).setHeader("Location Name");
		grid.addColumn("feedTitle").setResizable(true).setHeader("Feed Name");
		grid.addColumn("latest").setResizable(true).setWidth("150px").setFlexGrow(0).setHeader("Latest Update");
		grid.addComponentColumn(r -> {
			switch (SourceStates.getByName(r.getState().getState())) {
				case ONLINE: {
					return StateButton.ACTIVE.get();
				}
				case OFFLINE:{
					return StateButton.STOPPED.get();
				}
				case UPDATING:{
					return StateButton.PAUSED.get();
				}
				case UNREGISTERED:{
					return StateButton.UNREGISTERED.get();
				}
				default:{
					return StateButton.UNREGISTERED.get();
				}
			}
		}).setWidth("200px").setFlexGrow(0).setHeader("Status");
		grid.addColumn("enabled").setResizable(true).setHeader("Registered");
		
		return grid;
	}
	
	private void refresh() {
		transitFeedGrid.setDataProvider(new ListDataProvider<>(getFeedViews()));
		filterGrid();
	}
	
	private void filterGrid() {
		ListDataProvider<TransitFeedView> listDataProvider = (ListDataProvider<TransitFeedView>)transitFeedGrid.getDataProvider();
		listDataProvider.setFilter(getFilter());
	}
	
	public void refreshGrid(ListDataProvider<TransitFeedView> data) {
		transitFeedGrid.setDataProvider(data);
		filterGrid();
	}
	
	public List<TransitFeedView> getFeedViews(){
		try {
			return feedSource.getTransitFeeds();
		} catch (RestClientException e) {
			noti.showNotification(NotificationVariant.LUMO_ERROR, "No feed available.");
			return new ArrayList<>();
		}
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
		msgTh.addFunction("ServiceConfig", state -> { //TODO: Unique thread name
			attachEvent.getSource().getUI().ifPresent(ui -> ui.access(() -> {
				Map<Long, TransitFeedView> feeds = this.getFeedViews().stream().collect(Collectors.toMap(k -> k.getId(), v -> v));
				TransitFeedView view = feeds.get(state.getFeedId());
				if(view != null) {
					view.getState().setState(state.getState());
				}
				this.refreshGrid(new ListDataProvider<>(feeds.values()));
				noti.showNotification(NotificationVariant.LUMO_SUCCESS, "Feed statues refreshed");
			}));
			return null;
		});
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		msgTh.removeFunction("ServiceConfig");
	}
	
	
	
}
