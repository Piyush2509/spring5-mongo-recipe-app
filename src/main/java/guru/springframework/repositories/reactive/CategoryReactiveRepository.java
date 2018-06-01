package guru.springframework.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.domain.Category;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 31, 2018
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

	Mono<Category> findByDescription(String description);

}
