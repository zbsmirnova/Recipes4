package recipes;

import org.springframework.stereotype.Component;
import recipes.entities.Recipe;
import java.time.LocalDateTime;

@Component
public class RecipeMapper {
    public Recipe recipeCreateDtoRecipe(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }
        Recipe recipe = new Recipe();

        return updateFromDto(recipe, recipeDto);
    }

    public Recipe updateFromDto(Recipe recipe, RecipeDto recipeDto) {
        recipe.setName(recipeDto.getName());
        recipe.setDescription(recipeDto.getDescription());
        recipe.setDirections(recipeDto.getDirections());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setCategory(recipeDto.getCategory());

        recipe.setDate(LocalDateTime.now());
        return recipe;
    }

    public RecipeDto recipeDtoCreateRecipe(Recipe recipe) {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName(recipe.getName());
        recipeDto.setCategory(recipe.getCategory());
        recipeDto.setDate(recipe.getDate());
        recipeDto.setDescription(recipe.getDescription());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setDirections(recipe.getDirections());
        return recipeDto;
    }
}
