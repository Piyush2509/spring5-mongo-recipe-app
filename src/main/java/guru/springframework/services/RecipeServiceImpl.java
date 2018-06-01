package guru.springframework.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 21, 2018.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeReactiveRepository recipeReactiveRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository,
			RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeReactiveRepository = recipeReactiveRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Flux<Recipe> getRecipes() {
		log.debug("I'm in the getRecipes service");
		return recipeReactiveRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		log.debug("I'm in the findById service");
		Recipe recipe = recipeReactiveRepository.findById(id).block();
		if (recipe == null) {
			throw new NotFoundException("Recipe Not Found for id: " + id);
		}
		return Mono.just(recipe);
	}

	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		log.debug("I'm in the findCommandById service");
		return Mono.just(recipeToRecipeCommand.convert(findById(id).block()));
	}

	@Override
	@Transactional
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
		log.debug("I'm in the saveRecipeCommand service");
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe).block();
		log.debug("Saved Recipe Id:" + savedRecipe.getId());
		return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
	}

	@Override
	public Mono<Void> deleteById(String id) {
		log.debug("I'm in the deleteById service");
		recipeReactiveRepository.deleteById(id).block();
		log.debug("Deleted Recipe Id:" + id);
		return Mono.empty();
	}

}
