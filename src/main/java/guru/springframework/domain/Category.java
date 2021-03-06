package guru.springframework.domain;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
@Getter
@Setter
@Document
public class Category {

	@Id
	private String id;
	private String description;
	private Set<Recipe> recipes;

}
