package com.example.themoviedb.retrofit;

import com.example.themoviedb.activity.TrailerActivity;
import com.example.themoviedb.model.DetailModel;
import com.example.themoviedb.model.MovieModel;
import com.example.themoviedb.model.TrailerModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Call<MovieModel> getPopular(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/now_playing")
    Call<MovieModel> getNowPlaying(
            @Query("api_key") String api_key,
            @Query("language") String language,
            @Query("page") String page
    );

    @GET("movie/{movie_id}")
    Call<DetailModel> getDetail(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerModel> getTrailer(
            @Path("movie_id") String movie_id,
            @Query("api_key") String api_key
    );
}
