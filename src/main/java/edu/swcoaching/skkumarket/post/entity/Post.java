package edu.swcoaching.skkumarket.post.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getPosts().contains(this)) {
            this.member.getPosts().add(this);
        }
    }

    public Post(String title,
                String image,
                int price,
                String description,
                Post.Status status
                ){
        this.title = title;
        this.image = image;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    public enum Status{
        ACTIVE,
        DELETED
    }
}
