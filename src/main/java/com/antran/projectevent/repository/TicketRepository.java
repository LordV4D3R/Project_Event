package com.antran.projectevent.repository;

import com.antran.projectevent.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepository extends JpaRepository <Ticket, UUID>{

}
