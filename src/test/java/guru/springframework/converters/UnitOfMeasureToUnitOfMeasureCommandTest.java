package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

	public static final String ID_VALUE = "1";
	public static final String DESCRIPTION = "description";

	UnitOfMeasureToUnitOfMeasureCommand converter;

	@Before
	public void setUp() throws Exception {
		converter = new UnitOfMeasureToUnitOfMeasureCommand();
	}

	@Test
	public void testNullObjectConvert() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObj() throws Exception {
		assertNotNull(converter.convert(new UnitOfMeasure()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(ID_VALUE);
		uom.setDescription(DESCRIPTION);

		// when
		UnitOfMeasureCommand uomc = converter.convert(uom);

		// then
		assertEquals(ID_VALUE, uomc.getId());
		assertEquals(DESCRIPTION, uomc.getDescription());
	}

}
