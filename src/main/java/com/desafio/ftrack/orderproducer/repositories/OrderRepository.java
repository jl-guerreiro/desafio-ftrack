package com.desafio.ftrack.orderproducer.repositories;

import com.desafio.ftrack.orderproducer.types.Order;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {

    
}
