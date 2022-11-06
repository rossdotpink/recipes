package tw.ross.recipes.recipe;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    @Test
    void setTags_givenMixedCaseTags_convertsTagsToLowercase() {
        Recipe recipe = new Recipe();
        List<String> mixedCaseTags = List.of("CamelCase", "BLOCK CAPS");
        List<String> lowerCaseTags = List.of("camelcase", "block caps");
        recipe.setTags(mixedCaseTags);
        assertEquals(lowerCaseTags, recipe.getTags());
    }
}