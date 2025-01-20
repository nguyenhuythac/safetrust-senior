package com.safetrust.inventory_service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetrust.inventory_service.entity.User;
import com.safetrust.inventory_service.model.UserDTO;

@Component
public class UserMapper {
    @Autowired
    private ModelMapper modelMapper;

    /**
    * 
    * <p>Convert entity to DTO</p>
    * @param ContactEntity Contact Entity convert
    * @return Contact dto
    *
    */
    public UserDTO convertToDto(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    /**
    * 
    * <p>Convert DTO  To Entity</p>
    * @param Contact Contact DTO convert
    * @return ContactEntity 
    *
    */
    public User convertToEntity(UserDTO dto){
        return modelMapper.map(dto, User.class);
    }
}
