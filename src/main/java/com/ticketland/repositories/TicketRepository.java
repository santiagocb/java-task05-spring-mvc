package com.ticketland.repositories;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, String> {

    @Query("SELECT t FROM Ticket t WHERE t.userAccount.id = :userAccountId")
    List<Ticket> findTicketsByUserAccountId(@Param("userAccountId") String userAccountId);

    Page<Ticket> findAllByUserAccount(UserAccount userAccount, Pageable pageable);
}
