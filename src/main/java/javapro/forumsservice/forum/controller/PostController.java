package javapro.forumsservice.forum.controller;

import javapro.forumsservice.forum.dto.NewCommentDto;
import javapro.forumsservice.forum.dto.DatePeriodDto;
import javapro.forumsservice.forum.dto.NewPostAddDto;
import javapro.forumsservice.forum.dto.PostDto;
import javapro.forumsservice.forum.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")  //теперь можно не писать  "/forum"  в начале всех ендпоинтов
public class PostController {

    private final PostService postService;

    @PostMapping("/post/{user}")
    public PostDto addNewPost(@PathVariable String user, @RequestBody NewPostAddDto postAddDto) {
        return postService.addNewPost(user, postAddDto);
    }

    @GetMapping("/post/{postId}")
    public PostDto findPostById(@PathVariable String postId) {
        return postService.findPostById(postId);
    }

    @DeleteMapping("/post/{postId}")
    public PostDto removePost(@PathVariable String postId) {
        return postService.removePost(postId);
    }

    @PutMapping("/post/{postId}")
    public PostDto updatePost(@PathVariable String postId, @RequestBody NewPostAddDto newPostAddDto) {
        return postService.updatePost(postId, newPostAddDto);
    }

    @PutMapping("/post/{postId}/comment/{user}")
    public PostDto addComment(@PathVariable String postId, @PathVariable String user, @RequestBody NewCommentDto commentDto) {
        return postService.addComment(postId, user, commentDto);
    }

    @PutMapping("/post/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable String postId) {
        postService.addLike(postId);
    }

    @GetMapping("/posts/author/{user}")
    public Iterable<PostDto> findPostsByAuthor(@PathVariable String user) {
        return postService.findPostsByAuthor(user);
    }

    @PostMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(@RequestBody Set<String> tags) {
        return postService.findPostsByTags(tags);
    }

    @PostMapping("/posts/period")
    public Iterable<PostDto> findPostsByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
        return postService.findPostsByPeriod(datePeriodDto);
    }
}
