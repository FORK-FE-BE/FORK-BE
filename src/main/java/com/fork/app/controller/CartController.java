package com.fork.app.controller;

import com.fork.app.domain.dto.CartRequestDto;
import com.fork.app.domain.dto.UpdateQuantityRequest;
import com.fork.app.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Cart API", description = "장바구니 관련 API")
public class CartController {
    private final CartService cartService;

    // [1] 장바구니에 메뉴 추가
    @Operation(summary = "장바구니에 메뉴 추가", description = "사용자의 장바구니에 메뉴를 추가합니다.")
    @PostMapping("/api/cart/{userId}")
    public ResponseEntity<?> addToCart(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId,
            @RequestBody CartRequestDto dto) {
        log.info("장바구니 추가 요청 userId={}, menuId={}", userId, dto.getMenuId());
        cartService.addCart(dto, userId);
        return ResponseEntity.ok().body(Map.of(
                "result", "success",
                "message", "장바구니에 추가되었습니다."));
    }

    // [2] 장바구니 조회
    @Operation(summary = "장바구니 조회", description = "해당 사용자의 장바구니 정보를 조회합니다.")
    @GetMapping("/api/cart/{userId}")
    public ResponseEntity<?> getCart(
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long userId) {
        log.info("장바구니 조회 요청 userId={}", userId);
        return ResponseEntity.ok().body(cartService.getCart(userId));
    }

    // [3] 장바구니 항목 삭제
    @Operation(summary = "장바구니 항목 삭제", description = "장바구니 내 특정 항목을 삭제합니다.")
    @DeleteMapping("/api/cart/{userId}/items/{cartItemId}")
    public ResponseEntity<?> deleteCartItem(
            @Parameter(description = "사용자 ID", example = "1")@PathVariable Long userId,
            @Parameter(description = "장바구니 항목 ID", example = "2")@PathVariable Long cartItemId) {

        return cartService.deleteCartItem(userId, cartItemId);
    }

    // [4] 장바구니 수량 변경
    @Operation(summary = "장바구니 수량 변경", description = "장바구니 내 특정 항목의 수량을 수정합니다.")
    @PutMapping("/api/cart/{userId}/items/{cartItemId}")
    public ResponseEntity<?> updateCartItem(
            @Parameter(description = "사용자 ID", example = "1")@PathVariable Long userId,
            @Parameter(description = "장바구니 항목 ID", example = "2")@PathVariable Long cartItemId,
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
