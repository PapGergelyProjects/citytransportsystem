package prv.pgergely.ctscountry.modules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.interfaces.FixedThreadEngine;

@Order(2)
@Component
public class ThreadPoolInit implements ApplicationRunner {
	
	@Autowired
	private FixedThreadEngine threadEng;
	
	@Autowired
	private FeedVersionHandler versionHandler;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		threadEng.process("VersionHandler", versionHandler);
	}

}
