package com.example.themoviedb.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.themoviedb.R;
import com.example.themoviedb.model.DetailModel;
import com.example.themoviedb.model.MovieModel;
import com.example.themoviedb.retrofit.Constant;
import com.example.themoviedb.retrofit.MovieService;
import com.example.themoviedb.retrofit.RetrofitInstance;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {
    private String TAG = "DetailActivity";

    ProgressBar progress_loading;
    ImageView image_backdrop;
    FloatingActionButton fab_play;
    TextView text_title, text_vote, text_genre, text_overview;


    private MovieService service = RetrofitInstance.getUrl().create(MovieService.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupView();
        setupListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constant.MOVIE_ID != 0) {
            getDetailMovie();
        } else {
            finish();
        }
    }
    private void setupView(){

        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress_loading = findViewById(R.id.progress_loading);
        image_backdrop = findViewById(R.id.image_backdrop);
        fab_play = findViewById(R.id.fab_play);
        text_title = findViewById(R.id.text_title);
        text_vote = findViewById(R.id.text_vote);
        text_genre = findViewById(R.id.text_genre);
        text_overview = findViewById(R.id.text_overview);
    }

    private void setupListener(){
        fab_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, TrailerActivity.class));
            }
        });
    }

    private void getDetailMovie(){

        showLoading(true);
        Call<DetailModel> call = service.getDetail( String.valueOf( Constant.MOVIE_ID) ,
                Constant.API_KEY);
        call.enqueue(new Callback<DetailModel>() {
            @Override
            public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                Log.d(TAG, response.toString());
                showLoading(false);
                if (response.isSuccessful()) {
                    DetailModel detail = response.body();
                    showMovie( detail );
                }
            }

            @Override
            public void onFailure(Call<DetailModel> call, Throwable t) {
                Log.d(TAG, t.toString());
                showLoading(false);
            }
        });

    }

    private void showLoading( Boolean loading){
        if (loading){
            progress_loading.setVisibility(View.VISIBLE);
        }else {
            progress_loading.setVisibility(View.GONE);
        }
    }

    private void showMovie(DetailModel detail) {
        text_title.setText( detail.getTitle() );
        text_vote.setText(detail.getVote_average().toString());
        text_overview.setText(detail.getOverview());

        for (DetailModel.Genres genre: detail.getGenres()){

            text_genre.setText(genre.getName() + " " );
        }

        Picasso.get()
                .load(Constant.BACKDROP_PATH + detail.getBackdrop())
                .placeholder(R.drawable.placeholderlandscape)
                .error(R.drawable.placeholderlandscape)
                .fit().centerCrop()
                .into(image_backdrop);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
