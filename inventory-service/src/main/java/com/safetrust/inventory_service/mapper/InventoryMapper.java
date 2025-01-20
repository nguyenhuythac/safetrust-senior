package com.safetrust.inventory_service.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import com.safetrust.inventory_service.entity.Inventory;
import com.safetrust.inventory_service.model.InventoryDTO;

@Component
public class InventoryMapper {
    // @Autowired
    // private ModelMapper modelMapper;

    /**
    * 
    * <p>Convert entity to DTO</p>
    * @param ContactEntity Contact Entity convert
    * @return Contact dto
    *
    */
    public InventoryDTO convertToDto(Inventory entity) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        TypeMap<Inventory, InventoryDTO> propertyMapper = modelMapper.createTypeMap(Inventory.class,
                InventoryDTO.class);
        propertyMapper.addMappings(mapper -> mapper.skip(InventoryDTO::setBooks));
        propertyMapper.addMappings(mapper -> mapper.skip(InventoryDTO::setCreatedBrandUsers));
        return modelMapper.map(entity, InventoryDTO.class);
    }

    /**
    * 
    * <p>Convert DTO  To Entity</p>
    * @param Contact Contact DTO convert
    * @return ContactEntity 
    *
    */
    public Inventory convertToEntity(InventoryDTO dto){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Inventory.class);
    }
}
