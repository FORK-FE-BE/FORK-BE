package com.fork.app.domain.entity;
import com.fork.app.domain.entity.enumtype.MemberRole;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Column(nullable = false)
    private LocalDateTime createdDate; //가입일자

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Address> address= new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    public void changeNickname(String nickname){
        this.name = nickname;
    }
//    @Column(nullable = false, length = 11)
//    private String phone;


//    @Column(length = 255)
//    private String currentAddress;


//    @Column(nullable = false)
//    private LocalDateTime modifiedDate;

//    @Column(length = 20)
//    private String allergy;
}
