package tw.ross.recipes.recipe;

import jakarta.inject.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/recipe")
public class RecipeResource {

    @Inject
    private RecipeService recipeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recipe> hello() {
        return recipeService.allRecipes();
    }

    // CREATE
//    @RequestMapping(value = "/recipe", method = RequestMethod.POST)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createRecipe(Recipe recipe) {
        recipeService.addRecipe(recipe);
        return recipe.toString();
    }
//
//    // READ
//    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.GET)
//    public @ResponseBody Recipe getRecipe(@PathVariable Long id) {
//        return recipeService.getRecipe(id);
//    }
//    // UPDATE
//    // DELETE
//    @RequestMapping(value = "/recipe/{id}", method = RequestMethod.DELETE)
//    public HttpStatus deleteRecipe(@PathVariable Long id) {
//        recipeService.deleteRecipe(id);
//        return HttpStatus.NO_CONTENT;
//    }
//
//    @RequestMapping(value = "/recipe/{name}", method = RequestMethod.GET)
//    public List<Person> getPersonByName(@PathVariable String name) {
//        return recipeService.findByName(name);
//    }

//    @RequestMapping(value = "/recipe", method = RequestMethod.GET)
//    public Set<Recipe> getAll() {
//        return recipeService.getAllPersons();
//    }




//    @RequestMapping(value = "/recipe", method = RequestMethod.PUT)
//    public HttpStatus updatePerson(@RequestBody Person person) {
//        return personService.updatePerson(person) ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST;
//    }

//    @GetMapping("/{id}", produces = "application/json")
//    public Book getBook(@PathVariable int id) {
//        return findBookById(id);
//    }
//
//    private Book findBookById(int id) {
//        // ...
//    }
}