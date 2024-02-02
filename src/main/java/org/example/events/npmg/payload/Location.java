package org.example.events.npmg.payload;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Location {
	private String address;
	private String city;
	private String zipCode;
}
