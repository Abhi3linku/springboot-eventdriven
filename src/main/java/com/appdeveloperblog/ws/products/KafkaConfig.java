/**
 * 
 */
package com.appdeveloperblog.ws.products;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * 
 */
@Configuration
public class KafkaConfig {
	@Bean
	NewTopic createTopic() {
		return TopicBuilder.name("product-created-event-topic")
				.replicas(1)
				.partitions(1)
				.configs(Map.of("min.insync.replicas","1"))
				.build();
		
	}

	
	
}
