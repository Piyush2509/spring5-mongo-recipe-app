package guru.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.Category;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
public interface CategoryRepository extends CrudRepository<Category, String> {
	
	Optional<Category> findByDescription(String description);

}
