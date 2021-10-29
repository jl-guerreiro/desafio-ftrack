package com.desafio.ftrack.orderproducer.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import com.desafio.ftrack.orderproducer.repositories.OrderRepository;
import com.desafio.ftrack.orderproducer.types.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepo;
    
    @GetMapping
    public ResponseEntity<List<Order>> list(){
        orderRepo.findAll();
        return null;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order request){
        Order order = orderRepo.save(request);
        return ResponseEntity.created(null).body(order);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> search(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id){
        Order order = null;
        try{
            order = orderRepo.findById(id).get();
        }catch(NoSuchElementException nsee){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody Order request){
        request.setId(id);
        if(!orderRepo.findById(id).isPresent())
            return ResponseEntity.notFound().build();
        Order order = orderRepo.save(request);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Order> delete(@PathVariable Long id){
        try{
            orderRepo.delete(orderRepo.findById(id).get());
        }catch(NoSuchElementException nsee){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
