package com.alten.ecommerce.product.mapper;

import com.alten.ecommerce.product.dto.ProductDTO;
import com.alten.ecommerce.product.back.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product entity);

    @Mapping(target = "id", ignore = true)
    Product toEntity(ProductDTO dto);
}
