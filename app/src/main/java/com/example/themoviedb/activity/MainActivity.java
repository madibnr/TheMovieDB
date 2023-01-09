package com.example.themoviedb.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.themoviedb.R;
import com.example.themoviedb.adapter.MainAdapter;
import com.example.themoviedb.model.MovieModel;
import com.example.themoviedb.retrofit.Constant;
import com.example.themoviedb.retrofit.MovieService;
import com.example.themoviedb.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";

    private MovieService service = RetrofitInstance.getUrl().create(MovieService.class);
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private RecyclerView.LayoutManager layoutManager;
    private MainAdapter adapter;
    private List<MovieModel.Results> movies = new ArrayList<>();

    private String movieCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        SetupRecyclerView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (movieCategory == null ) movieCategory = Constant.POPULAR;
        getMovie();
    }

    private void setupView(){
        recyclerView = findViewById(R.id.list_movie);
        progressBar = findViewById(R.id.progress_loading);
    }

    private void SetupRecyclerView(){
        adapter = new MainAdapter(movies, this, new MainAdapter.AdapterListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(MainActivity.this, DetailActivity.class));
            }
        });

        layoutManager = new GridLayoutManager( this, 2);
        recyclerView.setLayoutManager( layoutManager);
        recyclerView.setAdapter(adapter);
    }


    private void getMovie(){

    showLoading(true);

        Call<MovieModel> call = null;

        switch (movieCategory){
            case Constant.POPULAR:
                call = service.getPopular(Constant.API_KEY, Constant.LANGUAGE, "1");
                break;
            case Constant.NOW_PLAYING:
                call = service.getNowPlaying(Constant.API_KEY, Constant.LANGUAGE, "1");
                break;
        }

        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response){
                showLoading(false);

               if (response.isSuccessful()) {
                   MovieModel movie = response.body();
                   showMovie( movie );
               }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                showLoading(false);
                Log.d(TAG, t.toString());
            }
        });

    }

    private void showLoading( Boolean loading){
        if (loading){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void showMovie(MovieModel movie) {
    movies = movie.getResults();
    adapter.setData(movies);
    }

    private  void showMessage(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflate the menu; this adds to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so Long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_popular) {
            getSupportActionBar().setTitle("POPULAR");
            movieCategory = Constant.POPULAR;
            getMovie();
            return true;
        }else if (id == R.id.action_now_playing){
            getSupportActionBar().setTitle("NOW PLAYING");
            movieCategory = Constant.NOW_PLAYING;
            getMovie();
                return true;
    }
        return super.onOptionsItemSelected(item);
    }
}