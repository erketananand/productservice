package com.ketan.productservice.repositories;

import com.ketan.productservice.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    <S extends Price> S save(S entity);

    void deleteById(Long aLong);

    @Override
    Optional<Price> findById(Long aLong);
}
