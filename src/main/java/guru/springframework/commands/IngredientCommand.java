package guru.springframework.commands;

import java.math.BigDecimal;

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
	
	private Long id;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasureCommand unitOfMeasure;

}
