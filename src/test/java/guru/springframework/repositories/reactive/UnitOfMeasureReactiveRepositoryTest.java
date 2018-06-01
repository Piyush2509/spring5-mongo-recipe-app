package guru.springframework.repositories.reactive;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;

/**
 * Created by piyush.b.kumar on Jun 1, 2018
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

	private static final String EACH = "Each";

	@Autowired
	UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

	@Before
	public void setUp() throws Exception {
		unitOfMeasureReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSave() throws Exception {
		// given
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(EACH);

		unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

		// when
		Long count = unitOfMeasureReactiveRepository.count().block();

		// then
		assertEquals(Long.valueOf(1L), count);
	}

	@Test
	public void testFindByDescription() throws Exception {
		// given
		UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setDescription(EACH);

		unitOfMeasureReactiveRepository.save(unitOfMeasure).block();

		// when
		UnitOfMeasure fetchedUOM = unitOfMeasureReactiveRepository.findByDescription(EACH).block();

		// then
		assertEquals(EACH, fetchedUOM.getDescription());
	}

}
