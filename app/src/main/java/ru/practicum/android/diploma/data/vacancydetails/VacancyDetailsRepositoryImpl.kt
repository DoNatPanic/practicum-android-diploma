package ru.practicum.android.diploma.data.vacancydetails

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.VacancyDetailsResponse
import ru.practicum.android.diploma.domain.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancydetails.api.VacancyDetailsRepository

class VacancyDetailsRepositoryImpl(
    private val vacanciesConverter: VacanciesConverter,
    private val networkClient: NetworkClient,
) : VacancyDetailsRepository {

    companion object {
        private const val NO_INTERNET_CONNECTION = -1
        private const val SUCCESSFUL_RESPONSE = 200
    }

    override fun searchVacancyById(vacancyId: String): Flow<Resource<Vacancy>> = flow {
        val response = networkClient.doRequestVacancyDetails(vacancyId)

        when (response.resultCode) {
            NO_INTERNET_CONNECTION -> emit(Resource.Error(-1))

            SUCCESSFUL_RESPONSE -> {
                with(response as VacancyDetailsResponse) {
                    val data = vacanciesConverter.convertFull(response.vacancyDto)
                    // Тут нужно будет проверять находится ли вакансия в избранном или нет
                    emit(Resource.Success(data))
                }
            }
        }
    }
}
