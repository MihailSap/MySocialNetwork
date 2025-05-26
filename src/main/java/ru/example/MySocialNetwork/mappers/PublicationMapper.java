package ru.example.MySocialNetwork.mappers;

import org.springframework.stereotype.Component;
import ru.example.MySocialNetwork.dto.CommentDTO;
import ru.example.MySocialNetwork.dto.PublicationDTO;
import ru.example.MySocialNetwork.models.Publication;

import java.util.ArrayList;
import java.util.List;

@Component
public class PublicationMapper {

    public PublicationDTO mapToDTO(Publication publication){
        var postDTO = new PublicationDTO();
        postDTO.setId(publication.getId());
        postDTO.setTitle(publication.getTitle());
        postDTO.setText(publication.getText());
        postDTO.setCreatedTime(publication.getCreatedAt());

        var commentDTOs = mapComments(publication);
        postDTO.setComments(commentDTOs);

        var likesCount = publication.getLikes().size();
        postDTO.setLikesCount(likesCount);
        return postDTO;
    }

    public List<CommentDTO> mapComments(Publication publication){
        var comments = publication.getComments();
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
