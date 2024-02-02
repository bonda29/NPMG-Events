package org.example.events.npmg.repository;

import org.example.events.npmg.models.TicketPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketPurchaseRepository extends JpaRepository<TicketPurchase, Long> {
}