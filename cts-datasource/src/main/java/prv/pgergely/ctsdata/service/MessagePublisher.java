package prv.pgergely.ctsdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.ctsdata.interfaces.UtilityRepo;

@Service
public class MessagePublisher {
	
	//@Autowired
	private StompSession session;
	
	@Autowired
	private UtilityRepo utility;
	
	public void publish(SourceState message) {
		utility.updateSourceState(message.getState());
		session.send("/app/refreshing", message);
	}
}
