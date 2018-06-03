package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.converters.CategoryToCategoryCommand;
import guru.springframework.domain.Category;
import guru.springframework.repositories.reactive.CategoryReactiveRepository;
import reactor.core.publisher.Flux;

/**
 * Created by piyush.b.kumar on May 25, 2018.
 */
public class CategoryServiceImplTest {

	CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();

	CategoryService categoryService;

	@Mock
	CategoryReactiveRepository categoryReactiveRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		categoryService = new CategoryServiceImpl(categoryReactiveRepository, categoryToCategoryCommand);
	}

	@Test
	public void listAllCategoriesTest() throws Exception {
		// given
		Set<Category> categories = new HashSet<>();
		Category category1 = new Category();
		category1.setId("1");
		categories.add(category1);

		Category category2 = new Category();
		category2.setId("2");
		categories.add(category2);

		// when
		when(categoryReactiveRepository.findAll()).thenReturn(Flux.fromIterable(categories));
		List<CategoryCommand> commands = categoryService.listAllCategories().collectList().block();

		// then
		assertEquals(2, commands.size());
		verify(categoryReactiveRepository, times(1)).findAll();
	}

}
