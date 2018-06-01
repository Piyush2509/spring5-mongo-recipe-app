package guru.springframework.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final RecipeRepository recipeRepository;
	private final RecipeReactiveRepository recipeReactiveRepository;
	private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository, RecipeReactiveRepository recipeReactiveRepository,
			UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

		return recipeReactiveRepository.findById(recipeId)
				.map(recipe -> recipe.getIngredients().stream()
						.filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId)).findFirst())
				.filter(Optional::isPresent).map(ingredient -> {
					IngredientCommand command = ingredientToIngredientCommand.convert(ingredient.get());
					command.setRecipeId(recipeId);
					return command;
				});
	}

	@Override
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {
			log.error("Recipe not found for id: " + command.getRecipeId());
			return Mono.just(new IngredientCommand());
		} else {
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

			if (ingredientOptional.isPresent()) {
				// update existing ingredient
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(unitOfMeasureReactiveRepository.findById(command.getUom().getId()).block());

				if (ingredientFound.getUom() == null) {
					throw new RuntimeException("UOM not found");
				}
			} else {
				// add new ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
					.filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId())).findFirst();

			// check by other fields
			if (!savedIngredientOptional.isPresent()) {
				// not totally safe.... but best guess
				savedIngredientOptional = savedRecipe.getIngredients().stream()
						.filter(recipeIngredients -> recipeIngredients.getDescription()
								.equals(command.getDescription()))
						.filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
						.filter(recipeIngredients -> recipeIngredients.getUom().getId()
								.equals(command.getUom().getId()))
						.findFirst();
			}

			IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand
					.convert(savedIngredientOptional.get());
			ingredientCommandSaved.setRecipeId(recipe.getId());

			return Mono.just(ingredientCommandSaved);
		}
	}

	@Override
	public Mono<Void> deleteRecipeIngredient(String recipeId, String ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient ingredientToDelete = ingredientOptional.get();
				recipe.getIngredients().remove(ingredientToDelete);
				recipeRepository.save(recipe);
			}
		} else {
			log.error("Recipe not found for id: " + recipeId);
		}

		return Mono.empty();
	}

}
