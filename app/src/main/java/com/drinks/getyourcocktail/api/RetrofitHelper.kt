package com.drinks.getyourcocktail.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "https://www.thecocktaildb.com/api/"
    var instance: Retrofit? = null


    fun getRetrofitInstance(): Retrofit {
        if (instance != null) {
            return instance!!
        } else {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return instance!!

        }

    }


}