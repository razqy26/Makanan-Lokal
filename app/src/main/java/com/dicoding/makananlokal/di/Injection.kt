package com.dicoding.makananlokal.di

import com.dicoding.makananlokal.data.FoodRepository

object Injection {
    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}