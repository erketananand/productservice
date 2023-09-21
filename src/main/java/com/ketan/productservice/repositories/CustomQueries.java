package com.ketan.productservice.repositories;

public interface CustomQueries {
    String FIND_ALL_PRODUCTS = "SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.price pr";
    String FIND_ALL_PRODUCTS_BY_CATEGORY_ID = "SELECT p FROM Product p JOIN FETCH p.category c JOIN FETCH p.price pr WHERE c.id = :categoryId";
}
