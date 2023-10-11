package id.elfastudio.moviecataloguecompose.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun MovieItem(
    id: Int,
    imageUrl: String,
    title: String,
    releaseDate: String,
    overview: String,
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .padding(8.dp)
            .clickable { navigateToDetail(id) }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier
                .padding(start = 14.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .semantics(mergeDescendants = true){
                        contentDescription = "movie_title"
                    }
            )
            Text(
                text = releaseDate,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1
            )
            Text(
                text = overview,
                maxLines = 2,
                color = Color.Black
            )
        }
    }
}