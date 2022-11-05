package tw.ross.recipes.search;

import jakarta.persistence.criteria.*;
import lombok.*;
import tw.ross.recipes.recipe.Recipe;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class RecipeSearch {
    private String name;
    private Integer serves;
    private List<String> tags;
    private List<String> notTags;
    private List<String> ingredients;
    private List<String> notIngredients;
    private String instructions;

    public CriteriaQuery<Recipe> buildQuery(CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

        // add 'name' like predicate
        if (Objects.nonNull(name)) {
            predicates.add(criteriaBuilder.like(recipeRoot.get("name"), like(name)));
        }

        // add 'serves' equal predicate
        if (Objects.nonNull(serves)) {
            predicates.add(criteriaBuilder.equal(recipeRoot.get("serves"), serves));
        }

        // add 'tags' isMember predicates
        if (Objects.nonNull(tags) && tags.size() > 0) {
            for (String tag : tags) {
                predicates.add(criteriaBuilder.isMember(tag, recipeRoot.get("tags")));
            }
        }

        // add 'notTags' isNotMember predicates
        if (Objects.nonNull(notTags) && notTags.size() > 0) {
            for (String notTag : notTags) {
                predicates.add(criteriaBuilder.isNotMember(notTag, recipeRoot.get("tags")));
            }
        }

        // add 'ingredients' isMember predicates
        if (Objects.nonNull(ingredients) && ingredients.size() > 0) {
            for (String ingredient : ingredients) {
                predicates.add(criteriaBuilder.isMember(ingredient, recipeRoot.get("ingredients")));
            }
        }

        // add 'notIngredients' isMember predicates
        if (Objects.nonNull(notIngredients) && notIngredients.size() > 0) {
            for (String notIngredient : notIngredients) {
                predicates.add(criteriaBuilder.isNotMember(notIngredient, recipeRoot.get("ingredients")));
            }
        }

        // add 'instructions' like predicate
        if (Objects.nonNull(instructions)) {
            predicates.add(criteriaBuilder.like(recipeRoot.get("instructions"), like(instructions)));
        }

        // build complete predicate, prepare query
        Predicate finalPredicate = buildPredicates(criteriaBuilder, predicates);

        criteriaQuery.where(finalPredicate);

        // return query
        return criteriaQuery;
    }


    /**
     *
     * @param criteriaBuilder
     * @param predicates
     * @return
     */
    private Predicate buildPredicates(
            CriteriaBuilder criteriaBuilder,
            List<Predicate> predicates) {

        // handle 0 or 1 predicate
        switch (predicates.size()) {
            case 0:
                return null;
            case 1:
                return predicates.get(0);
            default:
                break;
        }

        // 'and' all predicates into one
        Predicate finalPredicate = predicates.get(0);
        Iterator<Predicate> i = predicates.iterator();
        while (i.hasNext()) {
            finalPredicate = criteriaBuilder.and(finalPredicate, i.next());
        }

        return finalPredicate;
    }

    /** Prepend and append '%' to the value of `term`. Useful
     *  for preparing strings for 'like' queries.
     *
     * @param term
     * @return
     */
    private String like(String term) {
        return "%" + term + "%";
    }

}
