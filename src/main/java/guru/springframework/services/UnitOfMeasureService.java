package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

/**
 * Created by piyush.b.kumar on May 24, 2018.
 */
public interface UnitOfMeasureService {

	Flux<UnitOfMeasureCommand> listAllUoms();

}
