package com.fork.app.controller;

import com.fork.app.domain.dto.MenuVectorizeDto;
import com.fork.app.domain.dto.response.MenuResponseDto;
import com.fork.app.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/vectorize")
    public ResponseEntity<List<MenuVectorizeDto>> getAllMenus() {
        List<MenuVectorizeDto> menus = menuService.getAllMenus();
        return ResponseEntity.ok().body(menus);
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenuDetail(menuId));
    }
}
