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
        var publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setText(publication.getText());
        publicationDTO.setCreatedTime(publication.getCreatedAt());
        publicationDTO.setAuthorName(publication.getPerson().getUsername());

        var commentDTOs = mapComments(publication);
        publicationDTO.setComments(commentDTOs);

        var likesCount = publication.getLikes().size();
        publicationDTO.setLikesCount(likesCount);
        return publicationDTO;
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
            commentDTO.setAuthorName(comment.getPerson().getUsername());
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
