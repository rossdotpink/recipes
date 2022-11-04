package tw.ross.recipes.recipe;

import jakarta.inject.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/recipe")
public class RecipeResource {

    @Inject
    private RecipeService recipeService;

    // CREATE
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe createRecipe(Recipe recipe) {
        recipeService.addRecipe(recipe);
        return recipe;
    }

    // READ
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> getRecipes(
            @QueryParam("serves") int serves
            ) {

        return recipeService.getRecipeList(serves);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("id") int id) {
        try {
            return recipeService.findRecipe(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new ServerErrorException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PATCH
    @Consumes("application/merge-patch+json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Recipe patch(@PathParam("id") int id, Recipe recipe) {
        // if client tries to patch the id, return 400
        if (Objects.nonNull(recipe.getId()) && recipe.getId() != id) {
            throw new ClientErrorException(Status.BAD_REQUEST);
        }

        try {
            // make sure to-be-updated resource exists
            recipeService.findRecipe(id);

            // update and return resource
            return recipeService.updateRecipe(recipe);

        }
        catch (EntityNotFoundException e) {throw new NotFoundException();}
        catch (Exception e) { throw new ServerErrorException(Status.INTERNAL_SERVER_ERROR);}
    }

    // DELETE
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            recipeService.removeRecipe(id);
            return Response
                    .ok()
                    .build();
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new ServerErrorException(Status.INTERNAL_SERVER_ERROR);
        }
    }

}