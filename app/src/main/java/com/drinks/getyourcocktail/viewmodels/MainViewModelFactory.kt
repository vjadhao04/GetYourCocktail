package com.drinks.getyourcocktail.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drinks.getyourcocktail.api.DrinksApiService
import com.drinks.getyourcocktail.repository.ApiDataRepository

class MainViewModelFactory(private val apiDataRepository: ApiDataRepository, private val apiService: DrinksApiService) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(apiService, apiDataRepository) as T
    }
}