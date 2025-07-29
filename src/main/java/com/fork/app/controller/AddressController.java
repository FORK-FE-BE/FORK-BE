package com.fork.app.controller;

import com.fork.app.domain.dto.request.UserAddressRequestDto;
import com.fork.app.domain.entity.Address;
import com.fork.app.domain.entity.User;
import com.fork.app.service.AddressService;
import com.fork.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "주소 API", description = "주소 관련 API")
public class AddressController {

    private final UserService userService;
    private final AddressService addressService;

    @Operation(summary = "사용자 주소 추가", description = "userId를 기반으로 주소를 추가합니다.")
    @PostMapping("/api/user/{userId}/profile/address")
    public ResponseEntity<?> addAddress(@RequestBody UserAddressRequestDto requestDto, @PathVariable Long userId) {
        User user = userService.findById(userId);
        Address address = requestDto.dtoToEntity();
        user.addAddress(address);
        addressService.save(address);

        return ResponseEntity.ok(Map.of(
                "message", "주소가 성공적으로 등록되었습니다.",
                "addressId", address.getId()
        ));
    }

    @Operation(summary = "사용자 주소 수정", description = "addressId에 해당하는 주소를 수정합니다.")
    @PutMapping("/api/user/{userId}/profile/address/{addressId}")
    public ResponseEntity<?> modifyAddress(@RequestBody UserAddressRequestDto requestDto,
                                           @PathVariable Long userId,
                                           @PathVariable Long addressId) {
        Address updated = requestDto.dtoToEntity(); // user는 service에서 검증
        Address result = addressService.updateAddress(addressId, updated);

        return ResponseEntity.ok(Map.of(
                "message", "주소가 성공적으로 수정되었습니다.",
                "addressId", result.getId()
        ));
    }

    @Operation(summary = "사용자 주소 삭제", description = "addressId에 해당하는 주소를 삭제합니다.")
    @DeleteMapping("/api/user/{userId}/profile/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long userId,
                                           @PathVariable Long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.ok(Map.of(
                "message", "주소가 성공적으로 삭제되었습니다.",
                "addressId", addressId
        ));
    }

    @Operation(summary = "기본 주소 설정", description = "userId와 addressId로 기본 주소를 설정합니다.")
    @PatchMapping("/api/user/{userId}/profile/address/{addressId}/default")
    public ResponseEntity<?> setDefaultAddress(@PathVariable Long userId,
                                               @PathVariable Long addressId) {
        User user = userService.findById(userId);
        addressService.setDefaultAddress(addressId, user);
        return ResponseEntity.ok(Map.of(
                "message", "기본 주소로 설정되었습니다.",
                "addressId", addressId
        ));
    }
}
