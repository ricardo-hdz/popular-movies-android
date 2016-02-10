package com.example.rhernande.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    private ImageAdapter adapter;
    private GridView gridView;
    //private ArrayAdapter<String> adapter;

    @Override
    public void onStart() {
        super.onStart();
        updateMoviePosters();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_popular) {
            updateMoviePosters("popularity.desc");
            return true;
        } else if (id == R.id.action_sort_rating) {
            updateMoviePosters("vote_count.desc");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateMoviePosters() {
        new FetchMoviesClass().execute();
    }

    private void updateMoviePosters(String sort) {
        new FetchMoviesClass().execute(sort);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        gridView = (GridView) rootView.findViewById(R.id.gridview);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public class FetchMoviesClass extends AsyncTask<String, Void, String[]> {
        private final String LOG_TAG = FetchMoviesClass.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] posterPaths) {
            if (posterPaths != null) {
                gridView.setAdapter(new ImageAdapter(getContext(), posterPaths));
            }
        }

        @Override
        protected String[] doInBackground(String... params) {

            Uri.Builder builder = new Uri.Builder();

            String sort = (params.length > 0) ? params[0] : "popularity.desc";

            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("api_key", "")
                    .appendQueryParameter("sort_by", sort);

            String uri = builder.build().toString();
            Log.v(LOG_TAG, "URI value: " + uri);

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String responseString = null;

            try {
                URL url = new URL(uri);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                responseString = buffer.toString();
                Log.v(LOG_TAG, "Response: " + responseString);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error while making network request", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                // parse JSON file
                return getMoviePostersFromJSON(responseString, 20);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error while parsing JSON", e);
                return null;
            }
        }

        private String[] getMoviePostersFromJSON(String response, int numPosters) throws JSONException {
            final int numPostersDefault = 20;

            numPosters = numPosters != 0 ? numPosters : numPostersDefault;

            JSONObject responseJSON = new JSONObject(response);
            JSONArray results = responseJSON.getJSONArray("results");

            String[] posterPaths = new String[numPosters];
            for (int i = 0; i < results.length(); i++) {
                JSONObject movie = results.getJSONObject(i);
                String posterPath = movie.getString("poster_path");
                posterPaths[i] = posterPath;
            }

            for (String path : posterPaths) {
                Log.v(LOG_TAG, "Poster Path: " + path);
            }

            return posterPaths;
        }
    }
}
