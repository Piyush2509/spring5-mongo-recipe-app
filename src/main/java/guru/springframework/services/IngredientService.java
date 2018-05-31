package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

	IngredientCommand saveIngredientCommand(IngredientCommand command);

	void deleteRecipeIngredient(String recipeId, String ingredientId);
}
