package id.elfastudio.moviecataloguecompose.domain.usecase

import id.elfastudio.moviecataloguecompose.data.source.remote.Resource
import id.elfastudio.moviecataloguecompose.domain.model.Movie
import id.elfastudio.moviecataloguecompose.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository) : MovieUseCase {

    override fun getMovie(): Flow<Resource<List<Movie>>> = movieRepository.getMovie()

    override fun getDetailMovie(movieId: Int): Flow<Resource<Movie>> =
        movieRepository.getDetailMovie(movieId)

    override fun getFavoriteMovies(): Flow<List<Movie>> = movieRepository.getFavoriteMovies()

    override suspend fun favoriteMovie(movieId: Int, state: Boolean) =
        movieRepository.favoriteMovie(movieId, state)
}