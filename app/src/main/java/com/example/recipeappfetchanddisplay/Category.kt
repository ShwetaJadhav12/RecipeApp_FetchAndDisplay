package com.example.recipeappfetchanddisplay

data class Category(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String

)
data class CategoryResponse(val categories: List<Category>)