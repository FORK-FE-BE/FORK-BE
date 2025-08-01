package com.fork.app.domain.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

//    @Column(nullable = false, length = 255)
//    private String status;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menuOptionId")
//    private MenuOption menuOption;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "orderId")
//    private Order order;
//    @Column(nullable = false)
//    private LocalDateTime createdDate;
//
//    @Column(nullable = false)
//    private LocalDateTime modifiedDate;
}
