package com.ticketland.repositories;

import com.ticketland.entities.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, String> {
    Optional<UserAccount> findByUserId(String userId);
}
