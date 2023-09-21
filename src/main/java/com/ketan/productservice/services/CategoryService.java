package com.ketan.productservice.services;

import com.ketan.productservice.dtos.GenericCategoryDto;
import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<GenericCategoryDto> getAllCategories();
    List<GenericProductDto> getAllProductsByCategoryId(Long categoryId);
    GenericCategoryDto deleteCategoryById(Long categoryId) throws NotFoundException;
}
