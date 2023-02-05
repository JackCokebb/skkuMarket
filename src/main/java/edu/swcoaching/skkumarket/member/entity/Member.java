package edu.swcoaching.skkumarket.member.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.swcoaching.skkumarket.audit.Auditable;
import edu.swcoaching.skkumarket.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
//@Builder(access = AccessLevel.PRIVATE)
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
    @Column
    private Authority authority=Authority.ROLE_USER;
    @JsonManagedReference
    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();
    public void addPost(Post post) {
        this.posts.add(post);
        if (post.getMember() != this) {
            post.addMember(this);
        }
    }

    public enum Status{
        ACTIVE,
        DELETED //soft delete -> JPAㅇㅔ서 지원하는 방식도 있음
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
