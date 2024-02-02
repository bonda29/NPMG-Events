package org.example.events.npmg.payload.request;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class LoginPayload implements Serializable {
    @NotNull
    String username;

    @NotNull
    String password;
}
