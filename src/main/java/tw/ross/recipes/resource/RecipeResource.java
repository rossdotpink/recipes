package tw.ross.recipes.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/recipe")
public class RecipeResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}