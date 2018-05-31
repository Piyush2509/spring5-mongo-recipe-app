package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.converters.CategoryToCategoryCommand;
import guru.springframework.domain.Category;
import guru.springframework.repositories.CategoryRepository;

/**
 * Created by piyush.b.kumar on May 25, 2018.
 */
public class CategoryServiceImplTest {

	CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();

	CategoryService categoryService;

	@Mock
	CategoryRepository categoryRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		categoryService = new CategoryServiceImpl(categoryRepository, categoryToCategoryCommand);
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
		when(categoryRepository.findAll()).thenReturn(categories);
		Set<CategoryCommand> commands = categoryService.listAllCategories();

		// then
		assertEquals(2, commands.size());
		verify(categoryRepository, times(1)).findAll();
	}

}
