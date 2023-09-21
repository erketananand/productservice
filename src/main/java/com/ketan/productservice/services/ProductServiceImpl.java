package com.ketan.productservice.services;


import com.ketan.productservice.dtos.GenericCategoryDto;
import com.ketan.productservice.dtos.GenericPriceDto;
import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;
import com.ketan.productservice.models.Category;
import com.ketan.productservice.models.Price;
import com.ketan.productservice.models.Product;
//import org.springframework.context.annotation.Primary;
import com.ketan.productservice.repositories.CategoryRepository;
import com.ketan.productservice.repositories.PriceRepository;
import com.ketan.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Primary
@Transactional
@Service("internalProductService")
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private PriceRepository priceRepository;

    private CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, PriceRepository priceRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
        this.categoryRepository = categoryRepository;
    }

    private Product convertProductDtoToProductEntity(GenericProductDto productDto) {
        Product product = Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(convertPriceDtoToPriceEntity(productDto.getPrice()))
                .category(convertGenericCategoryDtoToCategoryEntity(productDto.getCategory()))
                .image(productDto.getImage())
                .build();
        if(productDto.getId() != null) {
            product.setId(productDto.getId());
        }
        if(productDto.getPrice().getId() != null) {
            Optional<Price> price = priceRepository.findById(productDto.getPrice().getId());
            price.ifPresent(product::setPrice);
        }
        if(productDto.getCategory().getId() != null) {
            Optional<Category> category = categoryRepository.findById(productDto.getCategory().getId());
            category.ifPresent(product::setCategory);
        }
        return product;
    }

    private Price convertPriceDtoToPriceEntity(GenericPriceDto priceDto) {
        Price price = Price.builder()
                .price(priceDto.getPrice())
                .currency(priceDto.getCurrency())
                .build();
        if(priceDto.getId() != null) {
            price.setId(priceDto.getId());
        }
        return price;
    }

    private Category convertGenericCategoryDtoToCategoryEntity(GenericCategoryDto categoryDto) {
        Category category = Category.builder()
                .name(categoryDto.getName())
                .build();
        if(categoryDto.getId() != null) {
            category.setId(categoryDto.getId());
        }
        return category;
    }
    private Price updatePriceEntityFromPriceDto(GenericPriceDto priceDto, Price price) {
        price.setPrice(priceDto.getPrice());
        price.setCurrency(priceDto.getCurrency());
        return price;
    }

    private Category updateCategoryEntityFromGenericCategoryDto(GenericCategoryDto categoryDto, Category category) {
        category.setName(categoryDto.getName());
        return category;
    }


    private GenericProductDto convertProductEntityToGenericProductDto(Product savedProduct) {
        return GenericProductDto.builder()
                .id(savedProduct.getId())
                .title(savedProduct.getTitle())
                .description(savedProduct.getDescription())
                .price(convertPriceEntityToGenericPriceDto(savedProduct.getPrice()))
                .category(convertCategoryEntityToGenericCategoryDto(savedProduct.getCategory()))
                .image(savedProduct.getImage())
                .build();
    }

    private GenericCategoryDto convertCategoryEntityToGenericCategoryDto(Category category) {
        return GenericCategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private List<GenericProductDto> convertProductEntityListToProductDtoList(List<Product> products) {
        List<GenericProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            productDtoList.add(convertProductEntityToGenericProductDto(product));
        }
        return productDtoList;
    }

    private GenericPriceDto convertPriceEntityToGenericPriceDto(Price price) {
        return GenericPriceDto.builder()
                .id(price.getId())
                .price(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto productDto) {
        Product product = convertProductDtoToProductEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertProductEntityToGenericProductDto(savedProduct);
    }

    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return convertProductEntityToGenericProductDto(product.get());
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<Product> products = productRepository.findAllWithJoins();
        return convertProductEntityListToProductDtoList(products);
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto productDto) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product productEntity = product.get();
            productEntity.setTitle(productDto.getTitle());
            productEntity.setDescription(productDto.getDescription());
            productEntity.setPrice(updatePriceEntityFromPriceDto(productDto.getPrice(), productEntity.getPrice()));
            productEntity.setCategory(updateCategoryEntityFromGenericCategoryDto(productDto.getCategory(), productEntity.getCategory()));
            productEntity.setImage(productDto.getImage());
            Product savedProduct = productRepository.save(productEntity);
            return convertProductEntityToGenericProductDto(savedProduct);
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public GenericProductDto deleteProductById(Long id) throws NotFoundException {
        if(productRepository.existsById(id)) {
            Optional<Product> product = productRepository.findById(id);
            productRepository.deleteById(id);
            return convertProductEntityToGenericProductDto(product.get());
        } else {
            throw new NotFoundException("Product not found with id: " + id);
        }
    }

    @Override
    public List<GenericProductDto> getAllProductsByCategoryId(Long categoryId) {
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        return convertProductEntityListToProductDtoList(products);
    }
}
