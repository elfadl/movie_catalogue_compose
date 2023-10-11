package id.elfastudio.moviecataloguecompose.utils

import id.elfastudio.moviecataloguecompose.data.source.local.entity.FavoriteMovieWithMovie
import id.elfastudio.moviecataloguecompose.data.source.local.entity.MovieEntity
import id.elfastudio.moviecataloguecompose.data.source.remote.response.MovieResponse
import id.elfastudio.moviecataloguecompose.domain.model.Movie

object DataMapper {

    fun favoriteMoviesToMovies(input: List<FavoriteMovieWithMovie>): List<Movie> =
        input.map {
            Movie(
                it.movieEntity.movieId,
                it.movieEntity.title,
                it.movieEntity.genre,
                it.movieEntity.release,
                it.movieEntity.runtime,
                it.movieEntity.score,
                it.movieEntity.overview,
                it.movieEntity.poster,
                it.movieEntity.popularity,
                it.favorite.favorite
            )
        }

    fun favoriteMovieToMovie(input: FavoriteMovieWithMovie?): Movie? = input?.let {
        Movie(
            input.movieEntity.movieId,
            input.movieEntity.title,
            input.movieEntity.genre,
            input.movieEntity.release,
            input.movieEntity.runtime,
            input.movieEntity.score,
            input.movieEntity.overview,
            input.movieEntity.poster,
            input.movieEntity.popularity,
            input.favorite.favorite
        )
    }

    fun movieEntitiesToMovies(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                it.movieId,
                it.title,
                it.genre,
                it.release,
                it.runtime,
                it.score,
                it.overview,
                it.poster,
                it.popularity
            )
        }

}

