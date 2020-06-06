package net.wwc.shopapi.repository;

import net.wwc.shopapi.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.wwc.shopapi.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.spi.LocaleNameProvider;

/**
 * Created By Smita Swami on 3/6/2020.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, Long>{
   // List<Product> findAllByUserIdAndCategoryTypeOrderByProductIdAsc(Long userId, String categoryType);
    List<Product> findBySellerUserId(Long sellerUserId);
  // List<Product> findBySellerUserIdGroupByCategoryType(Long sellerUserId, String categoryType);
    Product findBySellerUserIdAndAndProductName(Long sellerUserId, String productName);

    Product findByProductId(Long productId);
    List<Product> getAllProductBySellerUserId(Long sellerUserId);
}
