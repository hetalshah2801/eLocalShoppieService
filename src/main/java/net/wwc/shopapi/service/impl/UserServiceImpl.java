package net.wwc.shopapi.service.impl;



import net.wwc.shopapi.enums.ResultEnum;
import net.wwc.shopapi.exception.CustomException;
import net.wwc.shopapi.exception.ResourceNotFoundException;
import net.wwc.shopapi.model.ProductInOrder;
import net.wwc.shopapi.model.User;
import net.wwc.shopapi.model.UserLogin;
import net.wwc.shopapi.repository.CartRepository;
import net.wwc.shopapi.repository.UserRepository;
import net.wwc.shopapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created By Smita Swami on 3/6/2020.
 */
@Service
@DependsOn("passwordEncoder")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceGeneratorServiceImpl sequenceGeneratorServiceImpl;

    @Override
    public User findOne(String phone) {
        return userRepository.findByPhone(phone);
    }

    @Override
    public List<User> findByRole(String role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    @Transactional
    public User save(User user) {
        User existingUser = userRepository.findByPhone(user.getPhone());
        if (existingUser !=null)
            throw new CustomException(ResultEnum.USER_ALREADY_EXIST);
        //register
        user.setId(sequenceGeneratorServiceImpl.generateSequence(User.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            User savedUser = userRepository.save(user);

            /*// initial Cart
            Cart savedCart = cartRepository.save(new Cart(savedUser));
            savedUser.setCart(savedCart);
            return userRepository.save(savedUser);*/
            return savedUser;

        } catch (Exception e) {
            throw new CustomException(ResultEnum.VALID_ERROR);
        }

    }

    @Override
    @Transactional
    public User update(User user) {
        User oldUser = userRepository.findByPhone(user.getPhone());
        oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
        //oldUser.setPassword(user.getPassword());
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        oldUser.setHomeAddress(user.getHomeAddress());
        oldUser.setShopAddress(user.getHomeAddress());
        oldUser.setDeliveryAddress(user.getHomeAddress());
        oldUser.setGpayNumber(user.getGpayNumber());
        oldUser.setGstNumber(user.getGstNumber());
        return userRepository.save(oldUser);
    }
    @Override
    public User verifyUser(UserLogin user){
        Boolean isValid = false;
        User savedUser = userRepository.findByPhone(user.getPhone());
        //isValid = savedUser.getPassword().equals(user.getPassword());
        isValid= passwordEncoder.matches(user.getPassword(),savedUser.getPassword());
        if(isValid)
            return savedUser;
        else
            throw new ResourceNotFoundException("User not found with userName  : " + user.getUserName());


    }



    @Override
    public List<User> getShopsByUserDetails(String businessDomain ,String buyerPinCode){
        List<User> listOfshops = userRepository.findAllByBusinessDomainAndPinCode(businessDomain,buyerPinCode);

        if (listOfshops!=null && listOfshops .size()>0)
                    return  listOfshops;
        else
            throw new ResourceNotFoundException("Shops are not available for this business domain   : " + businessDomain);
    }

}
