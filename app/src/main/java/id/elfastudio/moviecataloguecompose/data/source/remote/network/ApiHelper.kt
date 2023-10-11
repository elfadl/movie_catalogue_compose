package id.elfastudio.moviecataloguecompose.data.source.remote.network

import id.elfastudio.moviecataloguecompose.data.source.remote.response.DetailMovieResponse
import id.elfastudio.moviecataloguecompose.data.source.remote.response.MovieResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getMovies(): Response<MovieResponse>

    suspend fun getDetailMovie(movieId: Int): Response<DetailMovieResponse>

    suspend fun searchMovie(query: String): Response<MovieResponse>

}