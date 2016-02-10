package com.example.rhernande.popularmovies;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rhernande on 2/9/16.
 */
public class Movie implements Serializable {
    private final String LOG_TAG = Movie.class.getSimpleName();

    String title;
    String posterPath;
    String overview;
    Date releaseDate;
    Double voteAverage;

    public Movie(JSONObject movie) throws JSONException {
        posterPath = movie.getString("poster_path");
        title = movie.getString("title");
        overview = movie.getString("overview");
        voteAverage = movie.getDouble("vote_average");

        // Date
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String releaseDateString = movie.getString("release_date");
        try {
            releaseDate = formatter.parse(releaseDateString);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "Unable to parse date", e);
        }
    }
}
