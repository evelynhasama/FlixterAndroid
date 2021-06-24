package com.example.flixter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

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
        setContentView(R.layout.activity_movie_details);

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvTitleDetail = findViewById(R.id.tvTitleDetail);
        tvOverviewDetail = findViewById(R.id.tvOverviewDetail);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        tvVoteCount = findViewById(R.id.tvVoteCount);
        tvReleaseDate = findViewById(R.id.tvReleaseDate);

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