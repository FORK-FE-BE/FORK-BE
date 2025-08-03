package com.fork.app.repository;

import com.fork.app.domain.entity.MenuOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuOptionRepository extends JpaRepository<MenuOption, Long> {
}
