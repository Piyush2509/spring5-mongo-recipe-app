package guru.springframework.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
	
	private Long id;
	private String description;

}
