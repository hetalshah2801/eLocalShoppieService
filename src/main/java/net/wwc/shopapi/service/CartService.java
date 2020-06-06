package net.wwc.shopapi.service;

import net.wwc.shopapi.model.Cart;
import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.model.ProductInOrder;

import java.util.List;
import java.util.Map;

public interface CartService {
    Cart updateProductInCart(Cart cart);
    Map<String,List<ProductInOrder>> getAllCartProduct(Long buyerId);
}
