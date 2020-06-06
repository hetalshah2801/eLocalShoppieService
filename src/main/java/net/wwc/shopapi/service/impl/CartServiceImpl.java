package net.wwc.shopapi.service.impl;

import net.wwc.shopapi.exception.ResourceNotFoundException;
import net.wwc.shopapi.model.Cart;
import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.model.ProductInOrder;
import net.wwc.shopapi.model.User;
import net.wwc.shopapi.repository.CartRepository;
import net.wwc.shopapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl;

    @Override
    public Cart updateProductInCart(Cart cart) {
        Optional<Cart> cartDB = this.cartRepository.findById(cart.getCartId());
        List<ProductInOrder> productInOrderList = null;
        Map<String,ProductInOrder> productMap = new HashMap<>();
        if(cartDB.isPresent()) {
            Cart cartUpdate = cartDB.get();
            cartUpdate.setCartId(cart.getCartId());
            cartUpdate.setBuyerId(cart.getBuyerId());
            cartUpdate.setStatus(cart.getStatus());
            productInOrderList = cartUpdate.getProductInOrder();
            if(productInOrderList == null)
            productInOrderList.addAll(cart.getProductInOrder());
            else {
                productMap = productInOrderList.stream().collect(Collectors.toMap(product -> String.valueOf(product.getProductId())+String.valueOf(product.getSellerId()), product -> product));
                for(ProductInOrder product :cart.getProductInOrder())
                    productMap.put(String.valueOf(product.getProductId())+String.valueOf(product.getSellerId()),product);

                productInOrderList = new ArrayList<>(productMap.values());
            }

            cartUpdate.setProductInOrder(productInOrderList);

            return cartRepository.save(cartUpdate);
        }else {
            cart.setCartId(sequenceGeneratorServiceImpl.generateSequence(Cart.SEQUENCE_NAME));

            return cartRepository.save(cart);
        }

    }

    @Override
    public Map<String,List<ProductInOrder>> getAllCartProduct(Long buyerId) {
        Cart cart = cartRepository.findByBuyerId(buyerId);
        List<ProductInOrder> productList =cart.getProductInOrder();

        if (productList!=null && productList.size()>0) {
            return productList.stream().collect (Collectors.groupingBy(d -> d.getSellerId().toString()));
        }
        else
            throw new ResourceNotFoundException("Cart Empty ");
    }
}
