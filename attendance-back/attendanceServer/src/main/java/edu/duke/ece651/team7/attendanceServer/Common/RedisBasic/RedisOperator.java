package edu.duke.ece651.team7.attendanceServer.Common.RedisBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisOperator {
    
    @Autowired
	private StringRedisTemplate redisTemplate;

    public void set(String key, String value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	public String get(String key) {
		return (String)redisTemplate.opsForValue().get(key);
	}

}
