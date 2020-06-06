package net.wwc.shopapi.controller;

import java.util.List;
import java.util.Map;

import net.wwc.shopapi.model.Product;
import net.wwc.shopapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("products/getProductsByCategory/{sellerUserId}")
	public ResponseEntity <Map<String ,List<Product>>> getAllProductByCategory(@PathVariable Long sellerUserId){

		return ResponseEntity.ok().body(productService.getAllProductByShop(sellerUserId));
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProductByProductId(@PathVariable long productId){
		return ResponseEntity.ok().body(productService.getProductById(productId));
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		return ResponseEntity.ok().body(this.productService.createProduct(product));
	}
	
	@PutMapping("/updateProduct")
	public ResponseEntity<Product> updateProduct(@RequestBody Product product){
		return ResponseEntity.ok().body(this.productService.updateProduct(product));
	}

	@DeleteMapping("/products/{productId}")
	public HttpStatus deleteProduct(@PathVariable long productId){
		this.productService.deleteProduct(productId);
		return HttpStatus.OK;
	}

	@GetMapping("getStockForSeller/{sellerUserId}")
	public ResponseEntity <List<Product>> getAllProductOutOfStock(@PathVariable Long sellerUserId){

		return ResponseEntity.ok().body(productService.productOutOfStock(sellerUserId));
	}

}
