package tw.ross.recipes.search;

import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import tw.ross.recipes.recipe.*;

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
        try {
            return recipeService.searchRecipes(recipeSearch);
        } catch (Exception e) {
            throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
