package ru.mirea.lab5.api;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.mirea.lab5.api.model.BreedDTO;
import ru.mirea.lab5.api.model.PhotoDTO;
import ru.mirea.lab5.api.model.PostFavourites;

public interface CatApi {
    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("breeds")
    Call<List<BreedDTO>> getBreeds();

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("images/search?mime_types=gif,jpg,png")
    Call<List<PhotoDTO>> getPhotoForBreed(@Query("breed_ids") String breed,
                                          @Query("limit") int limit,
                                          @Query("order") String desc,
                                          @Query("page") int page
    );

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("votes")
    Call<List<PostFavourites>> getVotes(@Query("sub_id") String sub_id);


    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @POST("votes")
    Call<PostFavourites> setPostFavourites(@Body PostFavourites postFavourites
    );

}