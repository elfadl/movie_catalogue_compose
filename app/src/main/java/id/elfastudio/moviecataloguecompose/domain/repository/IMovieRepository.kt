package id.elfastudio.moviecataloguecompose.domain.repository

import id.elfastudio.moviecataloguecompose.data.source.remote.Resource
import id.elfastudio.moviecataloguecompose.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    fun getMovie(): Flow<Resource<List<Movie>>>

    fun getDetailMovie(movieId: Int): Flow<Resource<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun favoriteMovie(movieId: Int, state: Boolean)

}