package ru.practicum.android.diploma.ui.search.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.common.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.search.api.VacanciesInteractor

class SearchViewModel(
    private val vacanciesInteractor: VacanciesInteractor
) : ViewModel() {

    private val searchResultData: MutableLiveData<SearchResult> =
        MutableLiveData(SearchResult.Empty)

    fun searchResultLiveData(): LiveData<SearchResult> = searchResultData

    fun searchVacancies() {
        searchResultData.postValue(SearchResult.Loading)

        viewModelScope.launch {
            vacanciesInteractor
                .searchVacancies()
                .collect { resultPair ->
                    resultHandle(resultPair.first, resultPair.second)
                    /*
                   еще потом докинуть условие в каком случае нет интернета
                   то есть в каком случае возращать SearchResult.NoConnection (задача 27)
                    */
                }
        }
    }

    private fun resultHandle(foundVacancies: List<Vacancy>?, errorCode: Int?) {
        if (errorCode != null) {
            if (errorCode == -1) {
                searchResultData.postValue(SearchResult.NoConnection)
            } else {
                searchResultData.postValue(SearchResult.Error)
            }

        } else if (foundVacancies != null) {
            if (foundVacancies.isEmpty()) {
                searchResultData.postValue(SearchResult.NotFound)
            } else {
                searchResultData.postValue(SearchResult.SearchVacanciesContent(foundVacancies.size, foundVacancies))
            }
        }
    }

    fun onVacancyClicked(vacancy: Vacancy) {
        Log.i("isClicked", "Пользователь нажал на вакансию")
    }

}
