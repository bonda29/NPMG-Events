package org.example.events.npmg.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "code_sent_to_mail")
public class CodeSentToMail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    private String code;
}
