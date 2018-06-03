package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
@Slf4j
@Controller
public class IngredientController {

	private static final String RECIPE_INGREDIENT_INGREDIENTFORM = "recipe/ingredient/ingredientform";

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	private WebDataBinder webDataBinder;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@InitBinder("ingredient")
	public void initBinder(WebDataBinder webDataBinder) {
		this.webDataBinder = webDataBinder;
	}

	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.debug("Getting ingredient list for recipe id: " + recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(recipeId));
		return "recipe/ingredient/list";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Getting ingredient id: " + id + " for recipe id: " + recipeId);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String newRecipeIngredient(@PathVariable String recipeId, Model model) {
		log.debug("Getting new ingredient for a recipe form");
		// need to return back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		// init uom
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("ingredient", ingredientCommand);
		return RECIPE_INGREDIENT_INGREDIENTFORM;
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Getting ingredient form for update for ingredient id: " + id + " for recipe id: " + recipeId);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block());
		return RECIPE_INGREDIENT_INGREDIENTFORM;
	}

	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveRecipeIngredient(@ModelAttribute("ingredient") IngredientCommand command, Model model) {
		webDataBinder.validate();
		BindingResult bindingResult = webDataBinder.getBindingResult();

		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});

			return RECIPE_INGREDIENT_INGREDIENTFORM;
		}

		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();
		log.debug("Saved recipe id: " + savedCommand.getRecipeId());
		log.debug("Saved ingredient id: " + savedCommand.getId());
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteRecipeIngredient(@PathVariable String recipeId, @PathVariable String id) {
		log.debug("Deleting ingredient id: " + id + " for recipe id: " + recipeId);
		ingredientService.deleteRecipeIngredient(recipeId, id).block();
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}

	@ModelAttribute("uomList")
	public Flux<UnitOfMeasureCommand> populateUomList() {
		return unitOfMeasureService.listAllUoms();
	}

}
