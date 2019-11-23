package prv.pgergely.ctscountry.modules;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.interfaces.FixedThreadEngine;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.services.ZipContentSender;

@Order(2)
@Component
public class ThreadPoolInit implements ApplicationRunner {
	
	@Autowired
	private ScheduledThreadEngine threadEng;
	
	@Autowired
	private FeedVersionHandler versionHandler;
	
	@Autowired
	private ZipContentSender zipSender;
	
	@Autowired
	private CtsConfig config;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		long initDelay = config.getThreadParams().getInitDelayed();
		long delayBetween = config.getThreadParams().getDelayBetween();
		threadEng.process(initDelay, delayBetween, TimeUnit.SECONDS, "VersionHandler", versionHandler);
		threadEng.process(initDelay, delayBetween, TimeUnit.SECONDS, "ZipSender", zipSender);
	}

}
