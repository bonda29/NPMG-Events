package org.example.events.npmg.config.Mapper;

import org.example.events.npmg.models.Category;
import org.example.events.npmg.payload.DTOs.CategoryDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper;

    public CategoryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public CategoryDto toDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public List<CategoryDto> toDto(List<Category> categories) {
        Type listType = new TypeToken<List<CategoryDto>>() {
        }.getType();
        return modelMapper.map(categories, listType);
    }

    public Category toEntity(CategoryDto dto) {
        return modelMapper.map(dto, Category.class);
    }
}