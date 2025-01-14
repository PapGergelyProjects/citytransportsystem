package prv.pgergely.ctscountry.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	
	public static final String REDIS_TEMPLATE = "default_redis_template";
	
	@Bean(REDIS_TEMPLATE)
	public RedisTemplate<String, String> createTemplate(){
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
		connectionFactory.afterPropertiesSet();
		
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setDefaultSerializer(StringRedisSerializer.UTF_8);
		template.afterPropertiesSet();
		
		return template;
	}
}
