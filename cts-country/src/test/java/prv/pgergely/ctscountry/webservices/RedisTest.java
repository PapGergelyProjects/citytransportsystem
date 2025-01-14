package prv.pgergely.ctscountry.webservices;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import prv.pgergely.ctscountry.configurations.ApplicationCtsCountry;
import prv.pgergely.ctscountry.configurations.RedisConfig;
import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.services.MobilityApi;

@SpringBootTest(classes = ApplicationCtsCountry.class,  webEnvironment = WebEnvironment.RANDOM_PORT)
public class RedisTest {
	
	@Autowired
	@Qualifier(RedisConfig.REDIS_TEMPLATE)
	private RedisTemplate<String, String> redisTmp;
	
	@Autowired
	private MobilityApi mobApi;
	
	@Test
	@Order(1)
	@DisplayName("1. Test redis add and retrieve")
	public void testValuAddRetrieve() {
		redisTmp.opsForValue().set("data1", "first_data");
		assertEquals("first_data", redisTmp.opsForValue().get("data1"));
	}
	
	@Test
	@Order(2)
	@DisplayName("1. Test redis cache")
	public void testRedisCache() {
		mobApi.getAllGtfsFeeds("HU");
		Set<String> cacheKeySub = redisTmp.keys("gtfs-feeds*");
		System.out.println(">>>>>>>>>> cacheKeySub: "+cacheKeySub);
		String val = redisTmp.opsForValue().get("gtfs-feeds::HU");
		List<MobilityGtfsFeed> redisRawValue = getFromJson(val);
		assertTrue(!redisRawValue.isEmpty());
		List<MobilityGtfsFeed> fromCache = mobApi.getAllGtfsFeeds("HU");
		fromCache.retainAll(redisRawValue);
		assertTrue(fromCache.isEmpty());
	}
	
	private List<MobilityGtfsFeed> getFromJson(String val){
		try {
			JavaType gtfsType = new ObjectMapper().getTypeFactory().constructType(new TypeReference<List<MobilityGtfsFeed>>() {});
			return new ObjectMapper().readValue(val, gtfsType);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return List.of();
		}
	}
}
