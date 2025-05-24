package ru.example.MySocialNetwork.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.MySocialNetwork.dto.CommentDTO;
import ru.example.MySocialNetwork.models.Comment;
import ru.example.MySocialNetwork.repositories.CommentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;

    @Transactional
    public CommentDTO create(long postId, CommentDTO commentDTO){
        var comment = new Comment();
        comment.setText(commentDTO.getText());
        var activePerson = postService.getActivePerson();
        comment.setPerson(activePerson);
        var post = postService.getPost(postId);
        comment.setPost(post);
        commentRepository.save(comment);
        return mapToDTO(comment);
    }

    public CommentDTO mapToDTO(Comment comment){
        var commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        return commentDTO;
    }

//    public List<CommentDTO> getComments(long postId){
//        var post = postService.getPost(postId);
//        var comments = post.getComments();
//        List<CommentDTO> commentDTOS = new ArrayList<>();
//        for(var comment : comments){
//            commentDTOS.add(mapToDTO(comment));
//        }
//        return commentDTOS;
//    }

    @Transactional
    public CommentDTO update(long id, CommentDTO commentDTO){
        var comment = commentRepository.findById(id).get();
        comment.setText(commentDTO.getText());
        return mapToDTO(comment);
    }

    @Transactional
    public void delete(long id){
        var comment = commentRepository.findById(id).get();
        commentRepository.delete(comment);
    }
}
