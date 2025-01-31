package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    // suspend fun doRequest(dto: Any): Response
    suspend fun doRequestVacancies(text: String?, options: HashMap<String, Int>): Response
    suspend fun doRequestVacancyDetails(vacancyId: String): Response
    suspend fun doRequestCountries(): Response
    suspend fun doRequestArea(areaId: String): Response
}
