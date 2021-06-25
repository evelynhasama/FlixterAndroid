package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.adapters.MovieAdapter;
import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a71c0acdb84c7fdef6c324846d8ec1f1";
    public static final String COMING_SOON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=a71c0acdb84c7fdef6c324846d8ec1f1&region=US";
    public static final String TAG = "MainActivity";

    List<Movie> movies;

    Button btnNowPlaying;
    Button btnComingSoon;
    String url;

    String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Implement View Binding
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        btnComingSoon = binding.btnComingSoon;
        btnNowPlaying = binding.btnNowPlaying;

        checkChoice();

        if (choice.equals("Now Playing")){
            btnNowPlaying.setBackgroundColor(getColor(R.color.teal_700));
            btnComingSoon.setBackgroundColor(getColor(R.color.dark_grey));
            url = NOW_PLAYING_URL;
        } else {
            btnNowPlaying.setBackgroundColor(getColor(R.color.dark_grey));
            btnComingSoon.setBackgroundColor(getColor(R.color.teal_700));
            url = COMING_SOON_URL;
        }

        getSupportActionBar().setTitle("Flixter");

        movies = new ArrayList<>();

        RecyclerView rvMovies = binding.rvMovies;

        // Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        // Set adapter on recycler view
        rvMovies.setAdapter(movieAdapter);
        // Set layout manager on recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        retrieveMovies(movieAdapter);

        btnComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Coming Soon Clicked");
                if (choice.equals("Coming Soon") == false){
                    movies.removeAll(movies);
                    choice = "Coming Soon";
                    btnNowPlaying.setBackgroundColor(getColor(R.color.dark_grey));
                    btnComingSoon.setBackgroundColor(getColor(R.color.teal_700));
                    url = COMING_SOON_URL;
                    retrieveMovies(movieAdapter);
                    rvMovies.scrollToPosition(0);
                    saveChoice();
                }
            }
        });

        btnNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Now Playing Clicked");
                if (choice.equals("Now Playing") == false){
                    movies.removeAll(movies);
                    choice = "Now Playing";
                    btnNowPlaying.setBackgroundColor(getColor(R.color.teal_700));
                    btnComingSoon.setBackgroundColor(getColor(R.color.dark_grey));
                    url = NOW_PLAYING_URL;
                    retrieveMovies(movieAdapter);
                    rvMovies.scrollToPosition(0);
                    saveChoice();
                }
            }
        });
    }

    private void retrieveMovies(MovieAdapter movieAdapter){

        Objects.requireNonNull(getSupportActionBar()).setSubtitle(choice);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                    Objects.requireNonNull(getSupportActionBar()).setSubtitle(choice + ": " + movies.size() + " movies");
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(),"data.txt");
    }


    // This function will check which movies to display
    private void checkChoice(){
        try {
            choice = org.apache.commons.io.FileUtils.readFileToString(getDataFile(), Charset.defaultCharset());
            if (choice.equals("")){
                choice = "Now Playing";
            }
        } catch (IOException e) {
            Log.e(TAG, "Loading Data File");
            choice = "Now Playing";
        }
    }

    // This function saves movie list choice
    private void saveChoice() {
        try {
            org.apache.commons.io.FileUtils.write(getDataFile(), choice, (String) null, false);
        } catch (IOException e) {
            Log.e(TAG, "Saving Choice");
        }
    }

}