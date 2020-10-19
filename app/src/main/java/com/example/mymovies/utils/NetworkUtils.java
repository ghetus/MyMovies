package com.example.mymovies.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie?";
    private static final String BASE_URL_VIDEO = "https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_URL_REVIEW = "https://api.themoviedb.org/3/movie/%s/reviews";

    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_MIN_VOTE_COUNT = "vote_count.gte";

    private static final String PARAMS_API_KEY_VALUE = "c2402da7f75a1ed66e58704a1eeec8be";
//    private static final String PARAMS_LANGUAGE_VALUE = "en-EN";
    private static final String PARAMS_SORT_BY_POPULARITY = "popularity.desc";
    private static final String PARAMS_SORT_BY_VOTE = "vote_average.desc";
    private static final String PARAMS_MIN_VOTE_COUNT_VALUE = "1000";


    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;


    public static class JSONLoader extends AsyncTaskLoader<JSONObject> {
        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;

        public interface OnStartLoadingListener{
            void onStartLoading();
        }

        public void setLoadingListener(OnStartLoadingListener loadingListener) {
            this.onStartLoadingListener = loadingListener;
        }

        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onStartLoadingListener != null) {
                onStartLoadingListener.onStartLoading();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle != null) {
                String urlAsString = bundle.getString("url");
                URL url = null;
                try {
                    url = new URL(urlAsString);
                    JSONObject result = null;
                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = connection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader reader = new BufferedReader(inputStreamReader);
                        StringBuilder builder = new StringBuilder();
                        String line = reader.readLine();
                        while (line != null) {
                            builder.append(line);
                            line = reader.readLine();
                        }
                        result = new JSONObject(builder.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }

                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }
    }

    public static URL buildURL(int sortBy, int page, String lang) {
        URL result = null;
        String methodOfSort = PARAMS_SORT_BY_POPULARITY;
        if (sortBy == TOP_RATED) {
            methodOfSort = PARAMS_SORT_BY_VOTE;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, PARAMS_API_KEY_VALUE)
                .appendQueryParameter(PARAMS_LANGUAGE, lang)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .appendQueryParameter(PARAMS_MIN_VOTE_COUNT, PARAMS_MIN_VOTE_COUNT_VALUE)
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static URL buildURLToVideo(int movieID, String lang) {
        Uri uri = Uri.parse(String.format(BASE_URL_VIDEO, movieID)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, PARAMS_API_KEY_VALUE)
                .appendQueryParameter(PARAMS_LANGUAGE, lang)
                .build();
        try {
            return new URL(uri.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static URL buildURLToReview(int movieID, String lang) {
        Uri uri = Uri.parse(String.format(BASE_URL_REVIEW, movieID)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, PARAMS_API_KEY_VALUE)
                .appendQueryParameter(PARAMS_LANGUAGE, lang)
                .build();
        try {
            return new URL(uri.toString());
        } catch (Exception e) {
            return null;
        }
    }


    public static JSONObject getJSONFromNetwork(int sortBy, int page, String lang) {
        JSONObject result = null;
        URL url = buildURL(sortBy, page, lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONVideo(int movieID, String lang) {
        JSONObject result = null;
        URL url = buildURLToVideo(movieID, lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONReview(int movieID, String lang) {
        JSONObject result = null;
        URL url = buildURLToReview(movieID, lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return null;
            } else {
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) urls[0].openConnection();
                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder builder = new StringBuilder();
                    String line = reader.readLine();
                    while (line != null) {
                        builder.append(line);
                        line = reader.readLine();
                    }
                    result = new JSONObject(builder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
            return result;
        }
    }
}

