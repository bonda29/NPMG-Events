package org.example.events.npmg.service;

import lombok.Data;

@Data
public class EmailPayload {
    String to;
    String name;
    String subject;
    String content;
}
