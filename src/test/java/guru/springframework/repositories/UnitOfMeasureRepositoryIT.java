package guru.springframework.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;

/**
 * Created by piyush.b.kumar on May 22, 2018.
 */
@Ignore
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {
	
	@Autowired
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByDescription() throws Exception {		
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
		assertEquals("Teaspoon", uomOptional.get().getDescription());
	}

	@Test
	public void testFindByDescriptionCup() throws Exception {		
		Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findByDescription("Cup");
		assertEquals("Cup", uomOptional.get().getDescription());
	}

}
