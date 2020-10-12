package prv.pgergely.cts.common.cache.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prv.pgergely.cts.common.cache.model.Cache;
import prv.pgergely.cts.common.cache.model.ShortLiveCacheObject;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;

@Service
public class CacheRegister {
	
	private Map<String, Cache<?>> storage = new ConcurrentHashMap<>();
	
	@Autowired
	private ScheduledThreadEngine thread;
	
	public CacheRegister() {}
	
	public CacheRegister(ScheduledThreadEngine threadEngine) {
		this.thread = threadEngine;
	}
	
	@PostConstruct
	public void init() {
		thread.process(0, 60, TimeUnit.SECONDS, "Cache Sweeper", ()->{
			System.out.println("Cache Sweeper has been started");
			storage.forEach((k,v) ->{
				if(!v.isConstant()) {
					long start = v.startTime();
					LocalDateTime cacheStart = Instant.ofEpochMilli(start).atZone(ZoneId.systemDefault()).toLocalDateTime();
					long liveTime = ChronoUnit.MILLIS.between(cacheStart, LocalDateTime.now());
					System.out.println(k+" live time: "+liveTime);
					if(liveTime >= v.aliveTime()) {
						storage.remove(k);
						System.out.println(k+" removed.");
					}
				} else {
					
				}
			});
		});
	}
	
	
	public <T> void addToShortLiveCache(String name, T object) {
		addToCached(name, new ShortLiveCacheObject<T>(object));
	}
	
	private <T>void addToCached(String cacheName, Cache<T> element) {
		storage.compute(cacheName, (name, elem) -> {
			if(elem == null) {
				return element;
			} else if(elem.hashCode() != element.hashCode()) {
				return element;
			}
			
			return elem;
		});
	}
	
	public <T> T getFromCache(String name, Class<T> clazz) {
		Cache<?> actCache = storage.get(name);
		
		return clazz.cast(actCache.store());
	}
	
	public int getStorageSize() {
		return storage.size();
	}
}
