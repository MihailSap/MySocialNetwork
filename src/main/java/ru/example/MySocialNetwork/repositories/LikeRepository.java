package ru.example.MySocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.MySocialNetwork.models.Like;
import ru.example.MySocialNetwork.models.Person;
import ru.example.MySocialNetwork.models.Post;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPersonAndPost(Person user, Post post);
}
