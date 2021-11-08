package com.desafio.ftrack.orderproducer.services;

import java.util.List;

import com.desafio.ftrack.orderproducer.repositories.OrderRepository;
import com.desafio.ftrack.orderproducer.types.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepo;


    public List<Order> search(String query, Double minPrice, Double maxPrice){
        if(minPrice==null || minPrice<0) minPrice=0d;
        if(maxPrice==null || maxPrice<0) maxPrice=Double.MAX_VALUE;
        log.debug("Q: {} - MIN: {} - MAX: {}", query , minPrice, maxPrice);

        if(query.trim().equals("")){
            return orderRepo.findByTotalBetween(minPrice, maxPrice);
        }else{
            try{
                return orderRepo.findByNameOrDescriptionContainsAndTotalBetween(query,minPrice,maxPrice);
            }catch(Exception e){
                System.err.println(e);
                return null;
            }
        }
    }
    
}
