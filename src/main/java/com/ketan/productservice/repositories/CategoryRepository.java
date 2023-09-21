package com.ketan.productservice.repositories;

import com.ketan.productservice.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    <S extends Category> S save(S entity);

    void deleteById(Long aLong);

    @Override
    Optional<Category> findById(Long aLong);

    @Override
    List<Category> findAll();

    @Override
    boolean existsById(Long aLong);
}
