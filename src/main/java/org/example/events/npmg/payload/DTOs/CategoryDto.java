package org.example.events.npmg.payload.DTOs;

import lombok.Data;
import lombok.Value;
import org.example.events.npmg.models.Category;

import java.io.Serializable;

/**
 * DTO for {@link Category}
 */
@Data
public class CategoryDto {
    String name;
    String description;
    String imageUrl;
}