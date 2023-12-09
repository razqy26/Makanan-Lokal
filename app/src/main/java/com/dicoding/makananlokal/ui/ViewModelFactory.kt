package com.dicoding.makananlokal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.makananlokal.data.FoodRepository
import com.dicoding.makananlokal.ui.screen.detail.DetailFoodViewModel
import com.dicoding.makananlokal.ui.screen.favorite.FavoriteViewModel
import com.dicoding.makananlokal.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailFoodViewModel::class.java)) {
            return DetailFoodViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}