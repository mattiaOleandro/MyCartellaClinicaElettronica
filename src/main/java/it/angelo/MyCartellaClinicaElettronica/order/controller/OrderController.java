package it.angelo.MyCartellaClinicaElettronica.order.controller;

import it.angelo.MyCartellaClinicaElettronica.notification.MailNotificationService;
import it.angelo.MyCartellaClinicaElettronica.order.entities.Order;
import it.angelo.MyCartellaClinicaElettronica.order.entities.OrderDTO;
import it.angelo.MyCartellaClinicaElettronica.order.repositories.OrdersRepository;
import it.angelo.MyCartellaClinicaElettronica.order.services.OrderService;
import it.angelo.MyCartellaClinicaElettronica.user.entities.User;
import it.angelo.MyCartellaClinicaElettronica.user.utils.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(OrderController.class);
    int lineGetter = new Exception().getStackTrace()[0].getLineNumber();

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REGISTERED')") //solo un user registrato può creare un ordine
    public ResponseEntity<Order> create(@RequestBody OrderDTO order) throws Exception{
        logger.info("PostMapped method 'create' method called at "+ OrderController.class +" at line#" + lineGetter);
        return ResponseEntity.ok(orderService.save(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getSingle(@PathVariable Long id, Principal principal){
        logger.info("GetMapped method 'getSingle' method called at "+ OrderController.class +" at line#" + lineGetter);

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
    public ResponseEntity<List<Order>> getAll(Principal principal){
        logger.info("GetMapped method 'getAll' method called at "+ OrderController.class +" at line#" + lineGetter);
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
