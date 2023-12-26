package pl.edu.pw.pamiwapi.model.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private UserRole role = UserRole.User;
}
