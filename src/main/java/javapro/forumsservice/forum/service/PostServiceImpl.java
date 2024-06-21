package javapro.forumsservice.forum.service;

import javapro.forumsservice.forum.dao.PostRepository;
import javapro.forumsservice.forum.dto.DatePeriodDto;
import javapro.forumsservice.forum.dto.NewCommentDto;
import javapro.forumsservice.forum.dto.NewPostAddDto;
import javapro.forumsservice.forum.dto.PostDto;
import javapro.forumsservice.forum.dto.exceptions.PostNotFoundException;
import javapro.forumsservice.forum.model.Comment;
import javapro.forumsservice.forum.model.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    final PostRepository postRepository;
    final ModelMapper modelMapper;

    @Override
    public PostDto addNewPost(String author, NewPostAddDto newPostAddDto) {
        Post post = modelMapper.map(newPostAddDto, Post.class);
        post.setAuthor(author);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto removePost(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(String id, NewPostAddDto newPostAddDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        String content = newPostAddDto.getContent();
        if (content != null) {
            post.setContent(content);
        }
        String title = newPostAddDto.getTitle();
        if (title != null) {
            post.setTitle(title);
        }
        Set<String> tags = newPostAddDto.getTags();
        if (tags != null) {
            tags.forEach(post::addTag);
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(author, newCommentDto.getMessage());
        post.addComment(comment);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void addLike(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.addLikes();
        postRepository.save(post);
    }

    @Override
    public Iterable<PostDto> findPostsByAuthor(String author) {
        return postRepository.findByAuthor(author)
                .map(a -> modelMapper.map(a, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<PostDto> findPostsByTags(Set<String> tags) {
        Set<Post> posts = postRepository.findByTagsIn(tags); // Assuming PostRepository has a method findByTagsIn
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
        if (datePeriodDto == null || datePeriodDto.getDateFrom() == null || datePeriodDto.getDateTo() == null) {
            return Collections.emptyList(); // Handle null argument or invalid date period
        }

        LocalDateTime startDate = datePeriodDto.getDateFrom();
        LocalDateTime endDate = datePeriodDto.getDateTo();

        // Преобразование LocalDateTime в Date
        Date start = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant());

        // Использование метода findByDateCreatedBetween из репозитория
        List<Post> filteredPosts = postRepository.findByDateCreatedBetween(start, end);

        // Преобразование в PostDto с использованием ModelMapper
        return filteredPosts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
