package edu.swcoaching.skkumarket.post.repository;

import edu.swcoaching.skkumarket.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
