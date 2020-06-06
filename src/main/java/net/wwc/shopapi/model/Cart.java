package net.wwc.shopapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotNull;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Document(collection = "CartDB")
public class Cart {
    @Transient
    public static final String SEQUENCE_NAME = "cart_sequence";
    @Id
    private Long cartId;
    @NotNull
    private Long buyerId;
    @NotNull
    private String status;
    
    private List<ProductInOrder> productInOrder;
}
