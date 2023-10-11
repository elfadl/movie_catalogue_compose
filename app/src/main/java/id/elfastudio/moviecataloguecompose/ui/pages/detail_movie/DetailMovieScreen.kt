package id.elfastudio.moviecataloguecompose.ui.pages.detail_movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.elfastudio.moviecataloguecompose.R
import id.elfastudio.moviecataloguecompose.other.UiState
import id.elfastudio.moviecataloguecompose.other.Url.IMAGE
import id.elfastudio.moviecataloguecompose.ui.theme.Dark
import id.elfastudio.moviecataloguecompose.ui.theme.MovieCatalogueComposeTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    movieId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailMovieViewModel = koinViewModel()
) {
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
                }
                viewModel.getDetailMovie(movieId)
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
                DetailContent(
                    imagePoster = uiState.data.poster ?: "",
                    title = uiState.data.title,
                    releaseDate = uiState.data.release ?: "-",
                    genre = uiState.data.genre?.joinToString(", ") ?: "-",
                    runtime = uiState.data.runtime ?: "-",
                    score = "${uiState.data.score}%",
                    overview = uiState.data.overview ?: "-",
                    isFavorite = uiState.data.favorite,
                    actionFavorite = {
                        viewModel.favoriteMovie(uiState.data.movieId, !uiState.data.favorite)
                    }
                )
            }
        }
    }
}

@Composable
fun DetailContent(
    imagePoster: String,
    title: String,
    releaseDate: String,
    genre: String,
    runtime: String,
    score: String,
    overview: String,
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    actionFavorite: () -> Unit
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(bottom = 80.dp),
        modifier = modifier
    ) {
        item {
            MovieHeader(
                imagePoster,
                title,
                releaseDate,
                genre,
                runtime,
                score,
                actionFavorite,
                isFavorite
            )
        }
        item {
            MovieContent(overview)
        }
    }
}

@Composable
private fun MovieContent(
    overview: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.overview),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = overview,
            color = Color.Black,
            modifier = Modifier.testTag("overview")
        )
    }
}

@Composable
private fun MovieHeader(
    imagePoster: String,
    title: String,
    releaseDate: String,
    genre: String,
    runtime: String,
    score: String,
    actionFavorite: () -> Unit,
    isFavorite: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Dark)
            .padding(top = 20.dp, bottom = 20.dp)
    ) {
        AsyncImage(
            model = "$IMAGE$imagePoster",
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(160.dp)
                .height(240.dp)
                .clip(RoundedCornerShape(8.dp))
                .padding(start = 24.dp)
                .testTag("poster"),
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.testTag("title")
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = releaseDate,
                fontSize = 14.sp,
                color = Color.White,
            )
            Text(
                text = genre,
                fontSize = 14.sp,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.runtime),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = runtime,
                fontSize = 14.sp,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.score),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = score,
                fontSize = 14.sp,
                color = Color.White,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .selectable(
                        selected = isFavorite,
                        onClick = { actionFavorite() },
                        role = Role.Checkbox
                    )
                    .padding(top = 10.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "favorite",
                    tint = Color.White
                )
                Text(
                    text = if(isFavorite) "Added to Favorite" else "Add to Favorite",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    MovieCatalogueComposeTheme {
        DetailContent(
            imagePoster = "",
            title = "Alita",
            releaseDate = "2018-10-05 (US)",
            genre = "Drama, Animation",
            runtime = "2h 16m",
            score = "75%",
            overview = "Seasoned musician Jackson Maine discovers — and falls in love with — struggling artist Ally. She has just about given up on her dream to make it big as a singer — until Jack coaxes her into the spotlight. But even as Ally's career takes off, the personal side of their relationship is breaking down, as Jack fights an ongoing battle with his own internal demons.",
            isFavorite = false,
            actionFavorite = {}
        )
    }
}