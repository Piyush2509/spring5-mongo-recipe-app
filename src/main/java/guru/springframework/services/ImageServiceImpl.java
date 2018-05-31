package guru.springframework.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	public void saveRecipeImage(String recipeId, MultipartFile imageFile) {
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] byteObjects = new Byte[imageFile.getBytes().length];

			int i = 0;
			for (byte b : imageFile.getBytes()) {
				byteObjects[i++] = b;
			}

			recipe.setImage(byteObjects);
			recipeRepository.save(recipe);
		} catch (IOException e) {
			log.error("Error occured", e);
			e.printStackTrace();
		}
	}

}
