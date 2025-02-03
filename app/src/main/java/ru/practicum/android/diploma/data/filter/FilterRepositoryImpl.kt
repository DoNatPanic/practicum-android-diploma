package ru.practicum.android.diploma.data.filter

import ru.practicum.android.diploma.domain.filter.api.FilterRepository

class FilterRepositoryImpl(private val storageFilter: StorageFilter) : FilterRepository {

    override fun saveToFilterStorage(filterParameters: FilterParameters) {
        storageFilter.saveToStorage(filterParameters)
    }

    override fun readFromFilterStorage(): FilterParameters {
        return storageFilter.readFromStorage()
    }

}
