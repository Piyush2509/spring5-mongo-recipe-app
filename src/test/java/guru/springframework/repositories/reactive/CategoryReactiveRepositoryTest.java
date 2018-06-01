package guru.springframework.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.Category;

/**
 * Created by piyush.b.kumar on May 31, 2018
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {

	@Autowired
	CategoryReactiveRepository categoryReactiveRepository;

	@Before
	public void setUp() throws Exception {
		categoryReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSave() throws Exception {
		// given
		Category category = new Category();
		category.setDescription("Foo");

		categoryReactiveRepository.save(category).block();

		// when
		Long count = categoryReactiveRepository.count().block();

		// then
		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	public void testFindByDescription() throws Exception {
		// given
		Category category = new Category();
		category.setDescription("Foo");
		
		categoryReactiveRepository.save(category).block();
		
		// when
		Category fetchedCat = categoryReactiveRepository.findByDescription("Foo").block();
		
		// then
		assertNotNull(fetchedCat.getId());
	}

}
