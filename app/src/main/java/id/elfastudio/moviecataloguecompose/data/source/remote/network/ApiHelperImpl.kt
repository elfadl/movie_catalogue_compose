package id.elfastudio.moviecataloguecompose.data.source.remote.network

import id.elfastudio.moviecataloguecompose.data.source.remote.response.DetailMovieResponse
import id.elfastudio.moviecataloguecompose.data.source.remote.response.MovieResponse
import retrofit2.Response

class ApiHelperImpl constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getMovies(): Response<MovieResponse> = apiService.getMovies()

    override suspend fun getDetailMovie(movieId: Int): Response<DetailMovieResponse> =
        apiService.getDetailMovie(movieId)

    override suspend fun searchMovie(query: String): Response<MovieResponse> =
        apiService.searchMovies(query)
}