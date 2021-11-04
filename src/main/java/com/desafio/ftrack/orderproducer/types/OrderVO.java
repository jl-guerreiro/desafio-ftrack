package com.desafio.ftrack.orderproducer.types;

import com.desafio.ftrack.orderproducer.types.Order.ProcessingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderVO {

    private String name;
    private String description;
    private double total;
    private String status;

    public Order createFromVO(){
        Order order = new Order();
        order.setName(this.name);
        order.setDescription(this.description);
        order.setTotal(this.total);
        order.setStatus(ProcessingStatus.NOT_PROCESSED);
        return order;
    }

    public OrderVO(Order order){
        this.name = order.getName();
        this.description = order.getDescription();
        this.total = order.getTotal();
        this.status = order.getStatus().toString();
    }

}