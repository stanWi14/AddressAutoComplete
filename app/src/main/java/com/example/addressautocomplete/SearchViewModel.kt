package com.example.addressautocomplete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class Suggestion(val name: String, val city: String?, val country: String?, val latitude: Double, val longitude: Double)

class SearchViewModel : ViewModel() {
    private val _suggestions = MutableLiveData<List<Suggestion>>()
    val suggestions: LiveData<List<Suggestion>> = _suggestions

    fun searchAddresses(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchAddresses(query)
                _suggestions.value = response.features.map {
                    Suggestion(
                        name = it.properties.name,
                        city = it.properties.city,
                        country = it.properties.country,
                        latitude = it.geometry.coordinates[1],
                        longitude = it.geometry.coordinates[0]
                    )
                }
            } catch (e: Exception) {
                _suggestions.value = emptyList()
            }
        }
    }
}

