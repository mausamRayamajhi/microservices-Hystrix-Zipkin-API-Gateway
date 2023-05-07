package com.mausam.user.service.mapper;

import com.mausam.user.dto.UserDTO;
import com.mausam.user.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

@Service
public class UserServiceMapper implements GenericMapper<User, UserDTO> {

    PropertyMap<User, UserDTO> skipModifiedFieldsMap = new PropertyMap<User, UserDTO>() {
        protected void configure() {
            map().setUserId(source.getUserId());
            map().setFirstName(source.getFirstName());
            map().setLastName(source.getLastName());
            map().setEmail(source.getEmail());
            map().setRole(source.getRole());
            map().setStatus(source.getStatus());


        }
    };
    private ModelMapper mapper = new ModelMapper();

    private UserServiceMapper() {
        mapper.addMappings(skipModifiedFieldsMap);
    }

    @Override
    public User convertToEntity(UserDTO dto) {
        return mapper.map(dto, User.class);
    }

    @Override
    public UserDTO convertToDTO(User entity) {
        return mapper.map(entity, UserDTO.class);
    }

}
