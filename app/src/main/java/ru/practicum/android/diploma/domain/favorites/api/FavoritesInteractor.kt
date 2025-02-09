package ru.practicum.android.diploma.domain.favorites.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.DatabaseResult
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {
    fun getFavoritesList(): Flow<DatabaseResult>

    suspend fun checkFavoriteStatus(vacancyId: Long): Boolean
    suspend fun getVacancyByID(vacancyId: Long): Vacancy
    suspend fun saveVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    suspend fun removeVacancyById(vacancyId: Long)
}
