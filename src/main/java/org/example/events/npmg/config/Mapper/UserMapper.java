package org.example.events.npmg.config.Mapper;

import org.example.events.npmg.models.User;
import org.example.events.npmg.payload.DTOs.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> toDto(List<User> users) {

        Type listType = new TypeToken<List<UserDto>>() {
        }.getType();
        return modelMapper.map(users, listType);
    }


    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
