package guru.springframework.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
@Getter
@Setter
public class Notes {

	private String id;
	private String recipeNotes;
	private Recipe recipe;

}
