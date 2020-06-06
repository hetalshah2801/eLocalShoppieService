package net.wwc.shopapi.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Document (collection = "ProductDB")
public class Product {
	@Transient
	public static final String SEQUENCE_NAME = "product_sequence";
	@Id
	private Long productId;
	@NotNull
	private Long sellerUserId;

	@Size(max = 100)
	private String productName;
	@NotNull
	private BigDecimal productPrice;
	private String description;
	@NotNull
	@Min(0)
	private Integer productStock;
	private String productIcon;
	private Integer productStatus;
	private String categoryType;
	private String productType;
	private String unit;
	private Integer quantity;
	private String businessDomain;
	private Date expiryDate;
	private Date createTime;
	private Date updateTime;

}
	

