package com.desafio.ftrack.orderproducer.services;

import com.desafio.ftrack.orderproducer.types.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    
    private static final Logger log = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    private KafkaTemplate<String,Order> kafka;

    @Value(value = "${app.kafka.topic}")
    private String topicName;

    public void send(Order order){
        log.info("KAFKA - SEND MSG: {}",this.toJson(order));
        kafka.send(topicName, order);
    }

    private String toJson(Order order) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
