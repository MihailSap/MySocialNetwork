package ru.example.MySocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.MySocialNetwork.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
