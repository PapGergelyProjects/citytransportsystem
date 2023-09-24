package prv.pgergely.ctsdata.module;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;
import prv.pgergely.ctsdata.service.ZipHandlerService;

@Component
public class DatasourceUpdater {
	
	@Autowired
	private ScheduledThreadEngine threadEng;
	
	@Autowired
	private ZipHandlerService zipSrvc;
	
	private Queue<CompletableFuture<DownloadRequest>> storage = new LinkedBlockingQueue<>();
	private Deque<CompletableFuture<DownloadRequest>> futureStorage = new LinkedBlockingDeque<>();
	private Logger logger = LogManager.getLogger(DatasourceUpdater.class);
	
	@PostConstruct
	public void init() {
		threadEng.process(1L, 5L, TimeUnit.SECONDS, "RequestProcessor", () -> {
			if(!futureStorage.isEmpty()) {
				CompletableFuture<DownloadRequest> actualUpdate = futureStorage.peekFirst();
				if(actualUpdate.isDone() && !storage.isEmpty()) {
					CompletableFuture<DownloadRequest> nextRequest = storage.poll();
					this.addTask(nextRequest.join());
					DownloadRequest doneReq = futureStorage.removeLast().join();
					logger.info(doneReq.getFeedId()+" done.");
				}
			}
		});
	}
	
	public void addTask(final DownloadRequest req) {
		CompletableFuture<DownloadRequest> actualRequest = CompletableFuture.completedFuture(req);
		if(futureStorage.isEmpty()) {
			futureStorage.push(task(actualRequest));
		}else {
			CompletableFuture<DownloadRequest> actualUpdate = futureStorage.pollFirst();
			if(actualUpdate.isDone()) {
				futureStorage.push(task(actualRequest));
				futureStorage.addLast(actualUpdate);
			}else {
				storage.add(actualRequest);
				futureStorage.addFirst(actualUpdate);
			}
		}
	}
	
	private CompletableFuture<DownloadRequest> task(final CompletableFuture<DownloadRequest> completedRequest){
		return completedRequest.thenApplyAsync(e -> {
			zipSrvc.proceedZipFile(e);
			return e;
		}).exceptionallyAsync(e -> {
			logger.error(e.getMessage());
			DownloadRequest ret = new DownloadRequest(-1L, null, null);
			return ret;
		}).whenCompleteAsync((a, thr)->{
			if(thr==null) {
				if(a.getFeedId() == -1) {
					logger.warn("Feed source has been failed to update.");
				}else {
					logger.info("Feed source "+a.getFeedId()+" has been successfully updated.");
				}
			}else {
				logger.error(thr.getMessage(), thr);
			}
		});
	}
}
