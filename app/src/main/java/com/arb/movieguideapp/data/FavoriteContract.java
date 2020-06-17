package com.arb.movieguideapp.data;

import android.provider.BaseColumns;

public class FavoriteContract {

    public static final class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_DESCRIPTION = "overview";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
//        public static final String COLUMN_GENRE = "genre_ids";
//        public static final String COLUMN_CREDITS = "credits_id";
//        public static final String COLUMN_TRAILERS = "trailer_id";
//        public static final String COLUMN_SIMILAR_MOVIES = "similar_movies_id";
    }
}
