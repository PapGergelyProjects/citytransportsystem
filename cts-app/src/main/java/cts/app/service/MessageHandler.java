package cts.app.service;

import java.util.function.Function;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.SourceState;

@Service
public class MessageHandler implements ChannelInterceptor {
	
	private Function<SourceState, SourceState> funct;
	
	@Override
	public boolean preReceive(MessageChannel channel) {
		return true;
	}

	@Override
	public Message<?> postReceive(Message<?> message, MessageChannel channel) {
		Object payload = message.getPayload();
		if(payload instanceof SourceState state) {
			funct.apply(state);
		}
		return message;
	}
	
	public void addListener(Function<SourceState, SourceState> funct) {
		this.funct = funct;
	}
	
}
