package com.fork.app.repository;

import com.fork.app.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    // 1) 전체 User 반환
    User findByEmail(String email);

    // 2) 특정 필드 반환
    @Query("select u.email from User u where u.email = :email")
    String findEmailByEmail(@Param("email") String email);

    User findByUserId(Long userId);
}
