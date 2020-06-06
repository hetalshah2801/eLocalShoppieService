package net.wwc.shopapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class ProductInOrder {

	private Long productId;

	@NotNull
	@Size(max = 100)
	@Indexed(unique = true)
	private String productName;
	@NotNull
	private BigDecimal productPrice;
	@NotNull
	private Long sellerId;
	@NotNull
	private String shopName;
	private String description;
	private String productIcon;
	private Integer productStatus;
	private String productCategory;
	private String productType;
	private String unit;
	private Integer count;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductInOrder that = (ProductInOrder) o;
		return productId.equals(that.productId) &&
				sellerId.equals(that.sellerId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, sellerId);
	}
}
	

