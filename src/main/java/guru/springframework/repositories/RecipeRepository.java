package guru.springframework.repositories;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Recipe;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
public interface RecipeRepository extends CrudRepository<Recipe, String> {

}
