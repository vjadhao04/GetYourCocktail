package com.drinks.getyourcocktail.api

import com.drinks.getyourcocktail.models.CocktailData
import com.drinks.getyourcocktail.models.SingleDrink
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinksApiService {

    @GET("json/v1/1/search.php")
    suspend fun getDrinks(@Query("s") name: String): Response<CocktailData>

    @GET("json/v1/1/lookup.php?")
    suspend fun getDrink(@Query("i") name: String): Response<SingleDrink>
}