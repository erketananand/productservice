package com.ketan.productservice.controllers;

import com.ketan.productservice.dtos.GenericCategoryDto;
import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;
import com.ketan.productservice.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<GenericCategoryDto> getAllCategories() {
        return this.categoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public List<GenericProductDto> getAllProductsByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return this.categoryService.getAllProductsByCategoryId(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public GenericCategoryDto deleteCategoryById(@PathVariable("categoryId") Long categoryId) throws NotFoundException {
        return this.categoryService.deleteCategoryById(categoryId);
    }
}
