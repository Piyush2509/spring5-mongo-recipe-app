package guru.springframework.services;

import org.springframework.stereotype.Service;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.converters.CategoryToCategoryCommand;
import guru.springframework.repositories.reactive.CategoryReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * Created by piyush.b.kumar on May 25, 2018.
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryReactiveRepository categoryReactiveRepository;
	private final CategoryToCategoryCommand categoryToCategoryCommand;

	public CategoryServiceImpl(CategoryReactiveRepository categoryReactiveRepository,
			CategoryToCategoryCommand categoryToCategoryCommand) {
		this.categoryReactiveRepository = categoryReactiveRepository;
		this.categoryToCategoryCommand = categoryToCategoryCommand;
	}

	@Override
	public Flux<CategoryCommand> listAllCategories() {
		log.debug("I'm in the listAllCategories service");
		return categoryReactiveRepository.findAll().map(categoryToCategoryCommand::convert);
	}

}
