package guru.springframework.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.domain.UnitOfMeasure;

/**
 * Created by piyush.b.kumar on May 18, 2018.
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {
	
	Optional<UnitOfMeasure> findByDescription(String description);

}
