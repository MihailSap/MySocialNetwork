package ru.example.MySocialNetwork.mappers;

import org.springframework.stereotype.Component;
import ru.example.MySocialNetwork.dto.CommentDTO;
import ru.example.MySocialNetwork.dto.PostDTO;
import ru.example.MySocialNetwork.models.Post;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    public PostDTO mapToDTO(Post post){
        var postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setText(post.getText());

        var commentDTOs = mapComments(post);
        postDTO.setComments(commentDTOs);

        var likesCount = post.getLikes().size();
        postDTO.setLikesCount(likesCount);
        return postDTO;
    }

    public List<CommentDTO> mapComments(Post post){
        var comments = post.getComments();
        if (comments == null){
            return new ArrayList<>();
        }

        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(var comment : comments){
            var commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setText(comment.getText());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
