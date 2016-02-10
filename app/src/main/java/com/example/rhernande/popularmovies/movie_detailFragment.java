package com.example.rhernande.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class movie_detailFragment extends Fragment {

    private Movie mMovie;

    public movie_detailFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        setMovieFromIntent();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mMovie != null) {
            ((movie_detail)getActivity()).getSupportActionBar().setTitle("IMDB -" + mMovie.title);
        }
    }

    private void setMovieFromIntent() {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie")) {
            mMovie = (Movie) intent.getSerializableExtra("movie");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        setMovieFromIntent();
        //Intent intent = getActivity().getIntent();
        //if (intent != null && intent.hasExtra("movie")) {
        if (mMovie != null) {
            //Movie mMovie = (Movie)intent.getSerializableExtra("movie");

            // Title
            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(mMovie.title);

            // Poster
            ImageView moviePoster = (ImageView) rootView.findViewById(R.id.image_view_poster);
            Picasso.with(getContext()).load(mMovie.getFullPosterPath()).into(moviePoster);

            // Synopsis
            EditText synopsis = (EditText) rootView.findViewById(R.id.editTextSynopsis);
            synopsis.setText(mMovie.overview);

            // Rating
            TextView rating = (TextView) rootView.findViewById(R.id.textViewRating);
            rating.setText(mMovie.voteAverage.toString());

            // Release Date
            TextView releaseDate = (TextView) rootView.findViewById(R.id.textViewReleaseDate);
            releaseDate.setText(mMovie.releaseDate.toString());
        }

        return rootView;
    }
}
