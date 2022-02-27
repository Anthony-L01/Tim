package com.lxc.tim.Config;


import com.lxc.tim.util.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    @SuppressWarnings(value = { "unchecked", "rawtypes" })
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory)
    {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        FastJsonRedisSerializer serializer = new FastJsonRedisSerializer(Object.class);

        // 使用StringRedisSerializer来序列化和反序列化    redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        // 使用FastJsonRedisSerializer来序列化和反序列化    redis的value值
        template.setValueSerializer(serializer);

        // 使用StringRedisSerializer来序列化和反序列化   Hash的key值
        template.setHashKeySerializer(new StringRedisSerializer());
        // 使用FastJsonRedisSerializer来序列化和反序列化    hash的value值
        template.setHashValueSerializer(serializer);


        template.afterPropertiesSet();
        return template;
    }
}