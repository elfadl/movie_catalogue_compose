package id.elfastudio.moviecataloguecompose.ui.pages.detail_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.elfastudio.moviecataloguecompose.data.source.remote.Resource
import id.elfastudio.moviecataloguecompose.domain.model.Movie
import id.elfastudio.moviecataloguecompose.domain.usecase.MovieUseCase
import id.elfastudio.moviecataloguecompose.other.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailMovieViewModel(
    private val movieUseCase: MovieUseCase
): ViewModel(){

    private val _uiState: MutableStateFlow<UiState<Movie>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Movie>>
        get() = _uiState

    fun getDetailMovie(movieId: Int){
        viewModelScope.launch {
            movieUseCase.getDetailMovie(movieId)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _uiState.value = UiState.Loading
                        }
                        is Resource.Error -> {
                            _uiState.value = UiState.Error(resource.message.toString())
                        }
                        is Resource.Success -> {
                            resource.data?.let { movies ->
                                _uiState.value = UiState.Success(movies)
                            } ?: kotlin.run { _uiState.value = UiState.Empty }
                        }
                    }
                }
        }
    }

    fun favoriteMovie(movieId: Int, state: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movieId > 0) {
                movieUseCase.favoriteMovie(movieId, state)
                getDetailMovie(movieId)
            }
        }
    }

}