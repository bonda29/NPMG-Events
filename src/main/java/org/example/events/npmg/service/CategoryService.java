package org.example.events.npmg.service;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.config.Mapper.CategoryMapper;
import org.example.events.npmg.models.Category;
import org.example.events.npmg.models.Event;
import org.example.events.npmg.payload.DTOs.CategoryDto;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.CategoryRepository;
import org.example.events.npmg.repository.EventRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.example.events.npmg.util.RepositoryUtil.findById;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    private final CategoryMapper categoryMapper;
    private final ModelMapper modelMapper;

    public ResponseEntity<MessageResponse> createCategory(CategoryDto categoryDto) {
        if (categoryDto.getName() == null) {
            throw new RuntimeException("Category must have a 'name'!");
        }

        Category category = categoryMapper.toEntity(categoryDto);
        category = categoryRepository.save(category);

        return ResponseEntity.ok(new MessageResponse("Category created successfully!"));
    }

    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Category category = findById(categoryRepository, id);
        return ResponseEntity.ok().body(categoryMapper.toDto(category));
    }

    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categoryMapper.toDto(categories);
        return ResponseEntity.ok().body(categoryDtos);
    }

    public ResponseEntity<MessageResponse> updateCategory(Long id, CategoryDto categoryDto) {
        Category category = findById(categoryRepository, id);
        modelMapper.map(categoryDto, category);
        categoryRepository.save(category);
        return ResponseEntity.ok(new MessageResponse("Category updated successfully!"));
    }

    public ResponseEntity<MessageResponse> deleteCategory(Long id) {
        List<Event> events = eventRepository.findAllByCategoryId(id).orElse(null);
        assert events != null;
        events.forEach(event -> {
            event.getCategories().removeIf(category -> category.getId().equals(id));
            eventRepository.save(event);
        });

        categoryRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Category deleted successfully!"));
    }
}