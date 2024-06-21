package javapro.forumsservice.forum.dao;

import javapro.forumsservice.forum.model.Post;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public interface PostRepository extends CrudRepository<Post, String> {

    Stream<Post> findByAuthor(String author);

    @Query("{ \"tags\": { \"$in\": ?0 } }")
    Set<Post> findByTagsIn(Set<String> tags);

    @Query("{ 'dateCreated' : { $gte: ?0, $lte: ?1 } }")
    List<Post> findByDateCreatedBetween(Date startDate, Date endDate);
}
