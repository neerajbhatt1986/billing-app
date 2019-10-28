package com.bill.customer.dao.repository;

import com.bill.customer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    User findByToken(String token);
}
