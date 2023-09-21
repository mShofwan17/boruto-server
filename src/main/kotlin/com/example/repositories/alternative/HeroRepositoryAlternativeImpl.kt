package com.example.repositories.alternative

import com.example.PageUtil
import com.example.data.HeroDataSource
import com.example.models.ApiResponse
import com.example.models.Hero

class HeroRepositoryAlternativeImpl(
    private var dataSource: HeroDataSource
) : HeroRepositoryAlternative {
    private val heroes = dataSource.allHerroes
    override suspend fun getAllHeroes(page: Int, limit: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            prevPage = PageUtil.prevPage(
                page = page,
                limit = limit,
                items = heroes
            ),
            nextPage = PageUtil.nextPage(
                page = page,
                limit = limit,
                items = heroes
            ),
            heroes = provideHeroes(
                items = heroes,
                limit = limit,
                page = page
            ),
            lastUpdated = System.currentTimeMillis()
        )
    }

    override suspend fun searchHeroes(name: String?): ApiResponse {
        return ApiResponse(
            success = true,
            message = "true",
            heroes = findHeroes(name)
        )
    }

    private fun provideHeroes(
        items: List<Hero>,
        page: Int,
        limit: Int
    ): List<Hero> {
        val allHeroes = items.windowed(
            size = limit,
            step = limit,
            partialWindows = true
        )

        require(page > 0 && page <= allHeroes.size)
        return allHeroes[page - 1]
    }

    private fun findHeroes(query: String?): List<Hero> {
        val found = mutableListOf<Hero>()

        return if (!query.isNullOrEmpty()) {
            this.heroes.forEach {
                if (it.name.lowercase().contains(query.lowercase())) {
                    found.add(it)
                }
            }

            found
        } else {
            emptyList()
        }
    }
}