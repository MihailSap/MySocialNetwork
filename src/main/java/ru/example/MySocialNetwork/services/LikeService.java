package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.exceptions.ForbiddenException;
import ru.example.MySocialNetwork.models.Like;
import ru.example.MySocialNetwork.repositories.LikeRepository;
import ru.example.MySocialNetwork.repositories.PostRepository;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PersonService personService;

    @Transactional
    public String likePost(long postId){
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден"));
        var person = personService.getActivePerson();
        var like = likeRepository.findByPersonAndPost(person, post);

        if (like.isPresent()){
            likeRepository.delete(like.get());
            return "Вы убрали лайк с данного поста";
        }

        var owner = post.getPerson();
        if (owner.equals(person)){
            throw new ForbiddenException("Вы не можете ставить лайки на свои посты");
        }

        var newLike = new Like();
        newLike.setPerson(person);
        newLike.setPost(post);
        likeRepository.save(newLike);
        return "Вы поставили лайк на данный пост";
    }
}