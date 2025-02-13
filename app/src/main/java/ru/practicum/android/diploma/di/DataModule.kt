package ru.practicum.android.diploma.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.areas.AreasRepositoryImpl
import ru.practicum.android.diploma.data.converters.AreaConverter
import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.filter.FilterRepositoryImpl
import ru.practicum.android.diploma.data.filter.StorageFilter
import ru.practicum.android.diploma.data.industries.IndustriesRepositoryImpl
import ru.practicum.android.diploma.data.network.HhApi
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.search.VacanciesRepositoryImpl
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorImpl
import ru.practicum.android.diploma.data.vacancydetails.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.areas.api.AreasRepository
import ru.practicum.android.diploma.domain.favorites.api.FavoritesRepository
import ru.practicum.android.diploma.domain.filter.api.FilterRepository
import ru.practicum.android.diploma.domain.industries.api.IndustriesRepository
import ru.practicum.android.diploma.domain.mapper.VacancyToVacancyForSearchViewHolderMapper
import ru.practicum.android.diploma.domain.models.FilterParameters
import ru.practicum.android.diploma.domain.search.api.VacanciesRepository
import ru.practicum.android.diploma.domain.sharing.api.ExternalNavigator
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository

private const val BASE_URL = "https://api.hh.ru/"
const val FILTER_KEY = "key_for_filter"
const val FILTER_PREFERENCES = "filter_preferences"

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HhApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), get(), get())
    }

    single(named(FILTER_PREFERENCES)) {
        androidContext()
            .getSharedPreferences(FILTER_PREFERENCES, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<AppDatabase>().vacancyDao()
    }

    factory { VacanciesConverter() }

    single<VacanciesRepository> {
        VacanciesRepositoryImpl(get(), get())
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(get(), get())
    }

    factory<FavoritesRepository> {
        FavoritesRepositoryImpl(get(), get(), get())
    }

    factory<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }

    factory { AreaConverter() }

    single<AreasRepository> {
        AreasRepositoryImpl(get(), get())
    }

    factory { IndustriesConverter() }

    single<IndustriesRepository> {
        IndustriesRepositoryImpl(get(), get())
    }

// зависимости для работы с сохранением фильтров
    single { FilterParameters() }

    single<StorageFilter> {
        StorageFilter(sharedPrefs = get(named(FILTER_PREFERENCES)), get())
    }

    single<FilterRepository> {
        FilterRepositoryImpl(get())
    }
// зависимости для работы с сохранением фильтров
    factory { VacancyToVacancyForSearchViewHolderMapper(androidApplication()) }
}
