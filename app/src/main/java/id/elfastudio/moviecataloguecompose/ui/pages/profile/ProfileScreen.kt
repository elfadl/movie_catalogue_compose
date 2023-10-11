package id.elfastudio.moviecataloguecompose.ui.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.elfastudio.moviecataloguecompose.R
import id.elfastudio.moviecataloguecompose.ui.theme.Dark
import id.elfastudio.moviecataloguecompose.ui.theme.MovieCatalogueComposeTheme

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .background(Dark)
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.photo),
            contentDescription = "Photo",
            modifier = Modifier
                .clip(CircleShape)
                .size(150.dp)
        )
        Text(
            text = stringResource(R.string.my_name),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp)
        )
        Text(
            text = stringResource(R.string.my_email),
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(top = 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    MovieCatalogueComposeTheme {
        ProfileScreen()
    }
}

