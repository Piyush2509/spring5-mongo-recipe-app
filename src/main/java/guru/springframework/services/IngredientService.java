package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public interface IngredientService {

	Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

	Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

	Mono<Void> deleteRecipeIngredient(String recipeId, String ingredientId);
}
