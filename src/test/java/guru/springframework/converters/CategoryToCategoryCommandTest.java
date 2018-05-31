package guru.springframework.converters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;

/**
 * Created by piyush.b.kumar on May 23, 2018.
 */
public class CategoryToCategoryCommandTest {

	public static final String ID_VALUE = "1";
	public static final String DESCRIPTION = "description";

	CategoryToCategoryCommand convter;

	@Before
	public void setUp() throws Exception {
		convter = new CategoryToCategoryCommand();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(convter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(convter.convert(new Category()));
	}

	@Test
	public void testConvert() throws Exception {
		// given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);

		// when
		CategoryCommand categoryCommand = convter.convert(category);

		// then
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());
	}

}
