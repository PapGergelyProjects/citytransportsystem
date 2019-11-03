package prv.pgergely.ctscountry.modules;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.interfaces.FixedThreadEngine;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
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
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(args.getNonOptionArgs());
		System.out.println(args.getOptionNames());
		threadEng.process(10, 300, TimeUnit.SECONDS, "VersionHandler", versionHandler);
		threadEng.process(10, 300, TimeUnit.SECONDS, "ZipSender", zipSender);
	}

}
