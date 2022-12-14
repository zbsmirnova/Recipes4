package recipes;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.entities.Recipe;
import recipes.entities.UserDto;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@Validated
@RequestMapping("/api/recipe")
public class Controller {
  @Autowired
  private RecipeService recipeService;

  @GetMapping("/recipe/{id}")
  public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
    try {
      RecipeDto recipe = recipeService.findById(id);
      return ResponseEntity.ok(recipe);
    }
    catch (RecipeNotFoundException e) {
      return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping(value = "/recipe/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<RecipeDto> searchRecipe(@Validated @RequestParam Optional<String> category, @Validated @RequestParam Optional<String> name) {
    if (name.isPresent() && category.isEmpty()) {
      return recipeService.findRecipesByName(name.get());
    }
    else if (category.isPresent() && name.isEmpty()) {
      return recipeService.findRecipesByCategory(category.get());
    }
    else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/recipe/new")
  public ResponseEntity<ReturnId> createRecipe(@Valid @RequestBody RecipeDto recipe) {
    ReturnId createdRecipe = recipeService.createRecipe(recipe);
    return ResponseEntity.ok().body(createdRecipe);
  }

  @DeleteMapping("/recipe/{id}")
  public ResponseEntity<?> deleteById(@PathVariable Long id) {
    try {
      recipeService.deleteById(id);
      return new ResponseEntity<Recipe>(HttpStatus.NO_CONTENT);
    }
    catch (RecipeNotFoundException e) {
      return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/recipe/{id}")
  public ResponseEntity<?> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDto recipeDto) {
    try {
      recipeService.update(id, recipeDto);
      return new ResponseEntity<Recipe>(HttpStatus.NO_CONTENT);
    }
    catch (RecipeNotFoundException e) {
      return new ResponseEntity<Recipe>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerNewUser( @Valid @RequestBody UserDto userDto) {

  }
}

@RestControllerAdvice
class ControllerExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  ResponseEntity.BodyBuilder exceptionHandler(ValidationException e) {
    return ResponseEntity.badRequest();
  }
}