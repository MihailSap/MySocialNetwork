package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.dto.PublicationDTO;
import ru.example.MySocialNetwork.exceptions.ForbiddenException;
import ru.example.MySocialNetwork.mappers.PublicationMapper;
import ru.example.MySocialNetwork.models.Publication;
import ru.example.MySocialNetwork.repositories.PublicationRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PersonService personService;
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    @Transactional
    public PublicationDTO create(PublicationDTO publicationDTO){
        var publication = new Publication();
        publication.setTitle(publicationDTO.getTitle());
        publication.setText(publicationDTO.getText());
        publication.setCreatedAt(LocalDateTime.now());

        var activePerson = personService.getActivePerson();
        publication.setPerson(activePerson);
        publicationRepository.save(publication);

        return publicationMapper.mapToDTO(publication);
    }

    public PublicationDTO getPublicationById(long id){
        var post = publicationRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
        return publicationMapper.mapToDTO(post);
    }

    @Transactional
    public PublicationDTO update(long id, PublicationDTO publicationDTO){
        var post = getPublications(id);
        checkRights(post);

        post.setTitle(publicationDTO.getTitle());
        post.setText(publicationDTO.getText());
        publicationRepository.save(post);
        return publicationMapper.mapToDTO(post);
    }

    @Transactional
    public void delete(long id){
        var post = publicationRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
        checkRights(post);
        publicationRepository.delete(post);
    }

    public List<PublicationDTO> getAllSortedPublications(){
        var posts = publicationRepository.findAll()
                .stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .toList();

        List<PublicationDTO> publicationDTOS = new ArrayList<>();
        for (var post : posts) {
            publicationDTOS.add(publicationMapper.mapToDTO(post));
        }

        return publicationDTOS;
    }

    public Publication getPublications(long id){
        return publicationRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
    }

    public void checkRights(Publication publication){
        var activePerson = personService.getActivePerson();
        var owner = publication.getPerson();
        if (!owner.equals(activePerson)) {
            throw new ForbiddenException("Вы не можете менять чужие посты");
        }
    }
}
