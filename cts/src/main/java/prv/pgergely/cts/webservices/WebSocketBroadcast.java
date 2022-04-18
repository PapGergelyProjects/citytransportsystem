package prv.pgergely.cts.webservices;

import java.util.concurrent.BlockingQueue;

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
	private BlockingQueue<SourceState> messages;
	
	@GetMapping("/channel-broadcast")
	public String getBroadcast() {
		return "broadcasting!!!!";
	}
	
	@SendTo("/state/current")
	@MessageMapping("/channel")
	public SourceState send(SourceState msg) {
		logger.info(msg);
		try {
			messages.put(msg);
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
		return new SourceState(-1L, "Server", msg.getFrom()+" OK");
	}
}
