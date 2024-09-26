package com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.repository;

import com.PragmaBootcamp2024.ShoppingCartMicroservice.infrastructure.driven.mysql.entity.CartDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartDetailsRepository extends JpaRepository<CartDetailsEntity, Long> {
    Optional<CartDetailsEntity> findByCartIdAndItemId(Long cartId, Long itemId);

    @Query("SELECT cd.itemId  FROM CartDetailsEntity cd where cd.cart.id = :cartId")
    List<Long> findItemIdsByCartId(@Param("cartId") Long cartId);

    Optional<List<CartDetailsEntity>> findByCartId(Long cartId);

    @Modifying
    @Query("DELETE FROM CartDetailsEntity cd where cd.cart.id = :cartId AND cd.itemId = :itemId")
    void deleteByCartIdAndItemId(@Param("cartId")Long cartId,
                                 @Param("itemId")Long itemId);
}
