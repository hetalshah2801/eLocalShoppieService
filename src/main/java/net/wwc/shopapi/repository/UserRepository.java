package net.wwc.shopapi.repository;


;

import net.wwc.shopapi.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created By Smita Swami on 3/6/2020.
 */

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    User findByPhone(String phone);
    List<User> findAllByRole(String role);
    List<User> findAllByBusinessDomainAndPinCode(String businessDomain,String buyerPinCode);

}
