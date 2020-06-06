package net.wwc.shopapi.repository;

import net.wwc.shopapi.model.Cart;
import net.wwc.shopapi.model.Order;
import net.wwc.shopapi.model.ProductInOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Smita Swami on 3/6/2020.
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, Long>{
    Order findByOrderId(Long orderId);


    List<Order> findAllByOrderStatusAndBuyerIdOrderByCreateTimeDesc(Integer orderStatus ,Long buyerId);
    List<Order> findAllByOrderStatusAndSellerIdOrderByCreateTimeDesc(Integer orderStatus ,Long sellerId);


    Page<Order> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail, Pageable pageable);

    Page<Order> findAllByOrderByOrderStatusAscCreateTimeDesc(Pageable pageable);

    Page<Order> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone, Pageable pageable);
    List<Order> findAllByBuyerIdOrderByOrderStatusAscCreateTimeDesc(Long buyerId);
    List<Order> findAllBySellerIdOrderByOrderStatusAscCreateTimeDesc(Long sellerId);
}

