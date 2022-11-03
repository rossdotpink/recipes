package tw.ross.recipes.recipe;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.*;

/**
 * Service layer for the Recipe resources.
 *
 * @see         Recipe
 * @see         RecipeResource
 */

@Stateless
//@ApplicationScoped
//@Transactional
public class RecipeService {

    @PersistenceContext
    private EntityManager em;

    // CREATE
    public void addRecipe(Recipe recipe) { em.persist(recipe); }

    // READ
    public Recipe findRecipe(long id) {
        return em.find(Recipe.class, id);
    }

    public Recipe getRecipe(long id) {
        return null;
    }

    public List<Recipe> allRecipes() {
        return em.createQuery("SELECT r FROM Recipe r", Recipe.class).getResultList();
    }

    public List<Recipe> getRecipes(int count, int offset) {
        return null;
    }

    // UPDATE
    public void updateRecipe(Recipe recipe) {return;}

    // DELETE
    public void deleteRecipe(long id) {return;}

}
