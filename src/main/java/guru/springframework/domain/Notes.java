package guru.springframework.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
@Getter
@Setter
public class Notes {

	@Id
	private String id;
	private String recipeNotes;

}
