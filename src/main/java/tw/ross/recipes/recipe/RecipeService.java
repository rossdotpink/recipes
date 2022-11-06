package tw.ross.recipes.recipe;

import jakarta.ejb.*;
import jakarta.enterprise.context.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.transaction.*;
import tw.ross.recipes.search.*;

import java.util.*;

/**
 * Service layer for the Recipe resources. Handles all requests which interact with the
 * JPA EntityManager.
 *
 * Always returns either the desired Recipe(s), or throws an Exception.
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
    public Recipe findRecipe(Integer id) {
        try {
            Recipe recipe = em.find(Recipe.class, id);
            if (Objects.isNull(recipe)) throw new EntityNotFoundException();
            return recipe;
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Recipe> getRecipeList(Integer pageNumber, Integer pageSize) {
        Integer firstResult = ((pageNumber - 1) * pageSize);

        return em
                .createQuery("SELECT r FROM Recipe r", Recipe.class)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();
    }

    // UPDATE
    public Recipe updateRecipe(Recipe recipe) {
        Recipe updatedRecipe = em.merge(recipe);
        return updatedRecipe;
    }

    // DELETE
    public Recipe removeRecipe(Integer id)  {
        try {
            Recipe recipe = em.find(Recipe.class, id);
            if (Objects.isNull(recipe)) throw new EntityNotFoundException();
            em.remove(recipe);
            return recipe;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     *
     * @param recipeSearch
     * @return
     */

    public List<Recipe> searchRecipes(RecipeSearch recipeSearch) {
        CriteriaQuery<Recipe> criteriaQuery = recipeSearch.buildQuery(em.getCriteriaBuilder());
        return em.createQuery(criteriaQuery).getResultList();
    }
}
