package org.example.events.npmg.repository;

import org.example.events.npmg.models.CodeSentToMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeSentToMailRepository extends JpaRepository<CodeSentToMail, Long> {
}