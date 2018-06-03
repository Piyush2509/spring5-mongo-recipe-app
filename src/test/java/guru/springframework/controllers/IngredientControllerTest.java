package guru.springframework.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 24, 2018
 */
@Ignore
public class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredientService;

	@Mock
	UnitOfMeasureService unitOfMeasureService;

	IngredientController ingredientController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();

		// when
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));

		// then
		mockMvc.perform(get("/recipe/1/ingredients")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(anyString());
	}

	@Test
	public void testShowRecipeIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
				.thenReturn(Mono.just(ingredientCommand));

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/show")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show")).andExpect(model().attributeExists("ingredient"));
	}

	@Test
	public void testNewRecipeIngredient() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");

		// when
		when(recipeService.findCommandById(anyString())).thenReturn(Mono.just(recipeCommand));
		when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

		// then
		mockMvc.perform(get("/recipe/1/ingredient/new")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));

		verify(recipeService, times(1)).findCommandById(anyString());
	}

	@Test
	public void testUpdateRecipeIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findByRecipeIdAndIngredientId(anyString(), anyString()))
				.thenReturn(Mono.just(ingredientCommand));
		when(unitOfMeasureService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/update")).andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/ingredientform"))
				.andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));
	}

	@Test
	public void testSaveRecipeIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId("3");
		ingredientCommand.setRecipeId("2");

		// when
		when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(ingredientCommand));

		// then
		mockMvc.perform(post("/recipe/2/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
	}

	@Test
	public void testDeleteRecipeIngredient() throws Exception {
		// when
		when(ingredientService.deleteRecipeIngredient(anyString(), anyString())).thenReturn(Mono.empty());

		// then
		mockMvc.perform(get("/recipe/2/ingredient/3/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/2/ingredients"));

		verify(ingredientService, times(1)).deleteRecipeIngredient(anyString(), anyString());
	}

}
