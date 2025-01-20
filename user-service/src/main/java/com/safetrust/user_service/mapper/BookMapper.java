package com.safetrust.user_service.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.safetrust.user_service.entity.Book;
import com.safetrust.user_service.model.BookDTO;

@Component
public class BookMapper {
    @Autowired
    private ModelMapper modelMapper;

    /**
    * 
    * <p>Convert entity to DTO</p>
    * @param ContactEntity Contact Entity convert
    * @return Contact dto
    *
    */
    public BookDTO convertToDto(Book entity) {
        return modelMapper.map(entity, BookDTO.class);
    }

    /**
    * 
    * <p>Convert DTO  To Entity</p>
    * @param Contact Contact DTO convert
    * @return ContactEntity 
    *
    */
    public Book convertToEntity(BookDTO dto){
        return modelMapper.map(dto, Book.class);
    }
}
