package com.dicoding.makananlokal.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.makananlokal.data.FoodRepository
import com.dicoding.makananlokal.model.FavoriteFood
import com.dicoding.makananlokal.model.Food
import com.dicoding.makananlokal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailFoodViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteFood>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteFood>>
        get() = _uiState

    fun getFoodById(foodId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getFavoriteFoodById(foodId))
        }
    }

    fun addToFavorite(food: Food, stars: Int) {
        viewModelScope.launch {
            repository.updateFavoriteFood(food.id, stars)
        }
    }

}