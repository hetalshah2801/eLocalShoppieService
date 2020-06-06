package net.wwc.shopapi.repository;

import net.wwc.shopapi.model.Cart;
import net.wwc.shopapi.model.ProductInOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Smita Swami on 3/6/2020.
 */
@Repository
public interface CartRepository extends MongoRepository<Cart, Long>{
    Cart findByBuyerId(Long buyerId);
}
