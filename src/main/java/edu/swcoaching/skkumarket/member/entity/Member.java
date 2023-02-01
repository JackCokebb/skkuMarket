package edu.swcoaching.skkumarket.member.entity;

import edu.swcoaching.skkumarket.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
public class Member extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private Status status=Status.ACTIVE;
    private Authority authority=Authority.ROLE_USER;

    public enum Status{
        ACTIVE,
        DELETED
    }

    public enum Authority{
        ROLE_ADMIN,
        ROLE_USER
    }
    public void updatePassword(String newPassword){
        this.password = newPassword;
    }
    public void updateNickname(String newNickname){
        this.nickname = newNickname;
    }
}
