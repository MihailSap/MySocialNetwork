package ru.example.MySocialNetwork.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.dto.PostDTO;
import ru.example.MySocialNetwork.models.Person;
import ru.example.MySocialNetwork.models.Post;
import ru.example.MySocialNetwork.repositories.PersonRepository;
import ru.example.MySocialNetwork.repositories.PostRepository;
import ru.example.MySocialNetwork.security.PersonDetails;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PersonRepository peopleRepository;

    @Autowired
    public PostService(PostRepository postRepository, PersonRepository personRepository) {
        this.postRepository = postRepository;
        this.peopleRepository = personRepository;
    }

    @Transactional
    public PostDTO create(PostDTO postDTO){
        var post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());

        var activePerson = getActivePerson();
        post.setPerson(activePerson);
        postRepository.save(post);

        return mapToDTO(post);
    }

    public PostDTO getPostById(long id){
        var post = postRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
        return mapToDTO(post);
    }

    @Transactional
    public PostDTO update(long id, PostDTO postDTO){
        var post = getPost(id);
        post.setTitle(postDTO.getTitle());
        post.setText(postDTO.getText());
        postRepository.save(post);
        return mapToDTO(post);
    }

    @Transactional
    public void delete(long id){
        var post = postRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
        postRepository.delete(post);
    }

    public Post getPost(long id){
        return postRepository.findById(id)
                .orElseThrow(() ->  new RuntimeException("Пост с таким id не найден"));
    }

    public PostDTO mapToDTO(Post post){
        var postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setText(post.getText());
        return postDTO;
    }

    public Person getActivePerson() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof PersonDetails personDetails) {
            return peopleRepository.findById(personDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        }

        throw new RuntimeException("Пользователь не авторизован");
    }
}
