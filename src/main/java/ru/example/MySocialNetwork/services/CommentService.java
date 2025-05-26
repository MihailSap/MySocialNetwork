package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.dto.CommentDTO;
import ru.example.MySocialNetwork.exceptions.ForbiddenException;
import ru.example.MySocialNetwork.models.Comment;
import ru.example.MySocialNetwork.repositories.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PublicationService publicationService;
    private final PersonService personService;

    @Transactional
    public CommentDTO create(long postId, CommentDTO commentDTO){
        var comment = new Comment();
        comment.setText(commentDTO.getText());
        var activePerson = personService.getActivePerson();
        comment.setPerson(activePerson);
        var post = publicationService.getPublications(postId);
        comment.setPublication(post);
        commentRepository.save(comment);
        return mapToDTO(comment);
    }

    public CommentDTO mapToDTO(Comment comment){
        var commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

    @Transactional
    public CommentDTO update(long id, CommentDTO commentDTO){
        var comment = commentRepository.findById(id).get();
        checkRights(comment);
        comment.setText(commentDTO.getText());
        return mapToDTO(comment);
    }

    @Transactional
    public void delete(long id){
        var comment = commentRepository.findById(id).get();
        checkRights(comment);
        commentRepository.delete(comment);
    }

    public void checkRights(Comment comment){
        var commentAuthor = comment.getPerson();
        var activePerson = personService.getActivePerson();
        if (!commentAuthor.equals(activePerson)){
            throw new ForbiddenException("Вы не можете редактировать чужие комментарии");
        }
    }
}
