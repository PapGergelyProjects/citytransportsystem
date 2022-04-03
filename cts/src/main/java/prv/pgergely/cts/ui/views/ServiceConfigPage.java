package prv.pgergely.cts.ui.views;

import java.time.LocalDate;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
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

import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.domain.ResponseData;
import prv.pgergely.cts.domain.SelectedFeed;
import prv.pgergely.cts.domain.TransitFeedView;
import prv.pgergely.cts.service.FeedService;
import prv.pgergely.cts.ui.MainLayout;
import prv.pgergely.cts.ui.utils.FlexSearchLayout;
import prv.pgergely.cts.ui.utils.StateButton;
import prv.pgergely.cts.utils.SourceStates;

@UIScope
@SpringComponent
@PageTitle("CTS - Configuration")
@Route(value = "configuration", layout = MainLayout.class)
public class ServiceConfigPage extends VerticalLayout {
	
	private static final long serialVersionUID = 2300113904760437763L;
	
	@Autowired
	private FeedService feedSource;
	
	@Autowired
	private Queue<SourceState> messages;
	
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
		transitFeedGrid.setDataProvider(new ListDataProvider<>(feedSource.getTransitFeeds()));
		filterGrid();
	}
	
	private void filterGrid() {
		ListDataProvider<TransitFeedView> listDataProvider = (ListDataProvider<TransitFeedView>)transitFeedGrid.getDataProvider();
		listDataProvider.setFilter(getFilter());
	}
	
	public void filterGrid(ListDataProvider<TransitFeedView> data) {
		transitFeedGrid.setDataProvider(data);
		data.setFilter(getFilter());
	}
	
	public List<TransitFeedView> getFeedViews(){
		return feedSource.getTransitFeeds();
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
	
//	private class FeedSourceStateRefresh extends Thread {
//		
//		private UI currentUI;
//		private ServiceConfigPage view;
//		
//		public FeedSourceStateRefresh(UI currentUI, ServiceConfigPage view) {
//			this.currentUI = currentUI;
//			this.view = view;
//		}
//
//		@Override
//		public void run() {
//			while(true) {
//				if(messages.size() > 0) {
//					currentUI.access(() -> {
//						SourceState current = messages.poll();
//						List<TransitFeedView> feeds = view.getFeedViews();
//						feeds.stream().filter(p -> current.getFrom().equals(p.getState().getFrom())).map(m -> {
//							SourceState cs = m.getState();
//							cs.setState(current.getState());
//							return m;
//						}).collect(Collectors.toList());
//						view.filterGrid(new ListDataProvider<>(feeds));
//					});
//				}
//			}
//		}
//
//	}
}
