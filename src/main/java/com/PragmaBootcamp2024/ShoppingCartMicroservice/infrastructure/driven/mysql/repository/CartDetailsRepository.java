package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDetailsRepository extends JpaRepository<CartDetailsEntity, Long> {
    Optional<CartDetailsEntity> findByCartIdAndItemId(Long cartId, Long itemId);
}
