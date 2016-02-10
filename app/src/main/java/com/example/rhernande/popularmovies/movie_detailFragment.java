package com.example.rhernande.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class movie_detailFragment extends Fragment {

    public movie_detailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movie")) {
            Movie mMovie = (Movie)intent.getSerializableExtra("movie");

            TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
            movieTitle.setText(mMovie.title);

            ImageView moviePoster = (ImageView) rootView.findViewById(R.id.image_view_poster);
            Picasso.with(getContext()).load(mMovie.getFullPosterPath()).into(moviePoster);
        }

        return rootView;
    }
}
