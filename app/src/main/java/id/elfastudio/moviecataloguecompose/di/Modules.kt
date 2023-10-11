package id.elfastudio.moviecataloguecompose.di

import androidx.room.Room
import id.elfastudio.moviecataloguecompose.BuildConfig
import id.elfastudio.moviecataloguecompose.data.repository.MovieRepository
import id.elfastudio.moviecataloguecompose.data.source.local.room.AppDatabase
import id.elfastudio.moviecataloguecompose.data.source.remote.datasource.MovieDataSource
import id.elfastudio.moviecataloguecompose.data.source.remote.network.ApiHelper
import id.elfastudio.moviecataloguecompose.data.source.remote.network.ApiHelperImpl
import id.elfastudio.moviecataloguecompose.data.source.remote.network.ApiService
import id.elfastudio.moviecataloguecompose.domain.repository.IMovieRepository
import id.elfastudio.moviecataloguecompose.domain.usecase.MovieInteractor
import id.elfastudio.moviecataloguecompose.domain.usecase.MovieUseCase
import id.elfastudio.moviecataloguecompose.ui.pages.detail_movie.DetailMovieViewModel
import id.elfastudio.moviecataloguecompose.ui.pages.favorite.FavoriteViewModel
import id.elfastudio.moviecataloguecompose.ui.pages.movie.MovieViewModel
import id.elfastudio.moviecataloguecompose.ui.pages.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val databaseModule = module {
    factory { get<AppDatabase>().movieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                val originalHttpUrl = chain.request().url
                val url =
                    originalHttpUrl.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY)
                        .build()
                request.url(url)
                return@addInterceptor chain.proceed(request.build())
            }
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
    single<ApiHelper> { ApiHelperImpl(get()) }
}

val repositoryModule = module {
    single { MovieDataSource(get()) }
    single<IMovieRepository> { MovieRepository(get(), get()) }
}

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}