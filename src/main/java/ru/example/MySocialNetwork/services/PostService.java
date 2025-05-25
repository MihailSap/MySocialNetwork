package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.dto.PostDTO;
import ru.example.MySocialNetwork.exceptions.ForbiddenException;
import ru.example.MySocialNetwork.mappers.PostMapper;
import ru.example.MySocialNetwork.models.Post;
import ru.example.MySocialNetwork.repositories.PostRepository;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PersonService personService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Transactional
    public PostDTO create(PostDTO postDTO){
        var post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());

        var activePerson = personService.getActivePerson();
        post.setPerson(activePerson);
        postRepository.save(post);

        return postMapper.mapToDTO(post);
    }

    public PostDTO getPostById(long id){
        var post = postRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
        return postMapper.mapToDTO(post);
    }

    @Transactional
    public PostDTO update(long id, PostDTO postDTO){
        var post = getPost(id);
        checkRights(post);

        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        postRepository.save(post);
        return postMapper.mapToDTO(post);
    }

    @Transactional
    public void delete(long id){
        var post = postRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
        checkRights(post);
        postRepository.delete(post);
    }

    public Post getPost(long id){
        return postRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
    }

    public void checkRights(Post post){
        var activePerson = personService.getActivePerson();
        var owner = post.getPerson();
        if (!owner.equals(activePerson)) {
            throw new ForbiddenException("Вы не можете менять чужие посты");
        }
    }
}
