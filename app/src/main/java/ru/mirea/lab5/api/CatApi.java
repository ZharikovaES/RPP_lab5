package ru.mirea.lab5.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.mirea.lab5.api.model.BreedDTO;
import ru.mirea.lab5.api.model.PhotoDTO;
import ru.mirea.lab5.api.model.PostCreate;
import ru.mirea.lab5.api.model.PostGet;
import ru.mirea.lab5.api.model.PostType;
import ru.mirea.lab5.api.model.Vote;

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
    Call<List<PostGet>> getVotes(@Query("sub_id") String sub_id);

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("images/{image_id}")
    Call<PhotoDTO> getVotesLike(@Path("image_id") String image_id
    );

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @DELETE("votes/{vote_id}")
    Call<Void> delVote(@Path("vote_id") String vote_id
    );

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @POST("votes")
    Call<Vote> setPostFavourites(@Body PostCreate postCreate);

}