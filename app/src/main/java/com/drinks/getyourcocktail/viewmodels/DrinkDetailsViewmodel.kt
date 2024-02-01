package com.drinks.getyourcocktail.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drinks.getyourcocktail.api.DrinksApiService
import com.drinks.getyourcocktail.models.CocktailData
import com.drinks.getyourcocktail.models.SingleDrink
import com.drinks.getyourcocktail.repository.ApiDataRepository
import kotlinx.coroutines.launch


class DrinkDetailsViewmodel(
    private val drinksApiService: DrinksApiService,
    private val apiDataRepository: ApiDataRepository
) : ViewModel() {
    private var drinkMutableLiveData: MutableLiveData<SingleDrink> = MutableLiveData()
    val drinkLiveData: LiveData<SingleDrink>
        get() = drinkMutableLiveData

    fun getDrink(id: Int) {
        viewModelScope.launch {
            Log.d("testing single data", "inside of get viewmodel" + id.toString())
            val data = apiDataRepository.getDrinkData(id)
            if (data.isSuccessful) {
                Log.d("testing single data", data.body().toString() + data.code().toString())
                drinkMutableLiveData.postValue(data.body())
            }


        }
    }
}