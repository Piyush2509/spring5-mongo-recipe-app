package guru.springframework.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.Recipe;

/**
 * Created by piyush.b.kumar on May 31, 2018
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;

	@Before
	public void setUp() throws Exception {
		recipeReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSave() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setDescription("Yummy");

		recipeReactiveRepository.save(recipe).block();

		// when
		Long count = recipeReactiveRepository.count().block();

		// then
		assertEquals(Long.valueOf(1L), count);
	}
	
}
