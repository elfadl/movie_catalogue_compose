package id.elfastudio.moviecataloguecompose.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import id.elfastudio.moviecataloguecompose.domain.model.Movie
import id.elfastudio.moviecataloguecompose.other.Url

@Composable
fun MovieContent(
    movies: List<Movie>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    listState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(14.dp),
        modifier = modifier
    ) {
        itemsIndexed(movies, key = { _, movie -> movie.movieId }) { index, movie ->
            MovieItem(
                id = movie.movieId,
                imageUrl = Url.IMAGE + (movie.poster ?: ""),
                title = movie.title,
                releaseDate = movie.release ?: "-",
                overview = movie.overview ?: "-",
                navigateToDetail = navigateToDetail,
                modifier = Modifier
                    .testTag("movie_$index")
            )
        }
    }
}