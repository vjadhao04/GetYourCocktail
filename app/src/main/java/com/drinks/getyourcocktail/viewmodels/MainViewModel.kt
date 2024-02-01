package com.drinks.getyourcocktail.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinks.getyourcocktail.api.DrinksApiService
import com.drinks.getyourcocktail.models.CocktailData
import com.drinks.getyourcocktail.repository.ApiDataRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val drinksApiService: DrinksApiService,
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {
    private var searchMutableLiveData: MutableLiveData<CocktailData> = MutableLiveData()
    val searchLiveData: LiveData<CocktailData>
        get() = searchMutableLiveData
    var id: Int = -1

    fun getSearchedData(name: String) {
        viewModelScope.launch {
            val data = apiDataRepository.getSearchData(name)
            searchMutableLiveData.postValue(data.body())
        }
    }

}