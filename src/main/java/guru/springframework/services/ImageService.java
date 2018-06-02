package guru.springframework.services;

import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public interface ImageService {

	Mono<Void> saveRecipeImage(String recipeId, MultipartFile imageFile);

}
