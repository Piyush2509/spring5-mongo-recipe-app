package guru.springframework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 21, 2018.
 */
public class RecipeServiceImplTest {

	RecipeService recipeService;

	@Mock
	RecipeReactiveRepository recipeReactiveRepository;

	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;

	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeReactiveRepository, recipeCommandToRecipe, recipeToRecipeCommand);
	}

	@Test
	public void getRecipesTest() throws Exception {
		when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe()));

		List<Recipe> recipes = recipeService.getRecipes().collectList().block();
		assertEquals(recipes.size(), 1);
		verify(recipeReactiveRepository, times(1)).findAll();
		verify(recipeReactiveRepository, never()).findById(anyString());
	}

	@Test
	public void findByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		Recipe recipeReturned = recipeService.findById("1").block();

		assertNotNull("Null recipe returned", recipeReturned);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test(expected = NotFoundException.class)
	public void findByIdTestNotFound() throws Exception {
		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.empty());

		recipeService.findById("1");
	}

	@Test
	public void findCommandByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeReactiveRepository.findById(anyString())).thenReturn(Mono.just(recipe));

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);

		RecipeCommand commandById = recipeService.findCommandById("1").block();

		assertNotNull("Null recipe returned", commandById);
		verify(recipeReactiveRepository, times(1)).findById(anyString());
		verify(recipeReactiveRepository, never()).findAll();
	}

	@Test
	public void deleteByIdTest() throws Exception {
		String idToDelete = "2";
		recipeService.deleteById(idToDelete).block();

		verify(recipeReactiveRepository, times(1)).deleteById(anyString());
	}

}
