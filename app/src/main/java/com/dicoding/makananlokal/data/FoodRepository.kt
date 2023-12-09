package com.dicoding.makananlokal.data

import com.dicoding.makananlokal.model.FakeFoodDataSource
import com.dicoding.makananlokal.model.FavoriteFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FoodRepository {

    private val favoriteFood = mutableListOf<FavoriteFood>()

    init {
        if (favoriteFood.isEmpty()) {
            FakeFoodDataSource.dummyFoods.forEach {
                favoriteFood.add(FavoriteFood(it,0))
            }
        }
    }

    fun getAllFoods(): Flow<List<FavoriteFood>> {
        return flowOf(favoriteFood)
    }

    fun getFavoriteFoodById(foodId: Long): FavoriteFood {
        return favoriteFood.first {
            it.food.id == foodId
        }
    }

    fun updateFavoriteFood(foodId: Long, newStarsValue: Int): Flow<Boolean> {
        val index = favoriteFood.indexOfFirst { it.food.id == foodId }
        val result = if (index >= 0) {
            val favFood = favoriteFood[index]
            favoriteFood[index] =
                favFood.copy(food = favFood.food, stars = newStarsValue)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedFavoriteFoods(): Flow<List<FavoriteFood>> {
        return getAllFoods()
            .map { favoriteFoods ->
                favoriteFoods.filter { favFood ->
                    favFood.stars != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this) {
                FoodRepository().apply {
                    instance = this
                }
            }
    }
}