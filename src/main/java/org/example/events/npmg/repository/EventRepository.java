package org.example.events.npmg.repository;

import org.example.events.npmg.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e JOIN e.categories c WHERE e.name LIKE CONCAT(:eventName, '%') AND c.name = :categoryName AND e.dateOfCreation > :date")
    Optional<List<Event>> findEvents(@Param("eventName") String eventName, @Param("categoryName") String categoryName, @Param("date") LocalDateTime date);
}