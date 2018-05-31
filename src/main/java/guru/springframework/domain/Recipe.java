package guru.springframework.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
@Getter
@Setter
public class Recipe {

	private String id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Set<Ingredient> ingredients = new HashSet<>();
	private Byte[] image;
	private Difficulty difficulty;
	private Notes notes;
	private Set<Category> categories = new HashSet<>();

	public Recipe addIngredient(Ingredient ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}

	public void setNotes(Notes notes) {
		if (notes != null) {
			this.notes = notes;
			notes.setRecipe(this);
		}
	}

}
