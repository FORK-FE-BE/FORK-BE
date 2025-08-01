package com.fork.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    private int quantity;
    private String selectedOptions; // 예: 토핑/사이즈 등
}