package com.fork.app.service;

import com.fork.app.domain.dto.MenuVectorizeDto;
import com.fork.app.domain.entity.Menu;
import com.fork.app.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;

    public void updateMenuImage(Long menuId, String imageUrl) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 메뉴입니다."));

        menu.setImgUrl(imageUrl);
    }

    public List<MenuVectorizeDto> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();  // 식당 정보 포함해서 조회

        return menus.stream().map(menu -> MenuVectorizeDto.builder()
                        .id(menu.getMenuId())
                        .menu(menu.getName())
                        .restaurant(menu.getRestaurant().getName())
                        .address(menu.getRestaurant().getAddress())
                        .restaurantId(menu.getRestaurant().getRestaurantId())
                        .category(String.valueOf(menu.getRestaurant().getRestaurantCategoryEnum()))
                        .price(menu.getPrice())
                        .hasAR(menu.getRestaurant().isHasAR())
                        .build())
                .collect(Collectors.toList());
    }
}
