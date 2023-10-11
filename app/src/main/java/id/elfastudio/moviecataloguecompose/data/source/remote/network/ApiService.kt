package id.elfastudio.moviecataloguecompose.data.source.remote.network

import id.elfastudio.moviecataloguecompose.data.source.remote.response.DetailMovieResponse
import id.elfastudio.moviecataloguecompose.data.source.remote.response.MovieResponse
import id.elfastudio.moviecataloguecompose.other.Url.DETAIL_MOVIE
import id.elfastudio.moviecataloguecompose.other.Url.GET_MOVIE
import id.elfastudio.moviecataloguecompose.other.Url.SEARCH_MOVIE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET(GET_MOVIE)
    suspend fun getMovies(): Response<MovieResponse>

    @GET(DETAIL_MOVIE)
    suspend fun getDetailMovie(
        @Path("movieId") movieId: Int
    ): Response<DetailMovieResponse>

    @GET(SEARCH_MOVIE)
    suspend fun searchMovies(
        @Query("query") query: String
    ): Response<MovieResponse>

}