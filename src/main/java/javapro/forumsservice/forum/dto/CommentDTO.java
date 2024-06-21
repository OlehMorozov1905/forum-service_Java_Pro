package javapro.forumsservice.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private Integer likes;

}
