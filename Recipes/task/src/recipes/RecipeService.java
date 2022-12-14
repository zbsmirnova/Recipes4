package recipes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.entities.Recipe;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeService {
    @Autowired
    private Repository recipeRepo;
    @Autowired
    private RecipeMapper recipeMapper;

    public RecipeDto findById(Long id) {
        Recipe recipe = recipeRepo.findById(id).orElseThrow(RecipeNotFoundException::new);
        return recipeMapper.recipeDtoCreateRecipe(recipe);
    }

    public ReturnId createRecipe(RecipeDto recipeDto) {
        Recipe recipe = recipeMapper.recipeCreateDtoRecipe(recipeDto);
        recipe = recipeRepo.save(recipe);
        return new ReturnId(recipe.getId());
    }

    public void deleteById(Long id) {
        if (!recipeRepo.existsById(id)){
            throw new RecipeNotFoundException();
        }
        recipeRepo.deleteById(id);
    }

    @org.springframework.transaction.annotation.Transactional
    public void update(Long id, RecipeDto recipeDto) {
        Optional<Recipe> optionalRecipe = recipeRepo.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        Recipe recipe = recipeMapper.recipeCreateDtoRecipe(recipeDto);
        recipe.setId(id);
        Recipe upd = recipeRepo.save(recipe);
    }

    public List<RecipeDto> findRecipesByName(String name) {
        List<Recipe> recipes = recipeRepo.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        return recipes.stream().map(recipe -> recipeMapper.recipeDtoCreateRecipe(recipe)).collect(Collectors.toList());
    }

    public List<RecipeDto> findRecipesByCategory(String category) {
        List<Recipe> recipes = recipeRepo.findByCategoryIgnoreCaseOrderByDateDesc(category);
        return recipes.stream().map(recipe -> recipeMapper.recipeDtoCreateRecipe(recipe)).collect(Collectors.toList());
    }
}