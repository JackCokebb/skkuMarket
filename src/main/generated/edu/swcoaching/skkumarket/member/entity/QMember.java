package edu.swcoaching.skkumarket.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1005822729L;

    public static final QMember member = new QMember("member1");

    public final edu.swcoaching.skkumarket.audit.QAuditable _super = new edu.swcoaching.skkumarket.audit.QAuditable(this);

    public final EnumPath<Member.Authority> authority = createEnum("authority", Member.Authority.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final ListPath<edu.swcoaching.skkumarket.post.entity.Post, edu.swcoaching.skkumarket.post.entity.QPost> posts = this.<edu.swcoaching.skkumarket.post.entity.Post, edu.swcoaching.skkumarket.post.entity.QPost>createList("posts", edu.swcoaching.skkumarket.post.entity.Post.class, edu.swcoaching.skkumarket.post.entity.QPost.class, PathInits.DIRECT2);

    public final EnumPath<Member.Status> status = createEnum("status", Member.Status.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

