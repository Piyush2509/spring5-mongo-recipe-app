package guru.springframework.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeReactiveRepository recipeReactiveRepository;

	public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
		this.recipeReactiveRepository = recipeReactiveRepository;
	}

	@Override
	public Mono<Void> saveRecipeImage(String recipeId, MultipartFile imageFile) {

		Mono<Recipe> recipeMono = recipeReactiveRepository.findById(recipeId).map(recipe -> {
			Byte[] byteObjects = new Byte[0];
			try {
				byteObjects = new Byte[imageFile.getBytes().length];

				int i = 0;
				for (byte b : imageFile.getBytes()) {
					byteObjects[i++] = b;
				}

				recipe.setImage(byteObjects);
				return recipe;
			} catch (IOException e) {
				log.error("Error occured", e);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		});

		recipeReactiveRepository.save(recipeMono.block()).block();

		return Mono.empty();
	}

}
