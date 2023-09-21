package com.ketan.productservice.services;

import java.util.List;

import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;

public interface ProductService {
    GenericProductDto createProduct(GenericProductDto productDto);

    GenericProductDto getProductById(Long id) throws NotFoundException;

    List<GenericProductDto> getAllProducts();

    GenericProductDto updateProductById(Long id, GenericProductDto productDto) throws NotFoundException;

    GenericProductDto deleteProductById(Long id) throws NotFoundException;

    List<GenericProductDto> getAllProductsByCategoryId(Long categoryId);
}
