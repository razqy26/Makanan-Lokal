package com.dicoding.makananlokal.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.makananlokal.data.FoodRepository
import com.dicoding.makananlokal.model.FavoriteFood
import com.dicoding.makananlokal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavoriteFood>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavoriteFood>>>
        get() = _uiState

    fun getAllFoods() {
        viewModelScope.launch {
            repository.getAllFoods()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteFoods ->
                    _uiState.value = UiState.Success(favoriteFoods)
                }
        }
    }

}