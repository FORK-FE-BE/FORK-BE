package com.fork.app.controller;

import com.fork.app.domain.dto.CartRequestDto;
import com.fork.app.domain.dto.UpdateQuantityRequest;
import com.fork.app.domain.entity.CartItem;
import com.fork.app.repository.CartItemRepository;
import com.fork.app.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    // [1] 장바구니에 메뉴 추가
    @PostMapping("/api/cart/{userId}")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartRequestDto dto) {
        log.info("장바구니 추가 요청 userId={}, menuId={}", userId, dto.getMenuId());
        cartService.addCart(dto, userId);
        return ResponseEntity.ok(Map.of(
                "result", "success",
                "message", "장바구니에 추가되었습니다."
        ));
    }

    // [2] 장바구니 조회
    @GetMapping("/api/cart/{userId}")
    public ResponseEntity<?> getCart(@PathVariable Long userId) {
        log.info("장바구니 조회 요청 userId={}", userId);
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // [3] 장바구니 항목 삭제
    @DeleteMapping("/api/cart/{userId}/items/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        return cartService.deleteCartItem(userId, cartItemId);
    }
    // [4] 장바구니 수량 변경
    @PutMapping("/api/cart/{userId}/items/{cartItemId}")
    public ResponseEntity<?> updateCartItem( @PathVariable Long userId,
                                             @PathVariable Long cartItemId,
                                             @RequestBody UpdateQuantityRequest updateQuantityRequest
    ) {
        Integer quantity = updateQuantityRequest.getQuantity();
        cartService.updateCartItem(cartItemId, quantity);

        return ResponseEntity.ok().body(Map.of(
                "result", "success",
                "message", "장바구니 수량이 변경되었습니다."
                )
        );
    }
}
