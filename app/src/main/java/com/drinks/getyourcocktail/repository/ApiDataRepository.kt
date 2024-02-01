package com.drinks.getyourcocktail.repository

import android.util.Log
import com.drinks.getyourcocktail.api.DrinksApiService
import com.drinks.getyourcocktail.models.CocktailData
import com.drinks.getyourcocktail.models.SingleDrink
import retrofit2.Response

class ApiDataRepository(val apiService: DrinksApiService) {

    suspend fun getSearchData(name: String): Response<CocktailData> {
        return apiService.getDrinks(name)
    }

    suspend fun getDrinkData(id: Int): Response<SingleDrink> {
        Log.d("testing single data", "inside of get repo")
        return apiService.getDrink(id.toString())
    }


}