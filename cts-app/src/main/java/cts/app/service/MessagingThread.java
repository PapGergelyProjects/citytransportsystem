package cts.app.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.domain.SourceState;
import prv.pgergely.cts.common.interfaces.FixedThreadEngine;

@Service
public class MessagingThread {
	
	@Autowired
	private FixedThreadEngine pool;
	
	private Map<String, Function<SourceState, Void>> taskContainer = new ConcurrentHashMap<>();
	
	public void init(SourceState state) {
		pool.process("MessageReceiverThread", () -> {
			SourceState actualState = state;
			if(actualState != null) {
				taskContainer.values().forEach(e -> e.apply(actualState));
			}
		});
	}
	
	public void addFunction(String name, Function<SourceState, Void> funct) {
		removeFunction(name);
		taskContainer.put(name, funct);
	}
	
	public void removeFunction(String name) {
		taskContainer.remove(name);
	}
}
