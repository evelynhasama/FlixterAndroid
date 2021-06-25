package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.databinding.ActivityMovieDetailsBinding;
import com.example.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String VIDEOS_URL_1 = "https://api.themoviedb.org/3/movie/";
    public static final String VIDEOS_URL_2 = "/videos?api_key=a71c0acdb84c7fdef6c324846d8ec1f1";
    public static final String TAG = "MovieDetailsActivity";

    Movie movie;

    TextView tvTitleDetail;
    TextView tvOverviewDetail;
    RatingBar rbVoteAverage;
    TextView tvVoteCount;
    TextView tvReleaseDate;

    String youtubeKey;
    ImageView ivVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        youtubeKey = "";

        // Implement View Binding
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setSubtitle("Movie Details");
        getSupportActionBar().setTitle("Flixter");

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(VIDEOS_URL_1+movie.getId()+VIDEOS_URL_2, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    int index;
                    index = 0;
                    while (index < results.length() && youtubeKey.equals("")) {
                        JSONObject jsonObject1 = (JSONObject) results.get(index);
                        if (jsonObject1.getString("site").equals("YouTube")) {
                            youtubeKey = jsonObject1.getString("key");
                            Log.i(TAG, "key: " + youtubeKey);
                        }
                        index += 1;
                    }
                } catch (JSONException jsonException) {
                    Log.e(TAG, "Hit json exception", jsonException);
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });

        tvTitleDetail = binding.tvTitleDetail;
        tvOverviewDetail = binding.tvOverviewDetail;
        rbVoteAverage = binding.rbVoteAverage;
        tvVoteCount = binding.tvVoteCount;
        tvReleaseDate = binding.tvReleaseDate;
        ivVideo = binding.ivVideo;

        // set the title and overview
        tvTitleDetail.setText(movie.getTitle());
        tvOverviewDetail.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        tvVoteCount.setText(movie.getVoteCount() + " votes");

        tvReleaseDate.setText("Released: " + movie.getReleaseDate());

        Glide.with(MovieDetailsActivity.this)
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.flicks_backdrop_placeholder)
                .fitCenter()
                .transform(new RoundedCorners(40))
                .into(ivVideo);

        ivVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (youtubeKey == "") {
                    Toast.makeText(MovieDetailsActivity.this, "No Video Available", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
                    // serialize the movie using parceler, use its short name as a key
                    intent.putExtra("youtubeId", youtubeKey);
                    // show the activity
                    startActivity(intent);

                }
            }
        });
    }
}