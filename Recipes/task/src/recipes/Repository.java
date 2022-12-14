package recipes;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import recipes.entities.Recipe;

import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends CrudRepository<Recipe,Long>, JpaSpecificationExecutor<Recipe> {

    //@Query("SELECT r FROM Recipe r WHERE r.name=:recipeName ORDER BY r.date")
    List<Recipe> findByNameContainingIgnoreCaseOrderByDateDesc(@Param("name") String name);

    //@Query("SELECT r FROM Recipe r WHERE r.category=:category ORDER BY r.date")
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(@Param("category") String category);
}