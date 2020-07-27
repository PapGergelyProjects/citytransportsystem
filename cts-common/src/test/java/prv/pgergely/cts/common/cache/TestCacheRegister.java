package prv.pgergely.cts.common.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import prv.pgergely.cts.common.CommonComponents;
import prv.pgergely.cts.common.cache.utils.CacheRegister;
import prv.pgergely.cts.common.threads.ScheduledThreadPool;

@SpringBootTest(classes= CommonComponents.class)
@Import(value= {CacheRegister.class, ScheduledThreadPool.class})
public class TestCacheRegister {
	
	@Autowired
	private CacheRegister register;
	
	@Before
	public void init() {
		Cache<String> cache1 = new CacheObject("FistItem");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Cache<String> cache2 = new CacheObject("SecondItem");
		register.<String>addToCached("first", cache1);
		register.<String>addToCached("second", cache2);
	}
	
	@Test
	@DisplayName("Test 1: check sweeper")
	public void test1() {
		int size = register.getStorageSize();
		while(0<size) {
			System.out.println(size);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			size = register.getStorageSize();
		}
		Assert.assertEquals(size, 0);
	}
}
