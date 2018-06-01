package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import guru.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public class IngredientServiceImplTest {

	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	IngredientService ingredientService;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
				new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
				new UnitOfMeasureCommandToUnitOfMeasure());
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientService = new IngredientServiceImpl(recipeReactiveRepository, unitOfMeasureReactiveRepository,
				ingredientToIngredientCommand, ingredientCommandToIngredient);
	}

	@Test
	public void findByRecipeIdAndIngredientIdTest() throws Exception {
	}

	@Test
	public void findByRecipeIdAndIngredientIdHappyPathTest() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setId("1");

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId("1");

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId("2");

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId("3");

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		// when
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId("1", "3").block();

		// then
		assertEquals("3", ingredientCommand.getId());
		assertEquals("1", ingredientCommand.getRecipeId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
	}

	@Test
	public void saveIngredientCommandTest() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId("3");
		command.setRecipeId("2");

		Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredient());
		savedRecipe.getIngredients().iterator().next().setId("3");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(savedRecipe));

		// when
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

		// then
		assertEquals("3", savedCommand.getId());
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
	}

	@Test
	public void deleteRecipeIngredientTest() throws Exception {
		// given
		Recipe recipe = new Recipe();
		Ingredient ingredient = new Ingredient();
		ingredient.setId("3");
		recipe.addIngredient(ingredient);

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		when(recipeReactiveRepository.save(any())).thenReturn(Mono.just(recipe));

		// when
		ingredientService.deleteRecipeIngredient("1", "3");

		// then
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, times(1)).save(any(Recipe.class));
	}

}
