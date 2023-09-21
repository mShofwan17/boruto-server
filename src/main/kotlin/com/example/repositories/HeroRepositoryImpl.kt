package com.example.repositories

import com.example.PageUtil
import com.example.models.ApiResponse
import com.example.models.Hero
import com.example.data.HeroDataSource

class HeroRepositoryImpl(
    private val service: HeroDataSource
) : IHeroRepository {
    override val heroes: Map<Int, List<Hero>> by lazy {
        mapOf(
            1 to page1,
            2 to page2,
            3 to page3,
            4 to page4,
            5 to page5
        )
    }
    override val page1: List<Hero>
        get() = service.page1
    override val page2: List<Hero>
        get() = service.page2
    override val page3: List<Hero>
        get() = service.page3
    override val page4: List<Hero>
        get() = service.page4
    override val page5: List<Hero>
        get() = service.page5

    override suspend fun getAllHeroes(page: Int): ApiResponse {
        return ApiResponse(
            success = true,
            message = "Success",
            prevPage = PageUtil.prevPage(page),
            nextPage = PageUtil.nextPage(page),
            heroes = heroes[page],
            lastUpdated = System.currentTimeMillis()
        )
    }

    override suspend fun searchHeroes(name: String?): ApiResponse {
        return ApiResponse(
            success = true,
            message = "OK",
            heroes = findHeroes(name)
        )
    }

    private fun findHeroes(query: String?): List<Hero> {
        val found = mutableListOf<Hero>()

        return if (!query.isNullOrEmpty()) {
            this.heroes.forEach { (_, heroes) ->
                heroes.forEach {
                    if (it.name.lowercase().contains(query.lowercase())) {
                        found.add(it)
                    }
                }
            }

            found
        } else {
            emptyList()
        }
    }
}