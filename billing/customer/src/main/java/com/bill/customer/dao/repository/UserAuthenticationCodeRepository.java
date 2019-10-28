package com.bill.customer.dao.repository;

import com.bill.customer.entity.User;
import com.bill.customer.entity.UserAuthenticationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticationCodeRepository extends JpaRepository<UserAuthenticationCode, Integer> {

    UserAuthenticationCode findByEmailAndCode(String email, String code);
    Optional<UserAuthenticationCode> findByEmail(String email);

}
