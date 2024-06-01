package hcmute.edu.vn.foodmachinelearning.Interface;

import java.util.List;

import hcmute.edu.vn.foodmachinelearning.model.Instructions;
import hcmute.edu.vn.foodmachinelearning.model.InstructionsResponse;
import hcmute.edu.vn.foodmachinelearning.model.RecipeInformation;
import hcmute.edu.vn.foodmachinelearning.model.complexSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpoonacularAPI {
    @GET("recipes/complexSearch")
    Call<complexSearch> getRecipeSearch(
//        @Path("id") int recipeId,
        @Query("apiKey") String apiKey,
        @Query("query") String query
    );

    @GET("recipes/{id}/information")
    Call<RecipeInformation> getRecipeInformation(
        @Path("id") int recipeId,
        @Query("apiKey") String apiKey
//        @Query("query") String query
    );

    @GET("recipes/{id}/analyzedInstructions")
    Call<List<Instructions>> getInstructions(
            @Path("id") int recipeId,
            @Query("apiKey") String apiKey
//        @Query("query") String query
    );

}
