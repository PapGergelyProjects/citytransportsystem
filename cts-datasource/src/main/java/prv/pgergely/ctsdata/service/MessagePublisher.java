package prv.pgergely.ctsdata.service;

import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.ctsdata.utility.WebSocketSessionHandler;

@Service
public class MessagePublisher {
	
	public void publish(SourceState message) {
		WebSocketSessionHandler.getSession().send("/app/channel", message);
	}
}
