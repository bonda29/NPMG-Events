package org.example.events.npmg.payload.request;

import lombok.Data;

@Data
public class EmailPayload {
    String to;
    String name;
    String subject;
    String content;
}
