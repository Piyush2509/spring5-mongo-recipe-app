package guru.springframework.repositories.reactive;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import guru.springframework.domain.Recipe;

/**
 * Created by piyush.b.kumar on May 31, 2018
 */
public interface RecipeReactiveRepository extends ReactiveMongoRepository<Recipe, String> {

}
