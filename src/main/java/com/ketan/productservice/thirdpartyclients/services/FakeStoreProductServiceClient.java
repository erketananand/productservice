package com.ketan.productservice.thirdpartyclients.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import com.ketan.productservice.dtos.GenericProductDto;
import com.ketan.productservice.exceptions.NotFoundException;
import com.ketan.productservice.thirdpartyclients.dtos.FakeStoreProductDto;

@Service
public class FakeStoreProductServiceClient {
    private RestTemplateBuilder restTemplateBuilder;
    private String fakeStoreApiUrl;

    public FakeStoreProductServiceClient(RestTemplateBuilder restTemplateBuilder,
            @Value("${fakestore.api.url}") String fakeStoreApiUrl) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreApiUrl = fakeStoreApiUrl;
    }

    public FakeStoreProductDto createProduct(GenericProductDto productDto) {
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity(this.fakeStoreApiUrl, productDto,
                FakeStoreProductDto.class);
        return response.getBody();
    }

    public FakeStoreProductDto getProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity(this.fakeStoreApiUrl + "/" + id,
                FakeStoreProductDto.class);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if (fakeStoreProductDto == null) {
            throw new NotFoundException("Product with id '" + id + "' not found");
        }
        return fakeStoreProductDto;
    }

    public List<FakeStoreProductDto> getAllProducts() {
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.getForEntity(this.fakeStoreApiUrl,
                FakeStoreProductDto[].class);
        return Arrays.stream(response.getBody()).toList();
    }

    public FakeStoreProductDto updateProductById(Long id, GenericProductDto productDto) throws NotFoundException {
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(productDto, FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate
                .responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(this.fakeStoreApiUrl + "/" + id,
                HttpMethod.PUT,
                requestCallback, responseExtractor);
        if (response == null || response.getBody() == null) {
            throw new NotFoundException("Product with id '" + id + "' not found");
        } else {
            return response.getBody();
        }
    }

    public FakeStoreProductDto deleteProductById(Long id) throws NotFoundException {
        RestTemplate restTemplate = this.restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(FakeStoreProductDto.class);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate
                .responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(this.fakeStoreApiUrl + "/" + id,
                HttpMethod.DELETE,
                requestCallback, responseExtractor);
        if (response == null || response.getBody() == null) {
            throw new NotFoundException("Product with id '" + id + "' not found");
        } else {
            return response.getBody();
        }
    }

}
