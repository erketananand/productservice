package com.ketan.productservice.repositories;

import com.ketan.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    <S extends Product> S save(S entity);

    Optional<Product> findById(Long aLong);

    List<Product> findAll();

    void deleteById(Long aLong);

    boolean existsById(Long aLong);

    @Query(CustomQueries.FIND_ALL_PRODUCTS)
    List<Product> findAllWithJoins();

    @Query(CustomQueries.FIND_ALL_PRODUCTS_BY_CATEGORY_ID)
    List<Product> findAllByCategoryId(Long categoryId);
}
