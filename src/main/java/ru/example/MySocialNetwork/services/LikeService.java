package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.exceptions.ForbiddenException;
import ru.example.MySocialNetwork.models.Like;
import ru.example.MySocialNetwork.repositories.LikeRepository;
import ru.example.MySocialNetwork.repositories.PublicationRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PublicationRepository publicationRepository;
    private final PersonService personService;

    @Transactional
    public String likePublication(long publicationId){
        var post = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Публикация не найдена"));
        var person = personService.getActivePerson();
        var like = likeRepository.findByPersonAndPublication(person, post);

        if (like.isPresent()){
            likeRepository.delete(like.get());
            return "Вы убрали лайк с данной публикации";
        }

        var owner = post.getPerson();
        if (owner.equals(person)){
            throw new ForbiddenException("Вы не можете ставить лайки на свои публикации");
        }

        var newLike = new Like();
        newLike.setPerson(person);
        newLike.setPublication(post);
        likeRepository.save(newLike);
        return "Вы поставили лайк на данный пост";
    }
}