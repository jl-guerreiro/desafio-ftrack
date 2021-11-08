package com.desafio.ftrack.orderproducer.repositories;

import java.util.List;

import com.desafio.ftrack.orderproducer.types.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order,Long> {

    List<Order> findByTotalBetween(Double minTotal, Double maxTotal);

    @Query("SELECT o FROM Order o WHERE (o.name like %:query% or o.description like %:query%) and o.total >= :minPrice and o.total <= :maxPrice") 
    List<Order> findByNameOrDescriptionContainsAndTotalBetween(String query, Double minPrice, Double maxPrice);

    
}
