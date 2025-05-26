package ru.example.MySocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.example.MySocialNetwork.models.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
