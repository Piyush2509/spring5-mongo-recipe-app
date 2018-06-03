package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
@Controller
public class ImageController {

	private final ImageService imageService;
	private final RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		this.imageService = imageService;
		this.recipeService = recipeService;
	}

	@GetMapping("recipe/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/imageuploadform";
	}

	@PostMapping("recipe/{id}/image")
	public String saveImage(@PathVariable String id, @RequestParam("imagefile") MultipartFile file) {
		imageService.saveRecipeImage(id, file).block();
		return "redirect:/recipe/" + id + "/show";
	}

	// @GetMapping("recipe/{id}/recipeimage")
	// public void getRecipeImage(@PathVariable String id, HttpServletResponse
	// response) throws IOException {
	// RecipeCommand recipeCommand = recipeService.findCommandById(id).block();
	//
	// if (recipeCommand.getImage() != null) {
	// byte[] byteArray = new byte[recipeCommand.getImage().length];
	//
	// int i = 0;
	// for (Byte wrappedByte : recipeCommand.getImage()) {
	// byteArray[i++] = wrappedByte;
	// }
	//
	// response.setContentType("image/jpeg");
	// InputStream is = new ByteArrayInputStream(byteArray);
	// IOUtils.copy(is, response.getOutputStream());
	// }
	// }

}
