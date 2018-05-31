package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
public class UnitOfMeasureCommandToUnitOfMeasureTest {

	public static final String ID_VALUE = "1";
	public static final String DESCRIPTION = "description";

	UnitOfMeasureCommandToUnitOfMeasure converter;

	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureCommandToUnitOfMeasure();
	}

	@Test
	public void testNullParamter() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasureCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(ID_VALUE);
		command.setDescription(DESCRIPTION);

		// when
		UnitOfMeasure uom = converter.convert(command);

		// then
		assertNotNull(uom);
		assertEquals(ID_VALUE, uom.getId());
		assertEquals(DESCRIPTION, uom.getDescription());
	}

}
