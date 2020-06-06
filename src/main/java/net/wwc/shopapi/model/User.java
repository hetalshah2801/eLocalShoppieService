package net.wwc.shopapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Document(collection = "UserDB")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    @Id
    private Long id;

    private String email;
    @NotNull
    private String userName;
    @NotNull
    @Size(min = 3, message = "Length must be more than 3")
    private String password;
    @NotNull
    private String name;

    private String shopName;
    private String shopIcon;
    @NotNull
    private String phone;
    @NotNull
    private String pinCode;

    private String homeAddress;
    private String shopAddress;
    private String deliveryAddress;

    private String gpayNumber;
    private String gstNumber;

    private String businessDomain;
    @NotNull
    private boolean active;
    @NotNull
    private String role = "ROLE_CUSTOMER";

}
