package com.example.recipeappfetchanddisplay

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    data class Responce(
        val isLoading: Boolean = true,
        val categories : List<Category> = emptyList(),
        val error :String? = null
    )
    private val _categories = mutableStateOf((Responce()))
    val categories: MutableState<Responce> = _categories


    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try{
                val r = recipeApi.getCategories()
                _categories.value = _categories.value.copy(
                    isLoading = false,
                    categories = r.categories,
                    error = null
                )


            }
            catch (e: Exception){
                _categories.value = _categories.value.copy(
                    isLoading = false,
                    error = "error in fecthing the data ${e.message}"
                )

            }

        }
    }



}