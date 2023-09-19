package com.ketan.productservice.services;

import java.util.ArrayList;
import java.util.List;

import com.ketan.productservice.dtos.GenericCategoryDto;
import com.ketan.productservice.dtos.GenericPriceDto;
import com.ketan.productservice.thirdpartyclients.dtos.FakeStoreCategoryDto;
import com.ketan.productservice.thirdpartyclients.dtos.FakeStorePriceDto;
import org.springframework.stereotype.Service;
import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;
import com.ketan.productservice.thirdpartyclients.dtos.FakeStoreProductDto;
import com.ketan.productservice.thirdpartyclients.services.FakeStoreProductServiceClient;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private FakeStoreProductServiceClient fakeStoreProductServiceClient;

    public FakeStoreProductService(FakeStoreProductServiceClient fakeStoreProductServiceClient) {
        this.fakeStoreProductServiceClient = fakeStoreProductServiceClient;
    }

    private GenericProductDto convertFakeStoreProductDtoToGenericProductDto(FakeStoreProductDto fakeStoreProductDto) {
        return GenericProductDto.builder()
                .id(fakeStoreProductDto.getId())
                .title(fakeStoreProductDto.getTitle())
                .description(fakeStoreProductDto.getDescription())
                .price(convertFakeStorePriceDtoToGenericProductDto(fakeStoreProductDto.getPrice()))
                .category(convertFakeStoreCategoryDtoToGenericCategoryDto(fakeStoreProductDto.getCategory()))
                .image(fakeStoreProductDto.getImage())
                .build();
    }

    private GenericPriceDto convertFakeStorePriceDtoToGenericProductDto(FakeStorePriceDto fakeStorePriceDto) {
        return GenericPriceDto.builder()
                .price(fakeStorePriceDto.getPrice())
                .currency(fakeStorePriceDto.getCurrency())
                .build();
    }

    private GenericCategoryDto convertFakeStoreCategoryDtoToGenericCategoryDto(FakeStoreCategoryDto fakeStoreCategoryDto) {
        return GenericCategoryDto.builder()
                .name(fakeStoreCategoryDto.getName())
                .build();
    }

    @Override
    public GenericProductDto createProduct(GenericProductDto productDto) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.createProduct(productDto);
        return this.convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto getProductById(Long id) throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.getProductById(id);
        return this.convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public List<GenericProductDto> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreProductServiceClient.getAllProducts();
        List<GenericProductDto> genericProductDtos = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            genericProductDtos.add(this.convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto));
        }
        return genericProductDtos;
    }

    @Override
    public GenericProductDto updateProductById(Long id, GenericProductDto productDto) throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.updateProductById(id, productDto);
        return this.convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
    }

    @Override
    public GenericProductDto deleteProductById(Long id) throws NotFoundException {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductServiceClient.deleteProductById(id);
        return this.convertFakeStoreProductDtoToGenericProductDto(fakeStoreProductDto);
    }

}
