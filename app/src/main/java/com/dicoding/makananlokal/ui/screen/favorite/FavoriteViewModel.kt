package com.dicoding.makananlokal.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.makananlokal.data.FoodRepository
import com.dicoding.makananlokal.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: FoodRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FavoriteState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FavoriteState>>
        get() = _uiState

    fun getAddedOrderRewards() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAddedFavoriteFoods().collect { favoriteFoods ->
                _uiState.value = UiState.Success(FavoriteState(favoriteFoods))
            }
        }
    }

    fun updateOrderReward(rewardId: Long, stars: Int) {
        viewModelScope.launch {
            repository.updateFavoriteFood(rewardId, stars)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderRewards()
                    }
                }
        }
    }
}