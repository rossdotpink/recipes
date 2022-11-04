package tw.ross.recipes.recipe;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.*;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class RecipeSearch {
    private String name;
    private Integer serves;
    private List<String> ingredients;
    private List<String> notIngredients;
    private List<String> instructions;

    public CriteriaQuery<Recipe> buildQuery(CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> recipeRoot = criteriaQuery.from(Recipe.class);

        // add 'serves' equal predicate
        if (Objects.nonNull(serves)) {
            predicates.add(criteriaBuilder.equal(recipeRoot.get("serves"), serves));
        }

        // add 'name' like predicate
        if (Objects.nonNull(name)) {
            predicates.add(criteriaBuilder.like(recipeRoot.get("name"), like(name)));
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
