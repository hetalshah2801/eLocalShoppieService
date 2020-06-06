package net.wwc.shopapi.service;



import net.wwc.shopapi.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created By Zhu Lin on 3/14/2018.
 */

public interface OrderService {
    Page<Order> findAll(Pageable pageable);



    Page<Order> findByBuyerEmail(String email, Pageable pageable);

    Page<Order> findByBuyerPhone(String phone, Pageable pageable);
    List<Order> getBuyerOrders(Long buyerId);
    List<Order> getSellerOrders(Long sellerId);
    List<Order> getBuyerOrdersByStatus(Integer orderStatus, Long buyerId);
    List<Order> getSellerOrdersByStatus(Integer orderStatus, Long sellerId);

    Order findByOrderId(Long orderId);
    Order Save(Order order);


    Order finish(Long orderId);

    Order cancel(Long orderId);

}
