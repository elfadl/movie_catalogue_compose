package id.elfastudio.moviecataloguecompose.ui.pages.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import id.elfastudio.moviecataloguecompose.other.UiState
import id.elfastudio.moviecataloguecompose.ui.component.MovieContent
import id.elfastudio.moviecataloguecompose.ui.component.SearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    navigateToDetail: (Int) -> Unit
) {
    Column(modifier = modifier) {
        val query by viewModel.query
        SearchBar(
            query = query,
            onQueryChange = viewModel::search,
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .semantics(mergeDescendants = true){
                    contentDescription = "search_bar"
                }
        )
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Text(text = "Loading Data...")
                        if (query.isEmpty())
                            viewModel.getMovies()
                        else
                            viewModel.search(query)
                    }
                }
                is UiState.Empty -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Data is empty")
                    }
                }
                is UiState.Error -> {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Error: ${uiState.errorMessage}")
                    }
                }
                is UiState.Success -> {
                    val listState = rememberLazyListState()
                    MovieContent(
                        movies = uiState.data,
                        navigateToDetail = navigateToDetail,
                        listState = listState
                    )
                }
            }
        }
    }
}