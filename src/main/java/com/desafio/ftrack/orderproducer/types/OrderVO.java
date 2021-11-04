package com.desafio.ftrack.orderproducer.types;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.desafio.ftrack.orderproducer.types.Order.ProcessingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class OrderVO {

    @NotNull
    @Size(min = 1)
    private String name;

    @NotNull
    @Size(min = 1)
    private String description;

    @NotNull
    @PositiveOrZero
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