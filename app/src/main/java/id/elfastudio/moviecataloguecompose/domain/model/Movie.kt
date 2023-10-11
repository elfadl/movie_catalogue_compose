package id.elfastudio.moviecataloguecompose.domain.model

data class Movie(
    val movieId: Int,
    val title: String,
    val genre: List<String>?,
    val release: String?,
    val runtime: String?,
    val score: Int?,
    val overview: String?,
    val poster: String?,
    val popularity: Double,
    val favorite: Boolean = false
)
