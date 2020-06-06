package net.wwc.shopapi.service;



import net.wwc.shopapi.model.ProductInOrder;
import net.wwc.shopapi.model.User;
import net.wwc.shopapi.model.UserLogin;

import java.util.List;

/**
 * Created By Smita Swami on 3/6/2020.
 */
public interface UserService {
    User findOne(String email);

    List<User> findByRole(String role);
    List<User> getShopsByUserDetails(String businessDomain, String buyerPinCode);

    User save(User user);

    User update(User user);
    public User verifyUser(UserLogin user);


}
