package com.example.restdemo.model.mapper;

import com.example.restdemo.controller.OrderController;
import com.example.restdemo.model.Order;
import com.example.restdemo.model.Status;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<Order,
        EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order order) {
        //unconditional links to single-item resource and aggregate root
        EntityModel<Order> orderModel = EntityModel.of(order,
                linkTo(methodOn(OrderController.class)
                        .one(order.getId()))
                        .withSelfRel(),
                linkTo(methodOn(OrderController.class)
                        .all())
                        .withRel("orders"));

        //conditional link based on the state of the order
        if (order.getStatus() == Status.IN_PROGRESS) {
            orderModel.add(
                    linkTo(methodOn(OrderController.class)
                            .cancel(order.getId())).withRel("cancel"));

            orderModel.add(
                    linkTo(methodOn(OrderController.class)
                            .complete(order.getId())).withRel("complete"));
        }
        return orderModel;
    }
}
