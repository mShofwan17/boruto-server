package com.example.repositories.alternative

import com.example.models.ApiResponse
import com.example.models.Hero

interface HeroRepositoryAlternative {
    suspend fun getAllHeroes(page: Int = 1, limit: Int = 4): ApiResponse
    suspend fun searchHeroes(name: String?): ApiResponse
}