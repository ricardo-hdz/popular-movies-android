package com.example.rhernande.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by rhernande on 2/9/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mPaths;

    private final String BASE_URL = "http://image.tmdb.org/t/p/";
    private final String IMAGE_SIZE = "w185";

    public ImageAdapter(Context context) {
        mContext = context;
        mPaths = new String[0];
    }

    public ImageAdapter(Context context, String[] imagePaths) {
        mContext = context;
        mPaths = imagePaths;
    }

    public ImageAdapter(Context context, Movie[] movies) {
        mContext = context;
        String[] imagePaths = new String[movies.length];
        for (int i = 0; i < movies.length; i++) {
            imagePaths[i] = movies[i].posterPath;
        }
        mPaths = imagePaths;
    }

    public void setPosterPaths(String[] paths) {
        mPaths = paths;
    }

    public int getCount() {
        return mPaths.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    private String getFullImagePath(String path) {
        return BASE_URL + IMAGE_SIZE + "/" + path;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(185, 185));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
        } else {
            imageView = (ImageView) convertView;
        }
        if (mPaths != null) {
            String fullPath = getFullImagePath(mPaths[position]);
            Picasso.with(mContext).load(fullPath).into(imageView);
        } else {
            imageView.setImageResource(mThumbIds[position]);
        }

        return imageView;
    }

    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}
