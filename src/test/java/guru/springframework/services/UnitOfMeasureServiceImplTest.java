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

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public class UnitOfMeasureServiceImplTest {

	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

	UnitOfMeasureService unitOfMeasureService;

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,
				unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void listAllUomsTest() throws Exception {
		// given
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId("1");
		unitOfMeasures.add(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		unitOfMeasures.add(uom2);

		// when
		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
		Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();

		// then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}
