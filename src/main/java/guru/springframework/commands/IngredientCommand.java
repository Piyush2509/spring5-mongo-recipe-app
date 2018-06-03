package guru.springframework.commands;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {

	private String id;
	private String recipeId;
	@NotBlank
	private String description;
	@NotNull
	@Min(1)
	private BigDecimal amount;
	@NotNull
	private UnitOfMeasureCommand uom;

}
