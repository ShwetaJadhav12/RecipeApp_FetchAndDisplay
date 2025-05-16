package com.example.recipeappfetchanddisplay

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val Myretrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
        .addConverterFactory(GsonConverterFactory.create()).build()

val recipeApi = Myretrofit.create(Apiservice::class.java)

interface Apiservice{

    @GET("categories.php")
//    categories.php is a endpoint that we want to access
//    allows to send a get request to the server and get a response

    suspend fun getCategories(): CategoryResponse

}