package ru.example.MySocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.MySocialNetwork.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
