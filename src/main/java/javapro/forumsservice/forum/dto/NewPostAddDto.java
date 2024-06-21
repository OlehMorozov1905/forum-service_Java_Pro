package javapro.forumsservice.forum.dto;

import lombok.Getter;


import java.util.Set;

@Getter
public class NewPostAddDto {
    private String title;
    private String content;
    private Set<String> tags; // Set<String> для того, чтобы избежать повторений
}
