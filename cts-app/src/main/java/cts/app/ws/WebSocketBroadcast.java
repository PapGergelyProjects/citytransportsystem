package cts.app.ws;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cts.app.service.FeedService;
import cts.app.service.MessagingThread;
import prv.pgergely.cts.common.domain.DataSourceState;
import prv.pgergely.cts.common.domain.SelectedFeed;
import prv.pgergely.cts.common.domain.SourceState;

@Controller
public class WebSocketBroadcast{
	
	private Logger logger = LogManager.getLogger(WebSocketBroadcast.class);
	
	@Autowired
	private MessagingThread msgTh;
	
	@Autowired
	private FeedService feedSrvc;
	
	@GetMapping("/channel-broadcast")
	public String getBroadcast() {
		return "broadcasting!!!!";
	}
	
	@SendTo("/cts-channel/messaging")
	@MessageMapping("/refreshing")
	public SourceState send(SourceState msg) {
		logger.info(msg);
		SelectedFeed feed = new SelectedFeed();
		feed.setId(msg.getFeedId());
		feed.setState(msg.getState());
		feedSrvc.updateState(feed);
		msgTh.init(msg);
		return new SourceState(-1L, "Server", DataSourceState.TECHNICAL);
	}
}
