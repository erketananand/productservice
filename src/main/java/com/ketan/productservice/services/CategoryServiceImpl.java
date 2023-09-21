package com.ketan.productservice.services;

import com.ketan.productservice.dtos.GenericCategoryDto;
import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;
import com.ketan.productservice.models.Category;
import com.ketan.productservice.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
    private ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository,@Qualifier("internalProductService") ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    private GenericCategoryDto convertCategoryEntityToGenericCategoryDto(Category category) {
        return GenericCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private List<GenericCategoryDto> convertCategoryEntitiesToGenericCategoryDtos(List<Category> categories) {
        List<GenericCategoryDto> genericCategoryDtos = new ArrayList<>();
        for(Category category : categories) {
            genericCategoryDtos.add(convertCategoryEntityToGenericCategoryDto(category));
        }
        return genericCategoryDtos;
    }

    @Override
    public List<GenericCategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return convertCategoryEntitiesToGenericCategoryDtos(categories);
    }

    @Override
    public List<GenericProductDto> getAllProductsByCategoryId(Long categoryId) {
        return this.productService.getAllProductsByCategoryId(categoryId);
    }

    @Override
    public GenericCategoryDto deleteCategoryById(Long categoryId) throws NotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            categoryRepository.delete(category);
            return convertCategoryEntityToGenericCategoryDto(category);
        } else {
            throw new NotFoundException("Category not found with id: " + categoryId);
        }
    }
}
