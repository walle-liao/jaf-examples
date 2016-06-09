package com.jaf.examples.redis.jedis.spring;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年3月11日
 * @since 1.0
 */
public class StringTypeTests extends JedisSpringBaseTests {

	@Test
	public void testSetAndGet() {
		String value = "testValue";
		redisTemplate.opsForValue().set("testSetAndGet", value);
		assertThat(redisTemplate.opsForValue().get("testSetAndGet")).isEqualTo(value);
	}
	
	@Test
	public void testSetNx() {
		assertThat(redisTemplate.opsForValue().setIfAbsent("testSetNx", "testValue")).isTrue();
		assertThat(redisTemplate.opsForValue().setIfAbsent("testSetNx", "testValueNew")).isFalse();
		
		assertThat(redisTemplate.opsForValue().get("testSetNx")).isEqualTo("testValue");
	}
	
	@Test
	public void testTimeOut() throws InterruptedException {
		redisTemplate.opsForValue().set("testTimeOut", "testTimeOutValue", 500, TimeUnit.MILLISECONDS);
		assertThat(redisTemplate.opsForValue().get("testTimeOut")).isEqualTo("testTimeOutValue");
		
		Thread.sleep(510);
		
		assertThat(redisTemplate.opsForValue().get("testTimeOut")).isNull();
	}
	
	@Test
	public void testUseTransaction() throws InterruptedException {
		final String watchKey = "watchKey";
		
		redisTemplate.execute(new SessionCallback<List<Object>>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public List<Object> execute(RedisOperations operations) 
					throws DataAccessException {
				operations.watch(watchKey);
				
				operations.multi();
				operations.opsForValue().set("test", "testValue");
				operations.opsForSet().add("testSet", "test1", "test2");
				
				// 这里获取 test 为 null，因为事务还没有提交
				assertThat(operations.opsForValue().get("test")).isNull();
				return operations.exec();
			}
		});
		
		assertThat(redisTemplate.opsForValue().get("test")).isEqualTo("testValue");
		MatcherAssert.assertThat(redisTemplate.opsForSet().members("testSet"), Matchers.contains("test1", "test2"));
	}
	
	@Test
	public void testUsePipelined() {
		redisTemplate.opsForValue().set("testUsePipelined", "testValue");
		redisTemplate.opsForSet().add("testUsePipelinedSet", "test1", "test2");
		
		MatcherAssert.assertThat(redisTemplate.opsForSet().members("testUsePipelinedSet"), 
				Matchers.contains("test1", "test2"));
		
		// 获取系统默认采用 key 的序列化工具类
		@SuppressWarnings("unchecked")
		final RedisSerializer<String> serializer = (RedisSerializer<String>) redisTemplate.getKeySerializer();
		redisTemplate.executePipelined(new RedisCallback<Object>() {
			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.openPipeline();
				
				// 注意这里如果直接使用 "test".getBytes() 将无法正常删除掉 key，因为 key 的序列化方式不一样
				// connection.del("test".getBytes());
				connection.del(serializer.serialize("testUsePipelined"));
				connection.del(serializer.serialize("testUsePipelinedSet"));
				connection.closePipeline();
				return null;
			}
		});
		
		assertThat(redisTemplate.opsForValue().get("testUsePipelined")).isNull();
	}
	
}
