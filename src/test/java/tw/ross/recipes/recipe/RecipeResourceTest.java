package tw.ross.recipes.recipe;

import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeResourceTest {

    @InjectMocks
    RecipeResource recipeResource;

    @Mock
    RecipeService recipeService;

    @Test
    void createRecipe() {
    }

    @Test
    void getRecipes() {
    }

    @Test
    void getRecipe() {
    }

    @Test
    void patch_givenRecipeAndMatchingAndExistantId_returnRecipe() {
        Integer existentId = 1;
        Recipe recipe = new Recipe();
        recipe.setId(existentId);

        lenient().when(recipeService.findRecipe(existentId)).thenReturn(recipe);
        lenient().when(recipeService.updateRecipe(recipe)).thenReturn(recipe);

        recipeResource.patch(existentId, recipe);

        verify(recipeService, times(1)).findRecipe(existentId);
        verify(recipeService, times(1)).updateRecipe(recipe);
    }

    @Test
    void patch_givenRecipeAndMatchingButNonExistantId_throwNotFoundException() {
        Integer nonExistentId = 1;
        Recipe recipe = new Recipe();
        recipe.setId(nonExistentId);

        lenient().when(recipeService.findRecipe(nonExistentId)).thenThrow(new EntityNotFoundException());

        assertThrows(NotFoundException.class, () -> {
            recipeResource.patch(nonExistentId, recipe);
        });

        verify(recipeService, times(1)).findRecipe(nonExistentId);
        verify(recipeService, times(0)).updateRecipe(recipe);
    }

    @Test
    void patch_givenRecipeWithIdDifferentFromIdParameter_throwClientErrorException() {
        Integer differentId = 1;
        Recipe recipe = new Recipe();
        recipe.setId(2);

        assertThrows(ClientErrorException.class, () -> {
            recipeResource.patch(differentId, recipe);
        });

        verify(recipeService, times(0)).findRecipe(differentId);
        verify(recipeService, times(0)).updateRecipe(recipe);

    }

    @Test
    void delete_givenValidId_callRecipeServiceRemoveReturnResponseOk() {
        Integer validId = 1;
        Recipe deletedRecipe = new Recipe();

        lenient().when(recipeService.removeRecipe(anyInt())).thenReturn(deletedRecipe);

//        Not sure how to mock 'Response' / 'Response.ResponseBuilder' properly; will just use the real deal
//        lenient().when(Response.ok().build()).thenReturn(Response);

        Response response = recipeResource.delete(validId);

        verify(recipeService, times(1)).removeRecipe(validId);
        assertTrue(response.getStatusInfo() == Response.Status.OK);

    }

    @Test
    void delete_givenInvalidId_callRecipeServiceRemoveRecipeThrowNotFoundException() {
        Integer invalidId = 1;
        Recipe deletedRecipe = new Recipe();

        lenient().when(recipeService.removeRecipe(anyInt())).thenThrow(new EntityNotFoundException());

//        Not sure how to mock 'Response' / 'Response.ResponseBuilder' properly; will just use the real deal
//        lenient().when(Response.ok().build()).thenReturn(Response);

        assertThrows(NotFoundException.class, () -> {
            recipeResource.delete(invalidId);
        });

        verify(recipeService, times(1)).removeRecipe(invalidId);

    }
}