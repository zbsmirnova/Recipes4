package recipes;

import org.springframework.data.repository.CrudRepository;
import recipes.entities.Direction;

@org.springframework.stereotype.Repository
public interface DirectionsRepository extends CrudRepository<Direction, Long> {
}
