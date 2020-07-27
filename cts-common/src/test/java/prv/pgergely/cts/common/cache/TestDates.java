package prv.pgergely.cts.common.cache;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class TestDates {
	
	private long lifeTime = 1000L;
	
	@Test
	public void checkDate() {
		LocalDateTime now = LocalDateTime.now();
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LocalDateTime cacheStart = LocalDateTime.now();//Instant.ofEpochMilli(100000L).atZone(ZoneId.systemDefault()).toLocalDateTime();
		long res = ChronoUnit.MILLIS.between(now, cacheStart);
		System.out.println(res);
	}
	
}
