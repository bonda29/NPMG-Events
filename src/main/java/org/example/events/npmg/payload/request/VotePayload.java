package org.example.events.npmg.payload.request;

import lombok.Value;

import java.io.Serializable;

@Value
public class VotePayload implements Serializable {
    String ip;
    Long beachId;
    String flagColor;
    Long userId; //can be null
}
