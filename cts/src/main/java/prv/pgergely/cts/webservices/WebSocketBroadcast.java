package prv.pgergely.cts.webservices;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import prv.pgergely.cts.common.domain.SourceState;

@Controller
public class WebSocketBroadcast {
	
	private Logger logger = LogManager.getLogger(WebSocketBroadcast.class);
	
	@GetMapping("/channel-broadcast")
	public String getBroadcast() {
		return "broadcasting!!!!";
	}
	
	@SendTo("/state/current")
	@MessageMapping("/channel")
	public SourceState send(SourceState msg) {
		logger.info(msg);
		return new SourceState("Server", "Check");
	}
}
