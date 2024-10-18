package com.appdeveloperblog.ws.products.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import com.appdeveloperblog.ws.products.rest.CreateProductRestModel;



@Service
public class ProductServiceImpl implements ProductService {
	
	KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	
	public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		
	}
	
	
	/**
	 *
	 */
	@Override
	public String createProduct(CreateProductRestModel productRestModel) throws Exception {
		
		String productId = UUID.randomUUID().toString();
		
		//TODO: To persists product details into database table before publishing an event 
		ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId,
				productRestModel.getTitle(), productRestModel.getPrice(),
				productRestModel.getQuantity());
		LOGGER.info("Before publishing a product Created Event");
		
		
	      SendResult<String, ProductCreatedEvent> result = 
	    		  kafkaTemplate.send("product-created-event-topic", productId, productCreatedEvent).get();
// for async      
//	      future.whenComplete((result, exception) -> {
//	    	  if(exception != null) {
//	    		  LOGGER.error("Failed to send message " + exception.getMessage());
//	    		     		  	    		  
//	    	  }else {
//	    		  LOGGER.info("Message sent successfully. Topic: {}, Partition: {}, Offset: {}",
//	    		            result.getRecordMetadata());
//	    		    }
//	    		  
//	    	 
//	      });
	      LOGGER.info("Partition: "+ result.getRecordMetadata().partition());
	      LOGGER.info("TopicName: "+ result.getRecordMetadata().topic());
	      LOGGER.info("Offset: "+ result.getRecordMetadata().offset());
	      // future.join(); 
	     LOGGER.info("***********************Returning productId*****************" );
					
		return productId;
	}
	

}
