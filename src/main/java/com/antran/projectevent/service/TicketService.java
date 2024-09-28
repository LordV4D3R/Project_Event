package com.antran.projectevent.service;

import com.antran.projectevent.model.Ticket;
import com.antran.projectevent.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    //Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
