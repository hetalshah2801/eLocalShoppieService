package net.wwc.shopapi.controller;

import net.wwc.shopapi.model.Cart;
import net.wwc.shopapi.model.ProductInOrder;
import net.wwc.shopapi.service.CartService;
import net.wwc.shopapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/carts/{buyerId}")
    public ResponseEntity<Map<String,List<ProductInOrder>>> getAllCartProduct(@PathVariable Long buyerId){
        return ResponseEntity.ok().body(this.cartService.getAllCartProduct(buyerId));
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> saveCart(@RequestBody Cart cart){
        return ResponseEntity.ok().body(this.cartService.updateProductInCart(cart));
    }

}
