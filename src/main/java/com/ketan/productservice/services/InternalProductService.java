package com.ketan.productservice.services;


import com.ketan.productservice.dtos.GenericCategoryDto;
import com.ketan.productservice.dtos.GenericPriceDto;
import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;
import com.ketan.productservice.models.Category;
import com.ketan.productservice.models.Price;
import com.ketan.productservice.models.Product;
//import org.springframework.context.annotation.Primary;
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
public class InternalProductService implements ProductService {

    private ProductRepository productRepository;
    private PriceRepository priceRepository;

    public InternalProductService(ProductRepository productRepository, PriceRepository priceRepository) {
        this.productRepository = productRepository;
        this.priceRepository = priceRepository;
    }

    private Product convertProductDtoToProductEntity(GenericProductDto productDto) {
        return Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .price(convertPriceDtoToPriceEntity(productDto.getPrice()))
                .category(convertGenericCategoryDtoToCategoryEntity(productDto.getCategory()))
                .image(productDto.getImage())
                .build();
    }

    private Price convertPriceDtoToPriceEntity(GenericPriceDto priceDto) {
        return Price.builder()
                .price(priceDto.getPrice())
                .currency(priceDto.getCurrency())
                .build();
    }

    private Category convertGenericCategoryDtoToCategoryEntity(GenericCategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .build();
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
        List<Product> products = productRepository.findAll();
        return convertProductEntityListToProductDtoList(products);
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto productDto) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product productEntity = product.get();
            productEntity.setTitle(productDto.getTitle());
            productEntity.setDescription(productDto.getDescription());
            productEntity.setPrice(convertPriceDtoToPriceEntity(productDto.getPrice()));
            productEntity.setCategory(convertGenericCategoryDtoToCategoryEntity(productDto.getCategory()));
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
}
