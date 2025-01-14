package prv.pgergely.ctscountry.configurations;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import prv.pgergely.ctscountry.domain.mobility.gtfs.MobilityGtfsFeed;
import prv.pgergely.ctscountry.domain.mobility.token.AuthToken;

@Configuration
public class CacheConfig implements CacheManagerCustomizer<ConcurrentMapCacheManager> {
	
	@Autowired
	@Qualifier(RedisConfig.REDIS_TEMPLATE)
	private RedisTemplate<String, String> redisTmp;
	
	@Override
	public void customize(ConcurrentMapCacheManager cacheManager) {
		cacheManager.setCacheNames(Arrays.asList("feeds"));
	}
	
	@Bean
	public RedisCacheManagerBuilderCustomizer custom() {
		redisTmp.getRequiredConnectionFactory().getConnection().commands().flushDb();
		return builder -> builder.withCacheConfiguration("gtfs-feeds", gtfsListCacheConfig());
	}
	
	private RedisCacheConfiguration gtfsListCacheConfig() {
		JavaType gtfsType = new ObjectMapper().getTypeFactory().constructType(new TypeReference<List<MobilityGtfsFeed>>() {});
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1L)).serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(gtfsType))
		);
	}
	
	private RedisCacheConfiguration authTokenCacheConfig() { // For future use
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1L)).serializeValuesWith(
				RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(AuthToken.class))
		);
	}
	
}
