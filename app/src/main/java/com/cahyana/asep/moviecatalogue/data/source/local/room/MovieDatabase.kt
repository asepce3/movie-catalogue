package com.cahyana.asep.moviecatalogue.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cahyana.asep.moviecatalogue.data.MovieDetail
import com.cahyana.asep.moviecatalogue.data.TvShowDetail
import com.cahyana.asep.moviecatalogue.data.source.local.entity.Genre
import com.cahyana.asep.moviecatalogue.data.source.local.entity.MovieGenreCrossRef
import com.cahyana.asep.moviecatalogue.data.source.local.entity.TvShowGenreCrossRef

@Database(entities = [Genre::class, MovieDetail::class, TvShowDetail::class,
    MovieGenreCrossRef::class, TvShowGenreCrossRef::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movies_catalogue.db"
                ).build().apply {
                    instance = this
                }
            }

    }
}