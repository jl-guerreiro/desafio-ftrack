package com.desafio.ftrack.orderproducer.configurations;

import java.util.HashMap;
import java.util.Map;

import com.desafio.ftrack.orderproducer.types.Order;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {
    
    @Value(value = "${app.kafka.host}")
    private String kafkaHost;

    @Value(value = "${app.kafka.topic}")
    private String topicName;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> conf = new HashMap<>();
        conf.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        return new KafkaAdmin(conf);
    }

    @Bean
    public NewTopic kafkaTopic(){
        return new NewTopic(topicName, 1, (short) 1 );
    }

    @Bean
    public ProducerFactory<String, Order> producer(){
        Map<String,Object> conf = new HashMap<>();
        conf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(conf);
    }

    @Bean
    public KafkaTemplate<String,Order> template(){
        return new KafkaTemplate<>(producer());
    }


}
