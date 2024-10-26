package com.ticketland.services;

import com.ticketland.entities.Event;
import com.ticketland.entities.Ticket;
import com.ticketland.entities.User;
import com.ticketland.entities.UserAccount;
import com.ticketland.repositories.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TicketService {

    private TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void generate(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> findTicketsByAccountUserId(String userAccountId) {
        return ticketRepository.findTicketsByUserAccountId(userAccountId);
    }

    public List<Ticket> findAll() {
        return StreamSupport.stream(ticketRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Ticket> getBookedTickets(UserAccount userAccount, int pageSize, int pageNum) {
        return ticketRepository.findAllByUserAccount(userAccount, PageRequest.of(pageNum - 1, pageSize)).getContent();
    }

    @Transactional
    public void preloadTickets(List<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticketRepository.save(ticket);
        }
    }
}
