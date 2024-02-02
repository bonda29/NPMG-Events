package org.example.events.npmg.payload.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
  private String token;
  private String type;
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
}
