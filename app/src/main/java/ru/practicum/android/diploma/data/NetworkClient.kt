package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response

interface NetworkClient {
    // suspend fun doRequest(dto: Any): Response
    suspend fun doRequestVacancies(options: HashMap<String, Int>,text: String?): Response
    suspend fun doRequestVacancyDetails(vacancyId: String): Response
}
