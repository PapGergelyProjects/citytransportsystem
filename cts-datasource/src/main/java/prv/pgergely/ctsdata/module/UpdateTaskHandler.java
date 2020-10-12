package prv.pgergely.ctsdata.module;

import java.util.Queue;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import prv.pgergely.cts.common.domain.DownloadRequest;
import prv.pgergely.ctsdata.utility.Qualifiers;

@Component
public class UpdateTaskHandler {
	
	@Autowired
	private DataUpdater updater;
	
	@Autowired
	@Qualifier(Qualifiers.FUTURE_STORE)
	private Queue<CompletableFuture<DownloadRequest>> storage;
	
	private Logger logger = LogManager.getLogger(UpdateTaskHandler.class);
	
	public void runUpdateTask(final DownloadRequest req) {
		if(storage.isEmpty()) {
			storage.add(task(req));
		}else {
			CompletableFuture<DownloadRequest> actualUpdate = storage.poll();
			if(actualUpdate.isDone()) {
				storage.add(task(req));
			}else {
				storage.add(actualUpdate);
			}
		}
	}
	
	private CompletableFuture<DownloadRequest> task(final DownloadRequest req){
		return CompletableFuture.completedFuture(req).thenApplyAsync(e -> {
			updater.proceedUpdate(e);
			return e;
		}).whenCompleteAsync((a, thr)->{
			if(thr==null) {
				logger.info("Feed "+a.getFeedId()+" has been successfully updated.");
			}else {
				logger.error(thr.getMessage(), thr);
			}
		});
	}
}
