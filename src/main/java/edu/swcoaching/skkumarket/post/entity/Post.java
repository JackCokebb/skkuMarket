package edu.swcoaching.skkumarket.post.entity;

import edu.swcoaching.skkumarket.audit.Auditable;
import edu.swcoaching.skkumarket.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private String image;
    @Column(nullable = false)
    private Integer price;
    @Column
    private String description;
    @Column(nullable = false)
    private Status status=Status.ACTIVE;
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getPosts().contains(this)) {
            this.member.getPosts().add(this);
        }
    }

    public enum Status{
        ACTIVE,
        DELETED
    }
}
