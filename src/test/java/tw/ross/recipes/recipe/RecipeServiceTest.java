package tw.ross.recipes.recipe;

import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;
import java.util.concurrent.atomic.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    EntityManager entityManager;

    @InjectMocks
    RecipeService recipeService;

    @Test
    void addRecipe_givenValidRecipe_callsEntityManagerPersist() {
        Recipe testRecipe = new Recipe();
        lenient().doNothing().when(entityManager).persist(any(Recipe.class));
        recipeService.addRecipe(testRecipe);
        verify(entityManager, times(1)).persist(any(Recipe.class));
    }

    @Test
    void findRecipe_givenValidId_callsEntityManagerPersist() {
        int validId = 1;
        lenient().when(entityManager.find(Recipe.class, validId)).thenReturn(new Recipe());
        recipeService.findRecipe(validId);
        verify(entityManager, times(1)).find(Recipe.class, validId);
    }

    @Test
    void findRecipe_givenValidId_returnsRecipe() {
        int validId = 1;
        lenient().when(entityManager.find(Recipe.class, validId)).thenReturn(new Recipe());
        Recipe validRecipe = recipeService.findRecipe(validId);
        assertTrue(Objects.nonNull(validRecipe));
    }

    @Test
    void findRecipe_givenInvalidId_throwsEntityNotFoundException() {
        int invalidId = 0;
        lenient().when(entityManager.find(Recipe.class, invalidId)).thenThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> {
            recipeService.findRecipe(invalidId);
        });
    }

    @Test
    void getRecipeList() {
    }

    @Test
    void updateRecipe_givenValidRecipe_callsEntityManagerMergeReturnsRecipe() {
        Recipe validRecipe = new Recipe();
        lenient().when(entityManager.merge(any(Recipe.class))).thenReturn(validRecipe);
        Recipe mergedRecipe = recipeService.updateRecipe(validRecipe);
        verify(entityManager, times(1)).merge(any(Recipe.class));
    }

    @Test
    void removeRecipe_givenValidId_callsEntityManagerFindCallsEntityManagerRemove() {
        Integer validId = 1;
        Recipe validRecipe = new Recipe();

        lenient().when(entityManager.find(Recipe.class, validId)).thenReturn(validRecipe);
        lenient().doNothing().when(entityManager).remove(any(Recipe.class));

        recipeService.removeRecipe(validId);

        InOrder inOrder = inOrder(entityManager);
        inOrder.verify(entityManager, times(1)).find(Recipe.class, validId);
        inOrder.verify(entityManager, times(1)).remove(validRecipe);

    }

    @Test
    void removeRecipe_givenInvalidId_callsEntityManagerFindDoesntCallEntityManagerRemoveThrowsEntityNotFoundException() {
        Integer invalidId = 1;
        Recipe validRecipe = new Recipe();

        lenient().when(entityManager.find(Recipe.class, invalidId)).thenReturn(null);
        lenient().doNothing().when(entityManager).remove(any(Recipe.class));

        // Workaround to get value out of lambda
        AtomicReference<Recipe> nullRecipe = new AtomicReference<>();
        assertThrows(EntityNotFoundException.class, () -> {
            nullRecipe.set(recipeService.removeRecipe(invalidId));
        });

        assertTrue(Objects.isNull(nullRecipe.get()));

        InOrder inOrder = inOrder(entityManager);
        inOrder.verify(entityManager, times(1)).find(Recipe.class, invalidId);
        inOrder.verify(entityManager, times(0)).remove(validRecipe);

    }

    @Test
    void searchRecipes() {
    }
}