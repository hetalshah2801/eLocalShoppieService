package net.wwc.shopapi.controller;

import net.wwc.shopapi.model.Order;
import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order order){
        return ResponseEntity.ok().body(this.orderService.Save(order));
    }

    @GetMapping("/orders/getBuyerOrders/{buyerId}")
    public ResponseEntity<List<Order>> getBuyerOrders(@PathVariable Long  buyerId){
        return ResponseEntity.ok().body(this.orderService.getBuyerOrders(buyerId));
    }

    @GetMapping("/orders/getSellerOrders/{sellerId}")
    public ResponseEntity<List<Order>> getSellerOrders(@PathVariable Long  sellerId){
        return ResponseEntity.ok().body(this.orderService.getSellerOrders(sellerId));
    }
    @GetMapping("/orders/getBuyerOrdersByStatus/{buyerId}/{orderStatus}")
    public ResponseEntity<List<Order>> getBuyerOrdersByStatus(@PathVariable Long  buyerId, @PathVariable Integer orderStatus){
        return ResponseEntity.ok().body(this.orderService.getBuyerOrdersByStatus(orderStatus, buyerId));
    }

    @GetMapping("/orders/getSellerOrdersByStatus/{sellerId}/{orderStatus}")
    public ResponseEntity<List<Order>> getSellerOrdersByStatus(@PathVariable Long  sellerId,  @PathVariable Integer orderStatus){
        return ResponseEntity.ok().body(this.orderService.getSellerOrdersByStatus(orderStatus ,sellerId));
    }
}

