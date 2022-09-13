package it.angelo.MyCartellaClinicaElettronica.order.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.angelo.MyCartellaClinicaElettronica.order.entities.Order;
import it.angelo.MyCartellaClinicaElettronica.order.entities.OrderDTO;
import it.angelo.MyCartellaClinicaElettronica.order.repositories.OrdersRepository;
import it.angelo.MyCartellaClinicaElettronica.order.services.OrderService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REGISTERED')") //solo un user registrato può creare un ordine
    @ApiOperation(value = "Create orders", notes = "Create an order by passing it a body with all the necessary data and save it in the DB")
    public ResponseEntity<Order> create(@ApiParam(value = "The requested parameter is the body of the order which corresponds to the OrderDTO data")@RequestBody OrderDTO order) throws Exception{
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get single orders by id", notes = "Return a specific order based on the past id")
    public ResponseEntity<Order> getSingle(@ApiParam(value = "The requested parameter is a Long type data which is equivalent to the order id")@PathVariable Long id, @ApiParam(value = "The parameter is a User entity")Principal principal){

        Optional<Order> order = ordersRepository.findById(id);
        if (!order.isPresent())return ResponseEntity.notFound().build();

        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(Roles.hasRole(user, Roles.REGISTERED) && order.get().getCreatedBy().getId() == user.getId()){
            return ResponseEntity.ok(order.get());

        }else if(Roles.hasRole(user, Roles.RESTAURANT) && order.get().getRestaurant().getId() == user.getId()) {
            return ResponseEntity.ok(order.get());

        }else if(Roles.hasRole(user, Roles.RIDER) && order.get().getRider().getId() == user.getId()){
            return ResponseEntity.ok(order.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @ApiOperation(value = "Get all orders", notes = "Returns a list containing all orders")
    public ResponseEntity<List<Order>> getAll(@ApiParam(value = "The parameter is a User entity")Principal principal){
        User user = (User)((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if(Roles.hasRole(user, Roles.REGISTERED)){
            return ResponseEntity.ok(ordersRepository.findByCreatedBy(user));
        } else if (Roles.hasRole(user, Roles.RESTAURANT)) {
            return ResponseEntity.ok(ordersRepository.findByRestaurant(user));
        } else if (Roles.hasRole(user, Roles.RIDER)){
            return ResponseEntity.ok(ordersRepository.findByRider(user));
        }
        return null;
    }
}
