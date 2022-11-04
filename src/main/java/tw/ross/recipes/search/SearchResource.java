package tw.ross.recipes.search;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import tw.ross.recipes.recipe.Recipe;
import tw.ross.recipes.recipe.RecipeSearch;
import tw.ross.recipes.recipe.RecipeService;

import java.util.List;

@Path("/search")
public class SearchResource {

    @Inject
    private RecipeService recipeService;

    @Path("/recipes")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> searchRecipe(RecipeSearch recipeSearch) {
        return recipeService.searchRecipes(recipeSearch);
    }
}
