package com.safetrust.borrow_service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetrust.borrow_service.entity.Borrow;
import com.safetrust.borrow_service.model.BorrowDTO;

@Component
public class BorrowMapper {
    @Autowired
    private ModelMapper modelMapper;

    /**
    * 
    * <p>Convert entity to DTO</p>
    * @param ContactEntity Contact Entity convert
    * @return Contact dto
    *
    */
    public BorrowDTO convertToDto(Borrow entity) {
        return modelMapper.map(entity, BorrowDTO.class);
    }

    /**
    * 
    * <p>Convert DTO  To Entity</p>
    * @param Contact Contact DTO convert
    * @return ContactEntity 
    *
    */
    public Borrow convertToEntity(BorrowDTO dto){
        return modelMapper.map(dto, Borrow.class);
    }
}
