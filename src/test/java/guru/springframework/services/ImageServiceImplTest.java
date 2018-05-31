package guru.springframework.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;

	ImageService imageService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	public void saveRecipeImageTest() throws Exception {
		// given
		String id = "1";
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain",
				"Spring Framework Guru".getBytes());

		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);

		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

		// when
		imageService.saveRecipeImage(id, multipartFile);

		// then
		verify(recipeRepository, times(1)).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
	}

}
