package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService,
			UnitOfMeasureService unitOfMeasureService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
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
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block());
		return "recipe/ingredient/show";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String newRecipeIngredient(@PathVariable String recipeId, Model model) {
		log.debug("Getting new ingredient for a recipe form");
		// make sure we have a good recipe id value
		@SuppressWarnings("unused")
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

		// need to return back parent id for hidden form property
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeId);
		// init uom
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms().collectList().block());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.debug("Getting ingredient form for update for ingredient id: " + id + " for recipe id: " + recipeId);
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(recipeId, id).block());
		model.addAttribute("uomList", unitOfMeasureService.listAllUoms().collectList().block());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveRecipeIngredient(@ModelAttribute IngredientCommand command) {
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

}
