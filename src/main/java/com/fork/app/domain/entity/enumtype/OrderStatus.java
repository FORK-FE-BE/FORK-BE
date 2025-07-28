package com.fork.app.domain.entity.enumtype;

public enum OrderStatus {
    READY,         // 주문 접수 및 준비 완료
    IN_PROGRESS,   // 조리 중
    DELIVERY,      // 배송 중
    DELIVERED,     // 배송 완료
    CANCELLED      // 주문 취소
}
