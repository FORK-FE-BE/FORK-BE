package com.fork.app.service;

import com.fork.app.domain.dto.response.CartItemResponseDto;
import com.fork.app.domain.dto.request.CartRequestDto;
import com.fork.app.domain.dto.response.CartResponseDto;
import com.fork.app.domain.entity.*;
import com.fork.app.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final CartItemRepository cartItemRepository;

    /**
     * 장바구니에 메뉴 추가
     * - 유저당 카트는 1개
     * - 만약 기존 카트의 식당과 다른 식당의 메뉴를 추가하려 하면 기존 카트를 삭제하고 새로 생성
     */
    public void addCart(CartRequestDto cartRequestDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Long menuId = cartRequestDto.getMenuId();
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new IllegalArgumentException("메뉴 없음"));
        Restaurant restaurant = menu.getRestaurant();
        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart != null) {
            //장바구니가 존재한다면 같은 식당의 id 인지 검사
            if (!cart.getRestaurant().getRestaurantId().equals(restaurant.getRestaurantId())) {
                cartItemRepository.deleteAll(cart.getCartItems());
                cartRepository.delete(cart);
                cart = null;
            }
        }
        // 장바구니가 없으면 새로 생성
        if (cart == null) {
            cart = Cart.builder().user(user).restaurant(restaurant).build();
            cartRepository.save(cart);
        }
        CartItem existingItem = cart
                .getCartItems().stream().filter(item -> item.getMenu().getMenuId().equals(menuId))
                .findFirst().orElse(null);

        if (existingItem != null) {
            // 이미 있으면 수량 증가만
            existingItem.setQuantity(existingItem.getQuantity() + cartRequestDto.getQuantity());
        } else {
            // 새롭게 추가
            CartItem cartItem = CartItem.builder()
                    .cart(cart)
                    .menu(menu)
                    .quantity(cartRequestDto.getQuantity())
                    .build();
            cartItemRepository.save(cartItem);
        }
    }

    public CartResponseDto getCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Cart cart = cartRepository.findByUser(user).orElse(null);
        if (cart == null || cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            return null; // 또는 빈 DTO 반환
        }

        String restaurantName = cart.getRestaurant().getName(); // null 체크 뒤에 가져오기 권장

        // CartItemResponseDto 리스트 생성
        List<CartItemResponseDto> cartItemResponseList = cart.getCartItems().stream()
                .map(cartItem -> {
                    // 옵션 추가 금액 계산 예시 (있으면 실제 구현 필요)
                    int optionExtraPrice = 0;
                    // ex) cartItem.getSelectedOptions()를 파싱해 optionExtraPrice 계산

                    int itemTotalPrice = (cartItem.getMenu().getPrice() + optionExtraPrice) * cartItem.getQuantity();

                    return CartItemResponseDto.builder()
                            .cartItemId(cartItem.getId())   // ID 필드명 확인
                            .menuId(cartItem.getMenu().getMenuId())
                            .menuName(cartItem.getMenu().getName())
                            .quantity(cartItem.getQuantity())
                            .price(cartItem.getMenu().getPrice())
                            .totalPrice(itemTotalPrice)
                            .build();
                })
                .collect(Collectors.toList());

        // 총 가격 계산 (전체 아이템 합산)
        int totalPrice = cartItemResponseList.stream()
                .mapToInt(CartItemResponseDto::getTotalPrice)
                .sum();

        return CartResponseDto.builder()
                .userId(user.getUserId())
                .restaurantId(cart.getRestaurant().getRestaurantId())
                .restaurantName(restaurantName)       // 추가: 식당 이름 포함
                .cartItemList(cartItemResponseList)
                .totalPrice(totalPrice)
                .build();
    }


    public void updateCartItem(Long cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("해당 장바구니 항목을 찾을 수 없습니다."));
        cartItem.setQuantity(quantity);
    }

    public ResponseEntity<?> deleteCartItem(Long userId, Long cartItemId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new IllegalArgumentException("장바구니 항목 없음"));

        // 장바구니 항목의 소유자가 요청한 사용자와 일치하는지 확인
        if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
            return ResponseEntity.status(403).body("본인의 장바구니가 아닙니다.");
        }

        cartItemRepository.delete(cartItem);
        return ResponseEntity.ok().body(Map.of("success", "delete"));
    }
}
