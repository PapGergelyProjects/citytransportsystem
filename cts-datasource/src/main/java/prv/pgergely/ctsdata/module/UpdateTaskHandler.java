package prv.pgergely.ctsdata.module;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctsdata.utility.Qualifiers;

@Component
public class UpdateTaskHandler {
	
	@Autowired
	private DataUpdater updater;
	
	private Queue<DownloadRequest> storage = new LinkedBlockingQueue<>();
	private Deque<CompletableFuture<DownloadRequest>> futureStorage = new LinkedBlockingDeque<>();
	private Logger logger = LogManager.getLogger(UpdateTaskHandler.class);
	
	@Autowired
	private ScheduledThreadEngine threadEng;
	
	@PostConstruct
	public void init() {
		threadEng.process(1L, 5L, TimeUnit.SECONDS, "RequestProcessor", () -> {
			if(!futureStorage.isEmpty()) {
				CompletableFuture<DownloadRequest> actualUpdate = futureStorage.peekFirst();
				if(actualUpdate.isDone() && !storage.isEmpty()) {
					DownloadRequest nextRequest = storage.poll();
					this.addTask(nextRequest);
					DownloadRequest doneReq = futureStorage.removeLast().join();
					logger.info(doneReq.getFeedId()+" done.");
				}
			}
		});
	}
	
	public void addTask(final DownloadRequest req) {
		if(futureStorage.isEmpty()) {
			futureStorage.push(task(req));
		}else {
			CompletableFuture<DownloadRequest> actualUpdate = futureStorage.pollFirst();
			if(actualUpdate.isDone()) {
				futureStorage.push(task(req));
				futureStorage.addLast(actualUpdate);
			}else {
				storage.add(req);
				futureStorage.addFirst(actualUpdate);
			}
		}
	}
	
	private CompletableFuture<DownloadRequest> task(final DownloadRequest req){
		return CompletableFuture.completedFuture(req).thenApplyAsync(e -> {
			updater.proceedUpdate(e);
			return e;
		}).whenCompleteAsync((a, thr)->{
			if(thr==null) {
				logger.info("Feed source "+a.getFeedId()+" has been successfully updated.");
			}else {
				logger.error(thr.getMessage(), thr);
			}
		});
	}
}
