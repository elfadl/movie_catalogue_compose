package id.elfastudio.moviecataloguecompose.ui.pages.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

class SearchViewModel(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Movie>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movie>>>
        get() = _uiState
    private val movieData = mutableStateOf<List<Movie>>(arrayListOf())
    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        if (_uiState.value is UiState.Success)
            _uiState.value = UiState.Success(movieData.value.filter {
                it.title.contains(newQuery, true)
            })
    }

    fun getMovies() {
        viewModelScope.launch {
            movieUseCase.getMovie()
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
                                movieData.value = movies
                                _uiState.value = UiState.Success(movies)
                            } ?: kotlin.run { _uiState.value = UiState.Empty }
                        }
                    }
                }
        }
    }

}