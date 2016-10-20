package com.jaf.examples.kafka.base;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringEncoder;

/**
 * TODO
 * 
 * @author liaozhicheng.cn@163.com
 * @date 2016年10月15日
 * @since 1.0
 */
public class ProducerDemo {
	
	
	public static void main(String[] args) throws InterruptedException {
		Producer<String, String> producer = buildProducer();
		
		List<Thread> produceThreads = IntStream.range(0, 5).mapToObj(i -> {
			return new Thread(() -> {
				sendMessage(producer, Constants.TOPIC_NAME, i + "", i + " message");
			});
		}).peek(Thread::start).collect(toList());
		
		produceThreads.stream().forEach(t -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		producer.close();
	}
	
	private static Producer<String, String> buildProducer() {
		Properties props = new Properties();
		props.put("metadata.broker.list", Constants.BROKER_LIST);
		props.put("serializer.class", StringEncoder.class.getName());
		props.put("partitioner.class", RandomPartitioner.class.getName());
		props.put("producer.type", "async");
		props.put("batch.num.messages", "3");
		props.put("queue.buffer.max.ms", "10000000");
		props.put("queue.buffering.max.messages", "1000000");
		props.put("queue.enqueue.timeout.ms", "20000000");
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> produce = new Producer<>(config);
		return produce;
	}
	
	private static void sendMessage(Producer<String, String> producer, 
			String topic, String key, String message) {
		KeyedMessage<String, String> message1 = new KeyedMessage<String, String>(topic, key, message);
	    producer.send(message1);
	}
	
}
