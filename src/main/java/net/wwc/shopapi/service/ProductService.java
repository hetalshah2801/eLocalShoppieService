package net.wwc.shopapi.service;


import java.util.List;
import java.util.Map;

import net.wwc.shopapi.model.Product;
import org.springframework.data.domain.Pageable;

/**
 * Created By Smita Swami on 3/6/2020.
 */
public interface ProductService {
	Product createProduct(Product product);

	Product updateProduct(Product product);

	Map<String ,List<Product>>  getAllProductByShop(Long sellerUseId);

	Product getProductById(long productId);

	void deleteProduct(long id);
	void increaseStock(Long productId, int amount);

	//decrease stock
	void decreaseStock(Long productId, int amount);

	Product offSale(Long productId);

	Product onSale(Long productId);
	List<Product> productOutOfStock(Long sellerUserId) ;
}
