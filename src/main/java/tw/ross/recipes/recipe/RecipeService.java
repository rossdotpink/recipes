package tw.ross.recipes.recipe;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import tw.ross.recipes.search.RecipeSearch;

import java.util.*;

/**
 * Service layer for the Recipe resources.
 *
 * Always returns either the desired data, or a
 *
 * @see         Recipe
 * @see         RecipeResource
 */

@Stateless
@ApplicationScoped
@Transactional
public class RecipeService {

    @PersistenceContext
    private EntityManager em;

    // CREATE
    public Recipe addRecipe(Recipe recipe) {
        try {
            em.persist(recipe);
            return recipe;
        } catch (Exception e) {
            throw e;
        }
    }

    // READ
    public Recipe findRecipe(long id) {
        try {
            Recipe recipe = em.find(Recipe.class, id);
            if (Objects.isNull(recipe)) throw new EntityNotFoundException();
            return recipe;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Recipe> getRecipeList(int serves) {
        return em
                .createQuery("SELECT r FROM Recipe r", Recipe.class)
                .getResultList();
    }

//    public List<Recipe> getRecipes(int count, int offset) {
//        return null;
//    }

    // UPDATE
    public Recipe updateRecipe(Recipe recipe) {
        Recipe updatedRecipe = em.merge(recipe);
        return updatedRecipe;
    }

    // DELETE
    public Recipe removeRecipe(long id)  {
        try {
            Recipe recipe = em.find(Recipe.class, id);
            if (Objects.isNull(recipe)) throw new EntityNotFoundException();
            em.remove(recipe);
            return recipe;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Recipe> searchRecipes(RecipeSearch recipeSearch) {
        CriteriaQuery<Recipe> criteriaQuery = recipeSearch.buildQuery(em.getCriteriaBuilder());
        return em.createQuery(criteriaQuery).getResultList();
    }
}
