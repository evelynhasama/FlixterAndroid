package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flixter.databinding.ActivityMainBinding;
import com.example.flixter.databinding.ActivityMovieDetailsBinding;
import com.example.flixter.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;

    TextView tvTitleDetail;
    TextView tvOverviewDetail;
    RatingBar rbVoteAverage;
    TextView tvVoteCount;
    TextView tvReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_movie_details);

        // Implement View Binding
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setSubtitle("Movie Details");
        getSupportActionBar().setTitle("Flixter");

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitleDetail = binding.tvTitleDetail;
        tvOverviewDetail = binding.tvOverviewDetail;
        rbVoteAverage = binding.rbVoteAverage;
        tvVoteCount = binding.tvVoteCount;
        tvReleaseDate = binding.tvReleaseDate;

        // set the title and overview
        tvTitleDetail.setText(movie.getTitle());
        tvOverviewDetail.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        tvVoteCount.setText(movie.getVoteCount() + " votes");

        tvReleaseDate.setText("Released: " + movie.getReleaseDate());
    }
}