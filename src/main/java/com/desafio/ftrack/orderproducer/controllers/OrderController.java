package com.desafio.ftrack.orderproducer.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import com.desafio.ftrack.orderproducer.repositories.OrderRepository;
import com.desafio.ftrack.orderproducer.services.OrderService;
import com.desafio.ftrack.orderproducer.services.ProducerService;
import com.desafio.ftrack.orderproducer.types.Order;
import com.desafio.ftrack.orderproducer.types.OrderVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderRepository orderRepo;

    @Autowired
    ProducerService producer;

    @Autowired 
    OrderService orderSvc;
    
    @GetMapping
    public ResponseEntity<Iterable<Order>> list(){
        return ResponseEntity.ok(orderRepo.findAll());
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody @Valid OrderVO request){
        Order order = orderRepo.save(request.createFromVO());
        producer.send(order);
        return ResponseEntity.created(null).body(order);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> search(@RequestParam(required = false, name = "max_total") Double maxTotal,
                                              @RequestParam(required = false, name = "min_total") Double minTotal,
                                              @RequestParam(required = false, name = "q") String query){
        List<Order> list = orderSvc.search(query, minTotal, maxTotal);
        if(list.size()>0){
            return ResponseEntity.ok(list);
        }else{
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody @Valid OrderVO request){
        Order order = request.createFromVO();
        if(!orderRepo.findById(id).isPresent())
            return ResponseEntity.notFound().build();
        order.setId(id);
        order = orderRepo.save(order);
        producer.send(order);
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
