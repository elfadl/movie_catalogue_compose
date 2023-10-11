package id.elfastudio.moviecataloguecompose.data.repository

import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.room.withTransaction
import id.elfastudio.moviecataloguecompose.data.source.local.entity.FavoriteMovie
import id.elfastudio.moviecataloguecompose.data.source.local.entity.MovieEntity
import id.elfastudio.moviecataloguecompose.data.source.local.room.AppDatabase
import id.elfastudio.moviecataloguecompose.data.source.remote.datasource.MovieDataSource
import id.elfastudio.moviecataloguecompose.domain.model.Movie
import id.elfastudio.moviecataloguecompose.domain.repository.IMovieRepository
import id.elfastudio.moviecataloguecompose.utils.DataMapper
import id.elfastudio.moviecataloguecompose.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull

class MovieRepository(
    private val dataSource: MovieDataSource,
    private val appDatabase: AppDatabase
) : IMovieRepository {

    override fun getMovie() = performGetOperation(
        localData = {
            appDatabase.movieDao().getMovies()
                .mapNotNull { DataMapper.movieEntitiesToMovies(it) }.asLiveData()
        },
        networkCall = {
            dataSource.getMovie()
        },
        saveCallResult = {
            it.results.map { movie ->
                val data = MovieEntity(
                    movie.id,
                    movie.title,
                    null,
                    movie.getRelease(),
                    null,
                    movie.getScore(),
                    movie.overview,
                    movie.posterPath,
                    movie.popularity
                )
                val favorite = FavoriteMovie(movie.id, false)
                appDatabase.withTransaction {
                    appDatabase.movieDao().insert(listOf(data))
                    appDatabase.movieDao().insertFavoriteMovie(listOf(favorite))
                }
            }
        },
        coroutineContext = Dispatchers.IO
    ).asFlow()

    override fun getDetailMovie(movieId: Int) = performGetOperation(
        localData = {
            appDatabase.movieDao().getDetailMovie(movieId)
                .mapNotNull { DataMapper.favoriteMovieToMovie(it) }.asLiveData()
        },
        networkCall = {
            dataSource.getDetailMovie(movieId)
        },
        saveCallResult = {
            val data = MovieEntity(
                it.id,
                it.title,
                it.genres.map { genre -> genre.name },
                it.getRelease(),
                it.getRuntime(),
                it.getScore(),
                it.overview,
                it.posterPath,
                it.popularity
            )
            val favorite = FavoriteMovie(it.id, false)
            appDatabase.withTransaction {
                appDatabase.movieDao().insert(listOf(data))
                appDatabase.movieDao().insertFavoriteMovie(listOf(favorite))
            }
        },
        coroutineContext = Dispatchers.IO
    ).asFlow()

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return appDatabase.movieDao().getFavoriteMovies().mapNotNull {
            DataMapper.favoriteMoviesToMovies(it)
        }
    }

    override suspend fun favoriteMovie(movieId: Int, state: Boolean) =
        appDatabase.movieDao().favoriteMovie(FavoriteMovie(movieId, state))

}