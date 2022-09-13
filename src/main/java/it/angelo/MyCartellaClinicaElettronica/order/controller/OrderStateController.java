package it.angelo.MyCartellaClinicaElettronica.order.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.angelo.MyCartellaClinicaElettronica.order.entities.Order;
import it.angelo.MyCartellaClinicaElettronica.order.repositories.OrdersRepository;
import it.angelo.MyCartellaClinicaElettronica.order.services.OrderStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/orders/{id}/state")
public class OrderStateController {

    @Autowired
    private OrderStateService orderStateService;

    @Autowired
    private OrdersRepository ordersRepository;


    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/accepted")
    @ApiOperation(value = "Order status accepted", notes = "Change the status of the current order to accepted")
    public ResponseEntity accepted(@ApiParam(value = "The parameter is a long type data which is equivalent to the order id")@PathVariable long id) throws Exception{
        Optional<Order> order = ordersRepository.findById(id);
        if(!order.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setAccept(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/in-preparation")
    @ApiOperation(value = "Order status in preparation", notes = "Change the status of the current order to in preparation")
    public ResponseEntity inPreparation(@ApiParam(value = "The parameter is a long type data which is equivalent to the order id")@PathVariable long id) throws Exception{
        Optional<Order> order = ordersRepository.findById(id);
        if(!order.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setInPreparation(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RESTAURANT')")
    @PutMapping("/ready")
    @ApiOperation(value = "Order status ready", notes = "Change the status of the current order to ready")
    public ResponseEntity ready(@ApiParam(value = "The parameter is a long type data which is equivalent to the order id")@PathVariable long id) throws Exception{
        Optional<Order> order = ordersRepository.findById(id);
        if(!order.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setReady(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RIDER')")
    @PutMapping("/delivering")
    @ApiOperation(value = "Order status delivering", notes = "Change the status of the current order to delivering")
    public ResponseEntity delivering(@ApiParam(value = "The parameter is a long type data which is equivalent to the order id")@PathVariable long id) throws Exception{
        Optional<Order> order = ordersRepository.findById(id);
        if(!order.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setDelivery(order.get()));
    }

    @PreAuthorize("hasRole('ROLE_RIDER')")
    @PutMapping("/complete")
    @ApiOperation(value = "Order status complete", notes = "Change the status of the current order to complete")
    public ResponseEntity complete(@ApiParam(value = "The parameter is a long type data which is equivalent to the order id")@PathVariable long id) throws Exception{
        Optional<Order> order = ordersRepository.findById(id);
        if(!order.isPresent())return ResponseEntity.notFound().build();
        return ResponseEntity.ok(orderStateService.setComplete(order.get()));
    }

}
