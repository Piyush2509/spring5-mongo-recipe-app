package guru.springframework.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.exceptions.TemplateInputException;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.CategoryService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by piyush.b.kumar on May 22, 2018.
 */
@Slf4j
@Controller
public class RecipeController {

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";

	private final RecipeService recipeService;

	private final CategoryService categoryService;

	private WebDataBinder webDataBinder;

	public RecipeController(RecipeService recipeService, CategoryService categoryService) {
		this.recipeService = recipeService;
		this.categoryService = categoryService;
	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}

	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		log.debug("Getting recipe page for id: " + id);
		model.addAttribute("recipe", recipeService.findById(id));
		return "recipe/show";
	}

	@GetMapping("recipe/new")
	public String newRecipe(Model model) {
		log.debug("Getting new recipe form");
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		log.debug("Getting recipe form for update for id: " + id);
		model.addAttribute("recipe", recipeService.findCommandById(id).block());
		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping("recipe")
	public String saveRecipe(@ModelAttribute("recipe") RecipeCommand command,
			@RequestParam(value = "categoryArray", required = false) String[] categoryArray) {
		log.debug("Saving or updating recipe");

		webDataBinder.validate();
		BindingResult bindingResult = webDataBinder.getBindingResult();

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});

			return RECIPE_RECIPEFORM_URL;
		}

		List<CategoryCommand> categoryList = categoryService.listAllCategories().collectList().block();
		List<String> list = Arrays.asList(categoryArray);
		List<CategoryCommand> recipeCategories = categoryList.stream()
				.filter(category -> list.contains(category.getId().toString())).collect(Collectors.toList());
		command.setCategories(recipeCategories);
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@GetMapping("/recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("Deleting recipe: " + id);
		recipeService.deleteById(id).block();
		return "redirect:/";
	}

	@ModelAttribute("categoryList")
	public Flux<CategoryCommand> populateCategoryList() {
		return categoryService.listAllCategories();
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class, TemplateInputException.class })
	public String handleNotFound(Exception exception, Model model) {
		log.error("Handling not found exception");
		log.error(exception.getMessage());
		model.addAttribute("exception", exception);
		return "404error";
	}

}
