package id.elfastudio.moviecataloguecompose.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.elfastudio.moviecataloguecompose.data.source.local.entity.FavoriteMovie
import id.elfastudio.moviecataloguecompose.data.source.local.entity.MovieEntity
import id.elfastudio.moviecataloguecompose.utils.GenreConverter

@Database(
    entities = [
        MovieEntity::class,
        FavoriteMovie::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(GenreConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movie_catalogue_compose"
    }

}