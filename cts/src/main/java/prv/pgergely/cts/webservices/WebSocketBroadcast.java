package prv.pgergely.cts.webservices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.service.MessagingThread;

@Controller
public class WebSocketBroadcast{
	
	private Logger logger = LogManager.getLogger(WebSocketBroadcast.class);
	
	@Autowired
	private MessagingThread msgTh;
	
	@GetMapping("/channel-broadcast")
	public String getBroadcast() {
		return "broadcasting!!!!";
	}
	
	@SendTo("/cts-channel/messaging")
	@MessageMapping("/refreshing")
	public SourceState send(SourceState msg) {
		logger.info(msg);
		msgTh.init(msg);
		return new SourceState(-1L, "Server", msg.getFrom()+" OK");
	}
}
