package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
public class CategoryCommandToCategoryTest {

	public static final String ID_VALUE = "1";
	public static final String DESCRIPTION = "description";

	CategoryCommandToCategory converter;

	@Before
	public void setUp() throws Exception {
		converter = new CategoryCommandToCategory();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new CategoryCommand()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);

		// when
		Category category = converter.convert(categoryCommand);

		// then
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}

}
