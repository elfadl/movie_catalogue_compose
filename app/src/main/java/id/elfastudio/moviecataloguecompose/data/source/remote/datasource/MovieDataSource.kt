package id.elfastudio.moviecataloguecompose.data.source.remote.datasource

import id.elfastudio.moviecataloguecompose.data.source.remote.network.ApiHelper
import id.elfastudio.moviecataloguecompose.data.source.remote.network.ApiResponse
import id.elfastudio.moviecataloguecompose.data.source.remote.response.DetailMovieResponse
import id.elfastudio.moviecataloguecompose.data.source.remote.response.MovieResponse

class MovieDataSource(private val apiHelper: ApiHelper): BaseDataSource() {

    suspend fun getMovie(): ApiResponse<MovieResponse> = getResult {
        apiHelper.getMovies()
    }

    suspend fun getDetailMovie(movieId: Int): ApiResponse<DetailMovieResponse> = getResult {
        apiHelper.getDetailMovie(movieId)
    }

}