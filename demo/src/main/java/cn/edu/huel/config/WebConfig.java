package cn.edu.huel.config;

import cn.edu.huel.component.FastjsonRedisSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.http.converter.HttpMessageConverter;

import java.nio.charset.StandardCharsets;

/**
 * @author 张晓华
 * @date 2023-2-16
 */
@Configuration
public class WebConfig {


	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		FastjsonRedisSerializer<Object> serializer = new FastjsonRedisSerializer(Object.class);
		RedisSerializer<String> stringRedisSerializer = RedisSerializer.string();
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(serializer);
		redisTemplate.setHashValueSerializer(serializer);
		return redisTemplate;
	}


	@Bean
	public HttpMessageConverter httpMessageConverter() {
		FastJsonHttpMessageConverter messageConverter = new FastJsonHttpMessageConverter();
		FastJsonConfig config = new FastJsonConfig();
		config.setCharset(StandardCharsets.UTF_8);
		config.setDateFormat("yyyy-MM-dd HH:mm:ss");
		config.setSerializerFeatures(SerializerFeature.NotWriteRootClassName, SerializerFeature.WriteDateUseDateFormat);
		messageConverter.setFastJsonConfig(config);
		return messageConverter;
	}


}
