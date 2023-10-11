package id.elfastudio.moviecataloguecompose.ui.pages.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.elfastudio.moviecataloguecompose.data.source.remote.Resource
import id.elfastudio.moviecataloguecompose.domain.model.Movie
import id.elfastudio.moviecataloguecompose.domain.usecase.MovieUseCase
import id.elfastudio.moviecataloguecompose.other.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movie>>>
        get() = _uiState

    fun getFavoriteMovies() {
        viewModelScope.launch {
            movieUseCase.getFavoriteMovies()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { movies ->
                    if(movies.isNotEmpty()){
                        _uiState.value = UiState.Success(movies)
                    }else{
                        _uiState.value = UiState.Empty
                    }
                }
        }
    }

}