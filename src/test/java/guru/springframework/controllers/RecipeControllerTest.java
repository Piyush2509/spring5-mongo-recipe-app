package guru.springframework.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

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

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.CategoryService;
import guru.springframework.services.RecipeService;

/**
 * Created by piyush.b.kumar on May 22, 2018.
 */
public class RecipeControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	CategoryService categoryService;

	RecipeController recipeController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService, categoryService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	public void testShowById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");

		when(recipeService.findById(anyString())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testShowByIdNotFound() throws Exception {
		when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isNotFound()).andExpect(view().name("404error"));
	}

	@Test
	public void testShowByIdNumberFormatException() throws Exception {
		mockMvc.perform(get("/recipe/asdf/show")).andExpect(status().isBadRequest()).andExpect(view().name("400error"));
	}

	@Test
	public void testNewRecipe() throws Exception {
		when(categoryService.listAllCategories()).thenReturn(new HashSet<>());

		mockMvc.perform(get("/recipe/new")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe")).andExpect(model().attributeExists("categoryList"));

		verify(categoryService, times(1)).listAllCategories();
	}

	@Test
	public void testUpdateRecipe() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");

		when(recipeService.findCommandById(anyString())).thenReturn(command);
		when(categoryService.listAllCategories()).thenReturn(new HashSet<>());

		mockMvc.perform(get("/recipe/1/update")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform"))
				.andExpect(model().attributeExists("recipe")).andExpect(model().attributeExists("categoryList"));

		verify(recipeService, times(1)).findCommandById(anyString());
		verify(categoryService, times(1)).listAllCategories();
	}

	@Test
	public void testSaveRecipe() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
				.param("description", "some string").param("directions", "some directions").param("categoryArray", ""))
				.andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/show"));
	}

	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");

		when(recipeService.saveRecipeCommand(any())).thenReturn(command);

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")
				.param("categoryArray", "")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"))
				.andExpect(view().name("recipe/recipeform"));
	}

	@Test
	public void testDeleteById() throws Exception {
		mockMvc.perform(get("/recipe/1/delete")).andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		verify(recipeService, times(1)).deleteById(anyString());
	}

}
