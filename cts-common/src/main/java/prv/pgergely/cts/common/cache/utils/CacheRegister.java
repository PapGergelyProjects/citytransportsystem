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

import prv.pgergely.cts.common.cache.Cache;
import prv.pgergely.cts.common.interfaces.ScheduledThreadEngine;

@Service
public class CacheRegister {
	
	private Map<String, Cache<?>> storage = new ConcurrentHashMap<>();
	
	@Autowired
	private ScheduledThreadEngine thread;
	
	@PostConstruct
	public void init() {
		thread.process(0, 5, TimeUnit.SECONDS, "Cache Sweeper", ()->{
			System.out.println("Cache Sweeper has been started");
			storage.forEach((k,v) ->{
				LocalDateTime now = LocalDateTime.now();
				LocalDateTime cacheStart = Instant.ofEpochMilli(v.getStartTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
				long liveTime = ChronoUnit.MILLIS.between(cacheStart, now);
				System.out.println(k+" live time: "+liveTime);
				if(liveTime >= v.getLifeTime()) {
					storage.remove(k);
					System.out.println(k+" removed.");
				}
			});
		});
	}
	
	public <T>void addToCached(String cacheName, Cache<T> element) {
		storage.compute(cacheName, (name, elem) -> {
			if(elem == null) {
				return element;
			} else if(elem.hashCode() != element.hashCode()) {
				return element;
			}
			
			return elem;
		});
	}
	
	public Cache<?> getFromCache(String cacheName) {
		Cache<?> actCache = storage.get(cacheName);
		
		return actCache;
	}
	
	public int getStorageSize() {
		return storage.size();
	}
}
