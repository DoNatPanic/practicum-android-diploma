package ru.practicum.android.diploma.ui.favourites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.DatabaseResult
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.favorites.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.SingleEventLiveData

class FavouritesViewModel(
    private val interactor: FavoritesInteractor
) : ViewModel() {

    init {
        loadFavorites()
    }

    private val openVacancyTrigger = SingleEventLiveData<Long>()
    fun getVacancyTrigger(): SingleEventLiveData<Long> = openVacancyTrigger

    private val _favoriteVacancies = MutableLiveData<DatabaseResult>()
    val favoriteVacancies: LiveData<DatabaseResult> = _favoriteVacancies

    fun onVacancyClicked(vacancyId: Long) {
        openVacancyTrigger.value = vacancyId
    }

    private fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.getFavoritesList()
                .collect { resource ->
                    _favoriteVacancies.value = resource
                }
        }
    }
}
