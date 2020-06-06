package net.wwc.shopapi.service.impl;



import net.wwc.shopapi.enums.OrderStatusEnum;
import net.wwc.shopapi.enums.ResultEnum;
import net.wwc.shopapi.exception.CustomException;
import net.wwc.shopapi.model.Order;
import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.model.ProductInOrder;
import net.wwc.shopapi.model.User;
import net.wwc.shopapi.repository.OrderRepository;
import net.wwc.shopapi.repository.ProductRepository;
import net.wwc.shopapi.repository.UserRepository;
import net.wwc.shopapi.service.OrderService;
import net.wwc.shopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

/**
 * Created By Zhu Lin on 3/14/2018.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    private SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl;
    @Autowired
    private MongoOperations mongoOperations;


    @Override
    public Order Save(Order order) {
    List<Long> userIds = new ArrayList<>();
        userIds.add(order.getBuyerId());
        userIds.add(order.getSellerId());
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(userIds));
        List<User> userList = mongoOperations.find(query,User.class);
        for(User user : userList) {
            if (user.getId() == order.getBuyerId())
            {
                order.setBuyerName(user.getName());
                order.setBuyerEmail(user.getEmail());
                order.setBuyerPhone(user.getPhone());
                order.setBuyerAddress(user.getDeliveryAddress());
            }else
            {
                order.setSellerName(user.getName());
                order.setSellerEmail(user.getEmail());
                order.setBuyerPhone(user.getPhone());
                order.setSellerAddress(user.getShopAddress());
                order.setShopName(user.getShopName());
            }
        }
        order.setOrderId(sequenceGeneratorServiceImpl.generateSequence(Order.SEQUENCE_NAME));
        return orderRepository.save(order);
    }
    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }


    @Override
    public Page<Order> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }

    @Override
    public Page<Order> findByBuyerPhone(String phone, Pageable pageable) {
        return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
    }
    @Override
    public List<Order> getBuyerOrders(Long buyerId) {
        return orderRepository.findAllByBuyerIdOrderByOrderStatusAscCreateTimeDesc(buyerId);
    }
    @Override
    public List<Order> getSellerOrders(Long sellerId) {
        return orderRepository.findAllBySellerIdOrderByOrderStatusAscCreateTimeDesc(sellerId);
    }
    @Override
    public List<Order> getBuyerOrdersByStatus(Integer orderStatus, Long buyerId)
    {
        return orderRepository.findAllByOrderStatusAndBuyerIdOrderByCreateTimeDesc(orderStatus,buyerId);
    }
    @Override
    public List<Order> getSellerOrdersByStatus(Integer orderStatus, Long sellerId){
        return orderRepository.findAllByOrderStatusAndSellerIdOrderByCreateTimeDesc(orderStatus,sellerId);
    }


    @Override
    public Order findByOrderId(Long orderId) {
        Order orderMain = orderRepository.findByOrderId(orderId);
        if(orderMain == null) {
            throw new CustomException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }


    @Override
    @Transactional
    public Order finish(Long orderId) {
        Order orderMain = findByOrderId(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new CustomException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    @Transactional
    public Order cancel(Long orderId) {
        Order orderMain = findByOrderId(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new CustomException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // Restore Stock
        Iterable<ProductInOrder> products = orderMain.getProducts();
        for(ProductInOrder productInOrder : products) {
            Product productInfo = productRepository.findByProductId(productInOrder.getProductId());
            if(productInfo != null) {
                productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
            }
        }
        return orderRepository.findByOrderId(orderId);

    }
}
