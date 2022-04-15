package prv.pgergely.cts.webservices;

import java.util.Queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import prv.pgergely.cts.common.domain.SourceState;

@Controller
public class WebSocketBroadcast{
	
	private Logger logger = LogManager.getLogger(WebSocketBroadcast.class);
	
	@Autowired
	private Queue<SourceState> messages;
	
	@GetMapping("/channel-broadcast")
	public String getBroadcast() {
		return "broadcasting!!!!";
	}
	
	@SendTo("/state/current")
	@MessageMapping("/channel")
	public SourceState send(SourceState msg) {
		logger.info(msg);
		messages.add(msg);
		return new SourceState(-1L, "Server", msg.getFrom()+" OK");
	}
}
