package com.fork.app.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemOption {
    @Id
    @GeneratedValue
    private Long id;

    private String groupName;
    private String optionName;
    private int optionPrice;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
}

