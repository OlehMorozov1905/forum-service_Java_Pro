package javapro.forumsservice.forum.service;

import javapro.forumsservice.forum.dto.NewCommentDto;
import javapro.forumsservice.forum.dto.DatePeriodDto;
import javapro.forumsservice.forum.dto.NewPostAddDto;
import javapro.forumsservice.forum.dto.PostDto;

import java.util.Set;

public interface PostService {
    PostDto addNewPost(String author, NewPostAddDto newPostAddDto);
    PostDto findPostById(String id);
    PostDto removePost(String id);
    PostDto updatePost(String id, NewPostAddDto newPostAddDto);
    PostDto addComment(String id, String author, NewCommentDto newCommentDto);
    void addLike(String id);
    Iterable<PostDto> findPostsByAuthor(String author);
    Iterable<PostDto> findPostsByTags(Set<String> tags);
    Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto);
}
