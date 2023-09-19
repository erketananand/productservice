package com.ketan.productservice.repositories;

import com.ketan.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    <S extends Product> S save(S entity);

    Optional<Product> findById(Long aLong);

    List<Product> findAll();

    void deleteById(Long aLong);

    boolean existsById(Long aLong);
}
