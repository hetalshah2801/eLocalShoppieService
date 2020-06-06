package net.wwc.shopapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "OrderDB")
public class Order {
    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";
    @Id
    private Long orderId;
    @NotNull
    private Long buyerId;
    @NotNull
    private Long sellerId;
    @NotNull
    private Set<ProductInOrder> products = new HashSet<>();
    private String buyerEmail;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String sellerEmail;
    private String sellerName;
    private String sellerPhone;
    private String sellerAddress;
    private String shopName;
    // Total Amount
    @NotNull
    private BigDecimal orderAmount;
    @NotNull
    private Integer orderStatus;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
