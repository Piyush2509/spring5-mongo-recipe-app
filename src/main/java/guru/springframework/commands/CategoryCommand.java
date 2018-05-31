package guru.springframework.commands;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryCommand {

	private String id;
	private String description;

}
