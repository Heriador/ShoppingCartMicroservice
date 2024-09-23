package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartDetailsRepository extends JpaRepository<CartDetailsEntity, Long> {
    Optional<CartDetailsEntity> findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("SELECT cd.itemId  FROM CartDetailsEntity cd where cd.cartId = :cartId")
    List<Long> findItemIdsByCartId(@Param("cartId") Long cartId);
}
