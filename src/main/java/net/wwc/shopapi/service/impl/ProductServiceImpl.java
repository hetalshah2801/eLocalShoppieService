package net.wwc.shopapi.service.impl;

import net.wwc.shopapi.enums.ProductStatusEnum;
import net.wwc.shopapi.enums.ResultEnum;
import net.wwc.shopapi.exception.CustomException;
import net.wwc.shopapi.exception.ResourceNotFoundException;
import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.model.User;
import net.wwc.shopapi.repository.ProductRepository;
import net.wwc.shopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created By Smita Swami on 3/6/2020.
 */
@Service
@Transactional
@Configuration
public class ProductServiceImpl implements ProductService {

	@Value("${min.product.quantity}")
	private int minProductQuantity;
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	@Override
	public Product createProduct(Product product) {
		Product existingProductDb = this.productRepository.findBySellerUserIdAndAndProductName(product.getSellerUserId(),product.getProductName());
		if (existingProductDb !=null)
			throw new CustomException(ResultEnum.PRODUCT_ALREADY_EXIST);
		product.setProductId(sequenceGeneratorServiceImpl.generateSequence(Product.SEQUENCE_NAME));
		product.setCreateTime(new Date());
		product.setUpdateTime(new Date());
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Product product) {
		Optional<Product> productDb = this.productRepository.findById(product.getProductId());
		
		if(productDb.isPresent()) {
			Product productUpdate = productDb.get();
			productUpdate.setProductId(product.getProductId());
			productUpdate.setProductName(product.getProductName());
			productUpdate.setDescription(product.getDescription());
			productUpdate.setUpdateTime(new Date());
			productRepository.save(productUpdate);
			return productUpdate;
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + product.getProductId());
		}		
	}

	/**
	 * get list of product as per selected user under businessDomain and their categoryType
	 * @param sellerUserId
	 * @return
	 */
	@Override
	public Map<String ,List<Product>> getAllProductByShop(Long sellerUserId) {

		List<Product> productList =this.productRepository.findBySellerUserId(sellerUserId);

		if (productList!=null && productList.size()>0) {
			return productList.stream().collect (Collectors.groupingBy(d -> d.getCategoryType()));
			}
		else
		throw new ResourceNotFoundException("Products not found with SellerUser  : " + sellerUserId);
	}

	@Override
	public Product getProductById(long productId) {
		
		Optional<Product> productDb = this.productRepository.findById(productId);
		
		if(productDb.isPresent()) {
			return productDb.get();
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + productId);
		}
	}

	@Override
	public void deleteProduct(long productId) {
		Optional<Product> productDb = this.productRepository.findById(productId);
		
		if(productDb.isPresent()) {
			this.productRepository.delete(productDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + productId);
		}
		
	}

	/*@Override
	public Page<Product> findUpAll(Pageable pageable) {
		return productRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(),pageable);
	}*/

	@Override
	@Transactional
	public void increaseStock(Long productId, int amount) {
		Product productInfo = this.getProductById(productId);
		if (productInfo == null) throw new CustomException(ResultEnum.PRODUCT_NOT_EXIST);

		int update = productInfo.getProductStock() + amount;
		productInfo.setProductStock(update);
		productRepository.save(productInfo);
	}

	@Override
	@Transactional
	public void decreaseStock(Long productId, int amount) {
		Product productInfo = this.getProductById(productId);
		if (productInfo == null) throw new CustomException(ResultEnum.PRODUCT_NOT_EXIST);

		int update = productInfo.getProductStock() - amount;
		if(update <= 0) throw new CustomException(ResultEnum.PRODUCT_NOT_ENOUGH );

		productInfo.setProductStock(update);
		productRepository.save(productInfo);
	}

	@Override
	@Transactional
	public Product offSale(Long productId) {
		Product productInfo = this.getProductById(productId);
		if (productInfo == null) throw new CustomException(ResultEnum.PRODUCT_NOT_EXIST);

		if (productInfo.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
			throw new CustomException(ResultEnum.PRODUCT_STATUS_ERROR);
		}


		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		return productRepository.save(productInfo);
	}

	@Override
	@Transactional
	public Product onSale(Long productId) {
		Product productInfo = this.getProductById(productId);
		if (productInfo == null) throw new CustomException(ResultEnum.PRODUCT_NOT_EXIST);

		if (productInfo.getProductStatus() == ProductStatusEnum.UP.getCode()) {
			throw new CustomException(ResultEnum.PRODUCT_STATUS_ERROR);
		}

		productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
		return productRepository.save(productInfo);
	}


	@Override
	@Transactional
	public List<Product> productOutOfStock(Long sellerUserId) {

		List<Product> productList =this.productRepository.getAllProductBySellerUserId(sellerUserId);
		if (productList!=null && productList.size()>0) {
			return productList.stream()
					.filter(p ->p.getProductStock()<minProductQuantity)   // filtering price
					.collect(Collectors.toList());
		}
		else
			throw new ResourceNotFoundException("No Product out of stock for SellerUser  : " + sellerUserId);
	}

}
