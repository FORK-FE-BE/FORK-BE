package com.fork.app.repository;

import com.fork.app.domain.entity.Cart;
import com.fork.app.domain.entity.Restaurant;
import com.fork.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}
