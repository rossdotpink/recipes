package tw.ross.recipes.recipe;

import jakarta.annotation.*;
import jakarta.inject.*;
import jakarta.persistence.*;
import jakarta.validation.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.*;
import jakarta.ws.rs.ext.Provider;

import java.util.*;

@Path("/recipe")
public class RecipeResource {

    @Inject
    private RecipeService recipeService;

    // CREATE
    /** Handles requests to create a new Recipe.
     *
     * Either returns a successful response of the newly persisted Recipe, or throws
     *  an exception.
     *
     * @return  a list of Recipes
     * @see RecipeService
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe createRecipe(Recipe recipe) {
        recipeService.addRecipe(recipe);
        return recipe;
    }

    // READ
    /** Handles requests to get all Recipes already in the database. Paginated by
     * default to 10 results per page.
     *
     * Either returns a successful response of all requested Recipes, or throws an
     * exception.
     *
     * @return  a list of Recipes
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> getRecipes(
            @QueryParam("pageNumber") Integer pageNumber,
            @QueryParam("pageSize") Integer pageSize) {
        if(Objects.isNull(pageNumber)) pageNumber = 1;
        if(Objects.isNull(pageSize)) pageSize = 10;
        return recipeService.getRecipeList(pageNumber, pageSize);
    }

    /** Handles requests to get a Recipe already in the database.
     *
     * Either returns a successful response of the requested Recipe, or throws an
     * exception.
     *
     * @param id        an Integer representing the unique Id of a Recipe
     * @return          the updated Recipe
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("id") Integer id) {
        try {
            return recipeService.findRecipe(id);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException();
        } catch (Exception e) {
            throw new ServerErrorException(Status.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    /** Handles requests to update a Recipe already in the database.
     *
     * Either returns a successful response of the updated Recipe, or throws an
     * exception.
     *
     * @param id        an Integer representing the unique Id of a Recipe
     * @param recipe    a Recipe to be persisted in place of an existing Recipe
     * @return          the updated Recipe
     */
    @PATCH
    @Consumes("application/merge-patch+json")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Recipe patch(@PathParam("id") Integer id, Recipe recipe) {
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
    /** Handles requests to delete a Recipe from the database.
     *
     * Either returns an empty successful response, or throws an exception.
     *
     * @param id        an Integer representing the unique Id of a Recipe
     * @return an empty successful response
     */
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
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

    /**
     * This method is run after Dependency Injection and simply
     * loads some sample recipes into the database.
     */
    @PostConstruct
    private void loadSampleRecipes() {
        Recipe noSalmonUsingOven = new Recipe();
        noSalmonUsingOven.setName("Oven-baked Cod");
        noSalmonUsingOven.setServes(2);
        noSalmonUsingOven.setTags(List.of("Pescetarian","fish","Oven"));
        noSalmonUsingOven.setIngredients(List.of("cod"));
        noSalmonUsingOven.setInstructions("Buy some cod. Stick it in the oven. ");
        recipeService.addRecipe(noSalmonUsingOven);

        Recipe servesFourWithPotatoes = new Recipe();
        servesFourWithPotatoes.setName("Potatoes for 4");
        servesFourWithPotatoes.setServes(4);
        servesFourWithPotatoes.setTags(List.of("vegetarian","potato","raw"));
        servesFourWithPotatoes.setIngredients(List.of("potatoes"));
        servesFourWithPotatoes.setInstructions("Steal four potatoes (tastes better than buying); put them on a plate, eat. ");
        recipeService.addRecipe(servesFourWithPotatoes);
    }

}