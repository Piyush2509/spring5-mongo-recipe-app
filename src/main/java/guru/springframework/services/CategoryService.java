package guru.springframework.services;

import guru.springframework.commands.CategoryCommand;
import reactor.core.publisher.Flux;

/**
 * Created by piyush.b.kumar on May 25, 2018.
 */
public interface CategoryService {

	Flux<CategoryCommand> listAllCategories();

}
