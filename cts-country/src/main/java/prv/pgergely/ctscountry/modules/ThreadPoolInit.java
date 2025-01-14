package prv.pgergely.ctscountry.modules;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctscountry.configurations.CtsConfig;
import prv.pgergely.ctscountry.services.FeedVersionHandler;
import prv.pgergely.ctscountry.services.ZipContentSender;

@Order(2)
@Component
public class ThreadPoolInit implements ApplicationRunner {
	
	@Autowired
	private ScheduledThreadEngine threadEng;
	
	@Autowired
	private FeedVersionHandler version;
	
	@Autowired
	private ZipContentSender zip;
	
	@Autowired
	private CtsConfig config;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		long initDelay = config.getThreadParams().getInitDelayed();
		long delayBetween = config.getThreadParams().getDelayBetween();
		long offset = config.getThreadParams().getOffset();
		threadEng.process(initDelay, delayBetween, TimeUnit.SECONDS, "VersionHandler", version);
		threadEng.process(initDelay, delayBetween+offset, TimeUnit.SECONDS, "ZipSender", zip);
	}

}
