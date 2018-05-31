package guru.springframework.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by piyush.b.kumar on May 21, 2018.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;

	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in the getRecipes service");
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

	@Override
	public Recipe findById(String id) {
		log.debug("I'm in the findById service");
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		if (!recipeOptional.isPresent()) {
			throw new NotFoundException("Recipe Not Found for id: " + id);
		}
		return recipeOptional.get();
	}

	@Override
	@Transactional
	public RecipeCommand findCommandById(String id) {
		log.debug("I'm in the findCommandById service");
		return recipeToRecipeCommand.convert(findById(id));
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		log.debug("I'm in the saveRecipeCommand service");
		Recipe detachedRecipe = recipeCommandToRecipe.convert(command);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved Recipe Id:" + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	@Override
	public void deleteById(String id) {
		log.debug("I'm in the deleteById service");
		recipeRepository.deleteById(id);
		log.debug("Deleted Recipe Id:" + id);
	}

}
