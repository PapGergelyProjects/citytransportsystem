package prv.pgergely.cts.common.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import prv.pgergely.cts.common.cache.utils.CacheRegister;
import prv.pgergely.cts.common.threads.ScheduledThreadPool;

public class TestCacheRegister {
	
	private CacheRegister register;
	
	@BeforeEach
	public void initialize() {
		register = new CacheRegister(new ScheduledThreadPool());
		register.init();
		register.<String>addToShortLiveCache("first", "FirstItem");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		register.<String>addToShortLiveCache("second", "SecondItem");
	}
	
	@Test
	@DisplayName("Test 1: check sweeper")
	public void test1() {
		int size = register.getStorageSize();
		while(0<size) {
			System.out.println(size+" waiting...");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			size = register.getStorageSize();
		}
		Assert.assertEquals(size, 0);
	}
	
	@Test
	@DisplayName("Test 2: check retrieve from cache")
	public void test2() {
		String val = register.getFromCache("second", String.class);
		Assert.assertEquals(val, "SecondItem");
	}
}
