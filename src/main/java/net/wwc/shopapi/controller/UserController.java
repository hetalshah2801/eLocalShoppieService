package net.wwc.shopapi.controller;

import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.model.ProductInOrder;
import net.wwc.shopapi.model.User;
import net.wwc.shopapi.model.UserLogin;
import net.wwc.shopapi.service.ProductService;
import net.wwc.shopapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;



    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.save(user));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return ResponseEntity.ok().body(this.userService.update(user));
    }

    @PostMapping("/loginUser")
    public ResponseEntity<User> loginUser(@RequestBody UserLogin user){
        return ResponseEntity.ok().body(this.userService.verifyUser(user));
    }

    @GetMapping("/getShopsByUserDetails/{businessDomain}/{buyerPinCode}")
    public ResponseEntity<List<User>> getShopsByUserDetails(@PathVariable String businessDomain, @PathVariable String buyerPinCode){
        return ResponseEntity.ok().body(userService.getShopsByUserDetails(businessDomain, buyerPinCode));
    }
}
