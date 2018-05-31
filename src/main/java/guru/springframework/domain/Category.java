package guru.springframework.domain;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
@Getter
@Setter
public class Category {

	private String id;
	private String description;
	private Set<Recipe> recipes;

}
